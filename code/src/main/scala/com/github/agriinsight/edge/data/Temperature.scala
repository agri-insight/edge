package com.github.agriinsight.edge.data

import scala.util.Random

case class Temperature(timestamp: Long, objectType: Long, objectNumber: Long, value: Double)

object Temperature {

  private val randomProvider = new Random()

  def random: Temperature =
    Temperature(
      System.currentTimeMillis(),
      randomProvider.nextLong(100),
      randomProvider.nextLong(1000),
      randomProvider.nextDouble() * 100
    )
}
