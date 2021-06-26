package company.ryzhkov.typeclasses

trait Functor[F[_]] {
  def fmap[A, B](fa: F[A])(f: A => B): F[B]

  def void[A](fa: F[A]): F[Unit]
}

object Functor {
  def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev
}

object FunctorSyntax {
  implicit class FunctorOps[F[_]: Functor, A](fa: F[A]) {
    val instance = implicitly[Functor[F]]

    def fmap[B](f: A => B): F[B] = instance.fmap(fa)(f)

    def void: F[Unit] = instance.void(fa)
  }
}

object FunctorInstances {
  implicit val optionFunctorInstance: Functor[Option] =
    new Functor[Option] {
      override def fmap[A, B](fa: Option[A])(f: A => B): Option[B] = {
        fa match {
          case Some(value) => Some(f(value))
          case None        => None
        }
      }

      override def void[A](fa: Option[A]): Option[Unit] = Some(())
    }

  implicit val listFunctorInstance: Functor[List] = new Functor[List] {
    override def fmap[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

    override def void[A](fa: List[A]): List[Unit] = List(())
  }

  implicit def eitherFunctorInstance[T]: Functor[Either[T, *]] =
    new Functor[Either[T, *]] {
      override def fmap[A, B](fa: Either[T, A])(f: A => B): Either[T, B] = {
        fa match {
          case Right(value) => Right(f(value))
          case Left(value)  => Left(value)
        }
      }

      override def void[A](fa: Either[T, A]): Either[T, Unit] = Right(())
    }

  implicit def functionFunctorInstance[T]: Functor[T => *] =
    new Functor[Function[T, *]] {
      override def fmap[A, B](fa: Function[T, A])(f: A => B): Function[T, B] = fa andThen f

      override def void[A](fa: Function[T, A]): Function[T, Unit] = _ => ()
    }

  implicit def pairFunctorInstance[T]: Functor[(T, *)] =
    new Functor[(T, *)] {
      override def fmap[A, B](fa: (T, A))(f: A => B): (T, B) = (fa._1, f(fa._2))

      override def void[A](fa: (T, A)): (T, Unit) = (fa._1, ())
    }
}
