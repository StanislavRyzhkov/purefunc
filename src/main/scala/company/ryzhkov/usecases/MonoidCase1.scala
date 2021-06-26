package company.ryzhkov.usecases

object MonoidCase1 extends App {

  def step(word: String) = (1, word.length, Map(word -> 1))

  val data = List("asd", "asd", "qwe")

}
