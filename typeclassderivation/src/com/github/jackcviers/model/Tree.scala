package com.github.jackcviers.model

import com.github.jackcviers.util.Monoid
import com.github.jackcviers.util.Show

sealed trait Tree[A] {

  def fold[B: Monoid](b: => B)(ifLeaf: (=> B, Tree[A]) => B): B
}

final case class Leaf[A](value: A) extends Tree[A] {
  override def fold[B: Monoid](b: => B)(ifLeaf: (=> B, Tree[A]) => B): B =
    ifLeaf(b, this)
}

object Leaf {
  implicit def show[A]: Show[Leaf[A]] = ???
}

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
  override def fold[B: Monoid](b: => B)(ifLeaf: (=> B, Tree[A]) => B): B =
    Monoid[B].combine(left.fold(b)(ifLeaf), right.fold(b)(ifLeaf))
}

object Branch {
  implicit def show[A]: Show[Branch[A]] = ???
}

final case class Empty[A]() extends Tree[A] {
  override def fold[B: Monoid](b: => B)(
      ifLeaf: (=> B, Tree[A]) => B
  ): B = b
}

object Empty {
  implicit def show[A]: Show[Empty[A]] = ???
}

object Tree {

  /** Creates an empty tree
    *
    * @return
    */
  def empty[A]: Tree[A] = Empty()

  /** Creates a leaf from a value. If the value is null, returns the empty tree.
    *
    * @param a
    * @return
    */
  def leaf[A](a: A): Tree[A] = Option(a).map(Leaf.apply).getOrElse(empty)

  /** Creates a branch from two trees.
    *
    * @param left
    * @param right
    * @return
    */
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

  /** Creates a Tree from a list of values. This tree is unbalanced and
    * naiive, no depth gaurantees are included, only useful for the
    * typeclass derivation principles.
    *
    * @param value
    * @param values
    * @return
    */
  def apply[A](value: A, values: A*): Tree[A] =
    values.toSeq.foldLeft(leaf(value)) { (acc: Tree[A], next: A) =>
      acc match {
        case Empty() =>
          leaf(next)
        case l @ Leaf(_) =>
          branch(l, leaf(next))
        case Branch(Leaf(_), Leaf(_)) =>
          branch(acc, leaf(next))
        case Branch(Branch(l, Empty()), Empty()) =>
          branch(l, leaf(next))
        case Branch(l @ Branch(_, _), Empty()) =>
          branch(l, leaf(next))
        case Branch(l @ Branch(_, _), Branch(rl, Empty())) =>
          branch(l, branch(rl, leaf(next)))
        case Branch(_, _) =>
          branch(acc, leaf(next))
      }
    }

  /** Prints a tree
    *
    * @return
    */
  implicit def show[A]: Show[Tree[A]] = ???
}
