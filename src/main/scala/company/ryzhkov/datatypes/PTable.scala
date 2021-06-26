package company.ryzhkov.datatypes

import company.ryzhkov.datatypes.PTable.{Events, Probabilities}
import company.ryzhkov.datatypes.ListSyntax._
import company.ryzhkov.typeclasses.MonoidInstances.listMonoid

case class PTable private (events: Events, probabilities: Probabilities)

object PTable {
  type Events        = List[String]
  type Probabilities = List[Double]

  def empty: PTable = PTable(Nil, Nil)

  def unapply(pTable: PTable): Option[(Events, Probabilities)] = Some(pTable.events, pTable.probabilities)

  def instance(events: Events, probabilities: Probabilities): PTable = {
    val totalProbabilities      = probabilities.sum
    val normalizedProbabilities = probabilities map (_ / totalProbabilities)
    PTable(events, normalizedProbabilities)
  }

  def cartCombine[A, B, C](listA: List[A], listB: List[B])(f: (A, B) => C): List[C] = {
    val nToAdd        = listB.size
    val repeatedListA = listA map (List.fill(nToAdd)(_))
    val newListA      = repeatedListA.mconcat
    val cycledListB   = LazyList.continually(LazyList(listB: _*)).flatten.take(newListA.size).toList
    zipWith(newListA, cycledListB)(f)
  }

  def combineEvents(events1: Events, events2: Events): Events = {
    def combiner(x1: String, x2: String) = s"$x1 - $x2"
    cartCombine(events1, events2)(combiner)
  }

  def combineProbabilities(probabilities1: Probabilities, probabilities2: Probabilities): Probabilities =
    cartCombine(probabilities1, probabilities2)(_ * _)

  def zipWith[A, B, C](listA: List[A], listB: List[B])(f: (A, B) => C): List[C] = {
    val zipped = listA zip listB
    val fShift = f.tupled
    zipped map fShift
  }
}
