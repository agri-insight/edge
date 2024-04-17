package com.github.agriinsight.edge.data

import com.github.agriinsight.edge.Parser
import java.time.Instant

case class Temperature(timestamp: Instant, objectType: Long, objectNumber: Long, value: Double)

object Temperature:

  given Parser[Temperature] = (map: Map[String, String]) =>
    for
      timestampString    <- map.get("timestamp_unix")
      timestamp          <- timestampString.toLongOption
      objectTypeString   <- map.get("t_obj")
      objectType         <- objectTypeString.toLongOption
      objectNumberString <- map.get("n_obj")
      objectNumber       <- objectNumberString.toLongOption
      valueString        <- map.get("value")
      value              <- valueString.toDoubleOption
    yield Temperature(Instant.ofEpochMilli(timestamp), objectType, objectNumber, value)
