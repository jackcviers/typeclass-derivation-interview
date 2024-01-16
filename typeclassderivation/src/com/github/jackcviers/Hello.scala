package com.github.jackcviers

import com.github.jackcviers.model.Tree
import com.github.jackcviers.util.Show.ops._

object Hello extends App with Greeting {
  println(msg.show)
  println(msg.fold("")((s, t) => s ++ t.show ++ ", "))
}

sealed trait Greeting {
  val msg = Tree[Int](1, 2, 3, 4, 5, 6, 7)
}
