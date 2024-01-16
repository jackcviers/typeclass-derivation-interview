package com.github.jackcviers.util

trait Show[A] {
  def show(a: A): String
}

object Show {
  def apply[A](implicit s: Show[A]) = s

  implicit object ShowString extends Show[String] {
    override def show(a: String): String = a
  }

  implicit object ShowChar extends Show[Char] {
    override def show(a: Char): String = a.toString()
  }

  implicit object ShowInt extends Show[Int] {
    override def show(a: Int): String = a.toString()
  }

  implicit object ShowShort extends Show[Short] {
    override def show(a: Short): String = a.toString()
  }

  implicit object ShowLong extends Show[Long] {
    override def show(a: Long): String = a.toString()
  }

  implicit object ShowFloat extends Show[Float] {
    override def show(a: Float): String = a.toString()
  }

  implicit object ShowDouble extends Show[Double] {
    override def show(a: Double): String = a.toString()
  }

  implicit object ShowBigInt extends Show[BigInt] {
    override def show(a: BigInt): String = a.toString()
  }

  implicit object ShowBigDecimal extends Show[BigDecimal] {
    override def show(a: BigDecimal): String = a.toString()
  }

  object ops {
    implicit class showOps[A: Show](val underlying: A) {
      def show: String = Show[A].show(underlying)
    }
  }

}
