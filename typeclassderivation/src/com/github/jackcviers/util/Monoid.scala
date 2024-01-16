package com.github.jackcviers.util

trait Monoid[A] {
  def empty: A
  def combine(a: A, b: A): A
}

object Monoid {
  def apply[A](implicit m: Monoid[A]): Monoid[A] = m

  implicit object stringMonoid extends Monoid[String] {
    def empty = ""
    def combine(a: String, b: String) =
      new StringBuilder().append(a).append(b).toString
  }

  implicit object additiveMonoidInt extends Monoid[Int] {
    def empty = 0
    def combine(a: Int, b: Int): Int = a + b
  }

  object multiplicativeMonoidInt extends Monoid[Int] {
    def empty = 1
    def combine(a: Int, b: Int): Int = a * b
  }

  implicit object andMonoidBoolean extends Monoid[Boolean] {
    def empty = true
    def combine(a: Boolean, b: Boolean): Boolean = a && b
  }

  object orMonoidBoolean extends Monoid[Boolean] {
    def empty = false
    def combine(a: Boolean, b: Boolean): Boolean = a || b
  }

  implicit object charMonoid extends Monoid[Char] {
    def empty = Char.MinValue
    def combine(a: Char, b: Char): Char = (a + b).toChar
  }
}
