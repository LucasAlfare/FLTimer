package com.lucasalfare.fltimer.core.statistics

import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve
import java.util.*

fun MutableList<Solve>.best(): StatisticResult {
  if (size > 0) {
    var result = Long.MAX_VALUE
    var related: UUID? = null
    this.forEach {
      if (it.time < result) {
        result = it.time
        related = it.id
      }
    }

    return StatisticResult(
      name = "best",
      result = result,
      related = mutableListOf(this.first { it.id == related })
    )
  }

  return StatisticResult.dnfResult
}

fun MutableList<Solve>.worst(): StatisticResult {
  if (size > 0) {
    var result = Long.MIN_VALUE
    var related: UUID? = null
    this.forEach {
      if (it.time > result) {
        result = it.time
        related = it.id
      }
    }

    return StatisticResult(
      name = "worst",
      result = result,
      related = mutableListOf(this.first { it.id == related })
    )
  }

  return StatisticResult.dnfResult
}

fun MutableList<Solve>.mean(): StatisticResult {
  if (size >= 2) {
    var result = 0L
    val related = mutableListOf<Solve>()
    val skipped = mutableListOf<Solve>()
    var nCountingTimes = size
    this.forEach { s ->
      if (s.penalty != Penalty.Dnf) {
        result += s.time
        related += s
      } else {
        skipped += s
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

  return StatisticResult.dnfResult
}

fun MutableList<Solve>.globalAverage() = calculateAverage(
  averageName = "global avg",
  data = this
)

fun MutableList<Solve>.rollingAverage(avgSize: Int): StatisticResult {
  if (size >= avgSize) {
    val range = this.toTypedArray().slice((size - avgSize) until (size))
    val data = mutableListOf<Solve>()
    range.forEach { data += it }
    return calculateAverage(averageName = "current ao$avgSize", data = data)
  }

  return StatisticResult.dnfResult
}

fun MutableList<Solve>.bestAverageOf(avgSize: Int): StatisticResult {
  if (size >= avgSize) {
    val averages = collectAverages(averageName = "best ao$avgSize", data = this, size = avgSize)

    var min = Long.MAX_VALUE
    var search = StatisticResult.notCalculatedResult
    averages.forEach {
      if (it.result < min) {
        min = it.result
        search = it
      }
    }

    return search
  }

  return StatisticResult.dnfResult
}

fun MutableList<Solve>.worstAverageOf(avgSize: Int): StatisticResult {
  if (size >= avgSize) {
    val id = "worst ao$avgSize"
    val averages = collectAverages(averageName = id, data = this, size = avgSize)

    var max = Long.MIN_VALUE
    var search = StatisticResult.notCalculatedResult
    averages.forEach {
      if (it.result > max) {
        max = it.result
        search = it
      }
    }

    return search
  }

  return StatisticResult.dnfResult
}