package company.ryzhkov

import company.ryzhkov.datatypes.PTable
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class PTableTest extends AnyFreeSpec with Matchers {

  "test" in {
    import company.ryzhkov.typeclasses.ShowInstances.pTableShow
    import company.ryzhkov.typeclasses.ShowSyntax._
    import company.ryzhkov.typeclasses.MonoidInstances.pTableInstance
    import company.ryzhkov.typeclasses.MonoidSyntax._

    val table1 = PTable.instance(
      events = List("орел", "решка"),
      probabilities = List(0.5, 0.5)
    )

    val table2 = PTable.instance(
      events = List("красный", "синий", "зеленый"),
      probabilities = List(0.1, 0.2, 0.7)
    )

    val res = table1 <> table2

    println(res.show)
  }
}
