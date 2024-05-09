package com.github.agriinsight.edge.data

import scala.util.Random

case class Temperature(timestamp: Long, value: Double)

object Temperature {

  private val randomProvider = new Random

  def random(minInclusive: Double, maxExclusive: Double): Temperature =
      Temperature(
        timestamp = System.currentTimeMillis(),
        value = randomProvider.between(minInclusive, maxExclusive)
    )
}
