package com.github.agriinsight.edge.data

import com.github.agriinsight.edge.Parser
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write

import java.time.Instant

case class Temperature(timestamp: Instant, objectType: Long, objectNumber: Long, value: Double)

object Temperature:

  val Parser: Parser = (map: Map[String, String]) =>
    for
      timestampString    <- map.get("timestamp_unix")
      timestamp          <- timestampString.toLongOption
      objectTypeString   <- map.get("t_obj")
      objectType         <- objectTypeString.toLongOption
      objectNumberString <- map.get("n_obj")
      objectNumber       <- objectNumberString.toLongOption
      valueString        <- map.get("value")
      value              <- valueString.toDoubleOption
    yield write(Temperature(Instant.ofEpochMilli(timestamp), objectType, objectNumber, value))(DefaultFormats)
