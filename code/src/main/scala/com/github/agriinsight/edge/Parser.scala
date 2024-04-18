package com.github.agriinsight.edge

trait Parser {
  def apply(map: Map[String, String]): Option[String]
}
