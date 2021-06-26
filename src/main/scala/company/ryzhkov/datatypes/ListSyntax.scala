package company.ryzhkov.datatypes

import company.ryzhkov.typeclasses.Monoid
import company.ryzhkov.typeclasses.MonoidSyntax._

object ListSyntax {
  implicit class ListOps[A: Monoid](list: List[A]) {
    def mconcat: A = list.foldLeft(Monoid[A].empty)(_ <> _)
  }
}
