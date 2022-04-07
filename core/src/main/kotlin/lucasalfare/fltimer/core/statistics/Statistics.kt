/**
 * This file contains all the functions to get statistics
 * related to speedcubing.
 *
 * Also, this file works as extending its function to all instances
 * of a [Solves] object, in order to keep easy retrieve all
 * results from a single data input.
 */
package lucasalfare.fltimer.core.statistics

import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solves
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

enum class Statistic {
  Best,
  Worst,
  Mean,
  GlobalAverage,
  RollingAverage,
  BestAverageOf,
  WorstAverageOf,
  StandardDeviation
}

fun Solves.best(): StatisticResult? {
  if (size > 0) {
    var result = Long.MAX_VALUE
    var related: UUID? = null
    this.values.forEach {
      if (it.time < result) {
        result = it.time
        related = it.id
      }
    }

    return StatisticResult(
      name = "best",
      result = result,
      related = Solves(this[related]!!)
    )
  }

  return null
}

fun Solves.worst(): StatisticResult? {
  if (size > 0) {
    var result = Long.MIN_VALUE
    var related: UUID? = null
    this.values.forEach {
      if (it.time > result) {
        result = it.time
        related = it.id
      }
    }

    return StatisticResult(
      name = "worst",
      result = result,
      related = Solves(this[related]!!)
    )
  }

  return null
}

fun Solves.mean(): StatisticResult? {
  if (size >= 2) {
    var result = 0L
    val related = Solves()
    val skipped = Solves()
    var nCountingTimes = size
    this.values.forEach {
      if (it.penalty != Penalty.Dnf) {
        result += it.time
        related += it
      } else {
        skipped += it
        nCountingTimes--
      }
    }

    return StatisticResult(
      name = "mean",
      result = result / nCountingTimes,
      related = related,
      skipped = skipped
    )
  }

  return null
}

fun Solves.globalAverage() = calculateAverage(
  averageName = "global average",
  data = this
)

fun Solves.rollingAverage(avgSize: Int): StatisticResult? {
  if (size >= avgSize) {
    val range = this.values.toTypedArray().slice((size - avgSize) until (size))
    val data = Solves()
    range.forEach { data += it }
    return calculateAverage(averageName = "average of $avgSize", data = data)
  }

  return null
}

fun Solves.bestAverageOf(avgSize: Int): StatisticResult? {
  if (size >= avgSize) {
    val averages = collectAverages(averageName = "best avg of $avgSize", data = this, size = avgSize)

    var min = Long.MAX_VALUE
    var search: StatisticResult? = null
    averages.forEach {
      if (it.result < min) {
        min = it.result
        search = it
      }
    }

    return search
  }

  return null
}

fun Solves.worstAverageOf(avgSize: Int): StatisticResult? {
  if (size >= avgSize) {
    val id = "worst avg of $avgSize"
    val averages = collectAverages(averageName = id, data = this, size = avgSize)

    var max = Long.MIN_VALUE
    var search: StatisticResult? = null
    averages.forEach {
      if (it.result > max) {
        max = it.result
        search = it
      }
    }

    return search
  }

  return null
}

fun Solves.standardDeviation(): Long {
  val solves = this.values
  if (solves.size == 0) return 0L
  var mean = 0.0
  var variance = 0.0
  solves.forEach { mean += it.time }
  mean /= size
  solves.forEach { variance += (it.time - mean).pow(2.0) }
  variance /= size
  return sqrt(variance).toLong()
}

/**
 * Returns a map of all statistics.
 */
fun Solves.getAllStatistics() = mapOf(
  Pair(Statistic.Best, this.best()),
  Pair(Statistic.Worst, this.worst()),
  Pair(Statistic.Mean, this.mean()),
  Pair(Statistic.GlobalAverage, this.globalAverage()),
  Pair(Statistic.RollingAverage, this.rollingAverage(5)),
  Pair(Statistic.RollingAverage, this.rollingAverage(12)),
  Pair(Statistic.BestAverageOf, this.bestAverageOf(5)),
  Pair(Statistic.WorstAverageOf, this.worstAverageOf(5)),
  Pair(Statistic.BestAverageOf, this.bestAverageOf(12)),
  Pair(Statistic.WorstAverageOf, this.worstAverageOf(12)),
  Pair(Statistic.BestAverageOf, this.bestAverageOf(50)),
  Pair(Statistic.WorstAverageOf, this.worstAverageOf(50)),
  Pair(Statistic.BestAverageOf, this.bestAverageOf(100)),
  Pair(Statistic.WorstAverageOf, this.worstAverageOf(100)),
  Pair(Statistic.StandardDeviation, this.standardDeviation()),
)