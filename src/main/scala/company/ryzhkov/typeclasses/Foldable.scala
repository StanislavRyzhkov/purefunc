package company.ryzhkov.typeclasses

import company.ryzhkov.typeclasses.MonoidSyntax._

trait Foldable[F[_]] {
  def fold[M: Monoid](fa: F[M]): M = foldMap(identity[M])(fa)

  def foldMap[A, M: Monoid](f: A => M)(fa: F[A]): M = foldr((x: A, y: M) => f(x).combine(y))(Monoid[M].empty)(fa)

  def foldr[A, B](f: (A, B) => B)(b: B)(fa: F[A]): B
}

object Foldable {
  def apply[F[_]](implicit ev: Foldable[F]): Foldable[F] = ev
}

object FoldableSyntax {}

object FoldableInstances {

  implicit val listFoldableInstance: Foldable[List] =
    new Foldable[List] {
      override def foldr[A, B](f: (A, B) => B)(b: B)(fa: List[A]): B = {
        fa.foldRight(b)(f)
      }
    }

  implicit val optionFoldableInstance: Foldable[Option] = new Foldable[Option] {
    override def foldr[A, B](f: (A, B) => B)(b: B)(fa: Option[A]): B =
      (f, b, fa) match {
        case (_, z, None)       => z
        case (func, z, Some(x)) => func(x, z)
      }
  }
}
