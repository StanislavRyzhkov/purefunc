package company.ryzhkov.typeclasses

import company.ryzhkov.datatypes.PTable

trait Show[A] {
  def show(a: A): String
}

object Show {
  def instance[A](f: A => String): Show[A] = (a: A) => f(a)

  def default[A]: Show[A] = Show.instance(_.toString)
}

object ShowSyntax {
  implicit class ShowOps[A: Show](a: A) {
    val instance = implicitly[Show[A]]

    def show: String = instance.show(a)
  }
}

object ShowInstances {
  implicit val stringShowInstance: Show[String] = Show.instance(identity)
  implicit val intShowInstance: Show[Int]       = Show.default
  implicit val doubleShowInstance: Show[Double] = Show.default

  implicit val pairStringDoubleShowInstance: Show[(String, Double)] = {
    import company.ryzhkov.typeclasses.ShowSyntax.ShowOps
    Show.instance {
      case (event, prob) => s"${event.show}|${prob.show}\n"
    }
  }

  implicit def listShowInstance[A]: Show[List[A]] = Show.default

  implicit val pTableShow: Show[PTable] = {
    import company.ryzhkov.typeclasses.ShowSyntax._
    import company.ryzhkov.typeclasses.MonoidInstances.stringMonoid

    Show.instance {
      case PTable(events, probabilities) =>
        val zipList = events.zip(probabilities).map(_.show)
        mconcat(zipList)
    }
  }

  private def mconcat[A: Monoid](list: List[A]): A = {
    import company.ryzhkov.typeclasses.MonoidSyntax._
    list.foldLeft(Monoid[A].empty)((a1, a2) => a1 <> a2)
  }
}
