package company.ryzhkov

object Util {
  def const[A, B]: A => B => A = a => _ => a
}
