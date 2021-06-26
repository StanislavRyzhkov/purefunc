package company.ryzhkov.typeclasses

trait Pointed[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}

object Pointed {
  def apply[F[_]](implicit ev: Pointed[F]): Pointed[F] = ev
}

object PureInstances {
  implicit val optionPointedInstance: Pointed[Option] = new Pointed[Option] {
    override def pure[A](a: A): Option[A] = Some(a)

    override def fmap[A, B](fa: Option[A])(f: A => B): Option[B] = super.fmap(fa)(f)

    override def void[A](fa: Option[A]): Option[Unit] = ???
  }

  implicit val listPointedInstance: Pointed[List] = new Pointed[List] {
    override def pure[A](a: A): List[A] = List(a)
  }

  implicit def eitherPointedInstance[T]: Pointed[Either[T, *]] =
    new Pointed[Either[T, *]] {
      override def pure[A](a: A): Either[T, A] = Right(a)
    }

}
