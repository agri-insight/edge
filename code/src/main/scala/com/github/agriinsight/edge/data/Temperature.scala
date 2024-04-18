package com.github.agriinsight.edge.data

import scala.util.Random

case class Temperature(timestamp: Long, objectType: Long, objectNumber: Long, value: Double)

object Temperature {

  private val randomProvider = new Random

  def random: Temperature =
    Temperature(
      timestamp = System.currentTimeMillis(),
      objectType = randomProvider.nextLong(100),
      objectNumber = randomProvider.nextLong(1000),
      value = randomProvider.nextDouble() * 100
    )
}
