package company.ryzhkov

import company.ryzhkov.typeclasses.Monoid
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class MonoidTest extends AnyFreeSpec with Matchers {
  "List monoid" in {
    import company.ryzhkov.typeclasses.MonoidInstances.listMonoid
    import company.ryzhkov.typeclasses.MonoidSyntax.MonoidOps

    val list1 = List(1, 2, 3)
    val list2 = Nil
    val list3 = List(7, 8)

    val res = list1 <> list2 <> list3

    res shouldBe List(1, 2, 3, 7, 8)
  }

  "Endo monoid" in {
    import company.ryzhkov.typeclasses.MonoidInstances.endoInstance
    import company.ryzhkov.typeclasses.MonoidSyntax.MonoidOps

    val f1 = (x: String) => x.toUpperCase()
    val f2 = identity[String] _
    val f3 = (x: String) => s"$x!!!"

    val res = f1 <> f2 <> f3

    res("hello") shouldBe "HELLO!!!"
  }

  "Set monoid" in {
    import company.ryzhkov.typeclasses.MonoidInstances.scalaSetInstance
    import company.ryzhkov.typeclasses.MonoidSyntax.MonoidOps

    val s1 = Set(1, 3, 5)
    val s2 = Set(1, 2, 4)
    val s3 = Set(1, 6, 7)
    val s4 = Monoid[Set[Int]].empty

    val res = s1 <> s2 <> s3 <> s4

    res shouldBe Set(1, 2, 3, 4, 5, 6, 7)
  }

  "Option1 monoid" in {
    import company.ryzhkov.datatypes.OptionSyntax._
    import company.ryzhkov.typeclasses.MonoidInstances.{intSumMonoid, optionMonoid1}
    import company.ryzhkov.typeclasses.MonoidSyntax._

    val o1 = 3.some
    val o2 = none[Int]
    val o3 = 5.some

    val res1 = o1 <> o3
    val res2 = o1 <> o2

    res1 should contain(8)
    res2 shouldBe empty
  }

  "Option2 monoid" in {
    import company.ryzhkov.datatypes.OptionSyntax._
    import company.ryzhkov.typeclasses.MonoidInstances.optionMonoid2
    import company.ryzhkov.typeclasses.MonoidSyntax._

    val o1 = none[Int]
    val o2 = none[Int]
    val o3 = 5.some
    val o4 = none[Int]
    val o5 = 7.some

    val res = o1 <> o2 <> o3 <> o4 <> o5

    println(res)
  }
}
