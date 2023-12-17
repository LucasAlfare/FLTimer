package com.lucasalfare.fltimer.core.statistics

import com.lucasalfare.fltimer.core.model.Penalty
import com.lucasalfare.fltimer.core.model.Solve

fun MutableList<Solve>.best(): StatisticResult {
  return if (size > 0) {
    val bestSolve = minByOrNull { it.time }
    StatisticResult(
      name = "best",
      result = bestSolve?.time ?: Long.MAX_VALUE,
      related = bestSolve?.let { mutableListOf(it) } ?: mutableListOf()
    )
  } else {
    StatisticResult.dnfResult
  }
}

fun MutableList<Solve>.worst(): StatisticResult {
  return if (size > 0) {
    val worstSolve = maxByOrNull { it.time }
    StatisticResult(
      name = "worst",
      result = worstSolve?.time ?: Long.MIN_VALUE,
      related = worstSolve?.let { mutableListOf(it) } ?: mutableListOf()
    )
  } else {
    StatisticResult.dnfResult
  }
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
  return if (size >= avgSize) {
    val data = subList(size - avgSize, size)
    calculateAverage(averageName = "current ao$avgSize", data = data)
  } else {
    StatisticResult.dnfResult
  }
}

fun MutableList<Solve>.bestAverageOf(avgSize: Int): StatisticResult {
  return if (size >= avgSize) {
    val search = collectAverages(averageName = "best ao$avgSize", data = this, size = avgSize)
      .minByOrNull { it.result } ?: StatisticResult.notCalculatedResult
    search
  } else {
    StatisticResult.dnfResult
  }
}

fun MutableList<Solve>.worstAverageOf(avgSize: Int): StatisticResult {
  return if (size >= avgSize) {
    val id = "worst ao$avgSize"
    val search = collectAverages(averageName = id, data = this, size = avgSize)
      .maxByOrNull { it.result } ?: StatisticResult.notCalculatedResult
    search
  } else {
    StatisticResult.dnfResult
  }
}