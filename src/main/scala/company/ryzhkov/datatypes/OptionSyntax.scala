package company.ryzhkov.datatypes

object OptionSyntax {
  implicit class OptionOps[A](a: A) {
    def some: Option[A] = Option(a)
  }

  def none[A]: Option[A] = None

  implicit class NoneOps(none: None.type) {
    def asOption[A]: Option[A] = none
  }
}
