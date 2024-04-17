package com.github.agriinsight.edge

trait Parser[+T <: AnyRef]:
  def apply(map: Map[String, String]): Option[T]
