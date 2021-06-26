package company.ryzhkov

import company.ryzhkov.typeclasses.Semigroup
import company.ryzhkov.typeclasses.SemigroupInstances.stringSemigroup
import company.ryzhkov.typeclasses.SemigroupSyntax.SemigroupOps

object Program extends App {
  println(comb("2", "3"))

  def comb[A: Semigroup](a1: A, a2: A): A = a1 <> a2
}
