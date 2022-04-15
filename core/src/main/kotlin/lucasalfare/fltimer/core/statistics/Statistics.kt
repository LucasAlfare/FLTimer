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
  averageName = "global avg",
  data = this
)

fun Solves.rollingAverage(avgSize: Int): StatisticResult? {
  if (size >= avgSize) {
    val range = this.values.toTypedArray().slice((size - avgSize) until (size))
    val data = Solves()
    range.forEach { data += it }
    return calculateAverage(averageName = "current ao$avgSize", data = data)
  }

  return null
}

fun Solves.bestAverageOf(avgSize: Int): StatisticResult? {
  if (size >= avgSize) {
    val averages = collectAverages(averageName = "best ao$avgSize", data = this, size = avgSize)

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
    val id = "worst ao$avgSize"
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

fun Solves.getStats() = mutableListOf(
  this.best(),
  this.worst(),
  this.rollingAverage(5),
  this.rollingAverage(12),
  this.rollingAverage(50),
  this.rollingAverage(100),
  this.rollingAverage(500),
  this.rollingAverage(1000),
  this.bestAverageOf(5),
  this.worstAverageOf(5),
  this.bestAverageOf(12),
  this.worstAverageOf(12),
  this.bestAverageOf(50),
  this.worstAverageOf(50),
  this.bestAverageOf(100),
  this.worstAverageOf(100),
  this.bestAverageOf(500),
  this.worstAverageOf(500),
  this.bestAverageOf(1000),
  this.worstAverageOf(1000),
  this.mean(),
  this.globalAverage(),
).filterNotNull()
