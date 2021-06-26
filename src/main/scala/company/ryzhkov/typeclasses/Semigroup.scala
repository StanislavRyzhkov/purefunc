package company.ryzhkov.typeclasses

import company.ryzhkov.datatypes.PTable

trait Semigroup[A] {
  def combine(a1: A, a2: A): A
}

object Semigroup {
  def instance[A](f: (A, A) => A): Semigroup[A] = (a1: A, a2: A) => f(a1, a2)

  def apply[A](implicit ev: Semigroup[A]): Semigroup[A] = ev
}

object SemigroupSyntax {
  implicit class SemigroupOps[A: Semigroup](a1: A) {
    val ev = implicitly[Semigroup[A]]

    def combine(a2: A): A = ev.combine(a1, a2)

    def <>(a2: A): A = combine(a2)
  }
}

object SemigroupInstances {
  implicit val intSumSemigroup: Semigroup[Int]     = Semigroup.instance[Int]((a1, a2) => a1 + a2)
  implicit val intProductSemigroup: Semigroup[Int] = Semigroup.instance[Int]((a1, a2) => a1 * a2)
  implicit val stringSemigroup: Semigroup[String]  = Semigroup.instance[String]((a1, a2) => a1 + a2)

  implicit val pTableSemigroup: Semigroup[PTable] = Semigroup.instance {
    case (pTable1, PTable(Nil, Nil))      => pTable1
    case (PTable(Nil, Nil), pTable2)      => pTable2
    case (PTable(e1, p1), PTable(e2, p2)) =>
      val newEvents        = PTable.combineEvents(e1, e2)
      val newProbabilities = PTable.combineProbabilities(p1, p2)
      PTable.instance(newEvents, newProbabilities)
  }
}
