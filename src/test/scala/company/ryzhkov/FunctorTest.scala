package company.ryzhkov

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class FunctorTest extends AnyFreeSpec with Matchers {

  "Functor for either" in {
    import company.ryzhkov.typeclasses.FunctorSyntax._
    import company.ryzhkov.typeclasses.FunctorInstances.eitherFunctorInstance

    val e1: Either[String, Double] = Right(0.25)
    val e2: Either[String, String] = Left("error!")

    val res1 = e1.fmap(_.toInt)
    val res2 = e2.fmap(_.toUpperCase)

    res1 shouldBe Right(0)
    res2 shouldBe Left("error!")
  }
}
