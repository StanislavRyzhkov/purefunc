package company.ryzhkov.typeclasses

import company.ryzhkov.datatypes.PTable

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
}

object MonoidSyntax {
  implicit class MonoidOps[A: Monoid](a: A) {
    val instance = implicitly[Monoid[A]]

    def combine(a2: A): A = instance.combine(a, a2)

    def <>(a2: A): A = instance.combine(a, a2)
  }
}

object MonoidInstances {
  implicit val intSumMonoid: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = 0

    override def combine(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    override def empty: String = ""

    override def combine(a1: String, a2: String): String = a1 + a2
  }

  implicit def listMonoid[A]: Monoid[List[A]] =
    new Monoid[List[A]] {
      override def empty: List[A] = Nil

      override def combine(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    }

  implicit def endoInstance[A]: Monoid[A => A] =
    new Monoid[A => A] {
      override def empty: A => A = identity

      override def combine(a1: A => A, a2: A => A): A => A = a1 andThen a2
    }

  implicit def scalaSetInstance[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def empty: Set[A] = Set.empty

      override def combine(a1: Set[A], a2: Set[A]): Set[A] = a1 union a2
    }

  implicit def optionMonoid1[A: Monoid]: Monoid[Option[A]] =
    new Monoid[Option[A]] {
      override def empty: Option[A] = None

      override def combine(a1: Option[A], a2: Option[A]): Option[A] = {
        import company.ryzhkov.typeclasses.MonoidSyntax._
        (a1, a2) match {
          case (Some(x1), Some(x2)) => Some(x1 <> x2)
          case _                    => None
        }
      }
    }

  implicit def optionMonoid2[A]: Monoid[Option[A]] =
    new Monoid[Option[A]] {
      override def empty: Option[A] = None

      override def combine(a1: Option[A], a2: Option[A]): Option[A] = {
        (a1, a2) match {
          case (None, x) => x
          case (x, _)    => x
        }
      }
    }

  implicit val pTableInstance: Monoid[PTable] = new Monoid[PTable] {
    override def empty: PTable = PTable.empty

    override def combine(a1: PTable, a2: PTable): PTable =
      (a1, a2) match {
        case (pTable1, PTable(Nil, Nil))      => pTable1
        case (PTable(Nil, Nil), pTable2)      => pTable2
        case (PTable(e1, p1), PTable(e2, p2)) =>
          val newEvents        = PTable.combineEvents(e1, e2)
          val newProbabilities = PTable.combineProbabilities(p1, p2)
          PTable.instance(newEvents, newProbabilities)
      }
  }
}
