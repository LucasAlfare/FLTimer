package lucasalfare.fltimer.core.statistics

import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solves
import kotlin.math.ceil

/**
 * Helper field to avoid intense calculation of `5%` factor.
 */
internal const val FivePercent = 5.0 / 100.0

/**
 * Class representing the main output result of a statistic.
 *
 * @param [name] a string representation name of the result;
 * @param [result] the numeric value calculated to this result;
 * @param [related] all times (IDs are pointing to times) used to reach this result;
 * @param [skipped] all values that was skipped in calculation of this result. Can or not be set.
 */
data class StatisticResult(
  val name: String,
  val result: Long,
  val related: Solves = Solves(),
  val skipped: Solves = Solves()
)

/**
 * Function used to abstract the average calculation of a list of times
 * based on its params.
 *
 * @params [averageName] The kind of average that is being calculated;
 * @param [data] source of elements that should have its average calculated.
 */
internal fun calculateAverage(
  averageName: String,
  data: Solves
): StatisticResult? {
  val solves = data.values
  //always rounds the percentile up
  val nSkips = ceil(FivePercent * solves.size).toInt()
  val nNotCountingTimes = (nSkips * 2)

  if (solves.size > nNotCountingTimes) {
    val skipped = Solves()
    //sorts by time then all min times will be at beginning and max at end
    val sortedSolves = solves.sortedBy { it.time }

    for (i in 0 until nSkips) {
      skipped += sortedSolves[i]
      skipped += sortedSolves[sortedSolves.size - nSkips + i]
    }

    var result = 0L
    val related = Solves()
    solves.forEach {
      if (!skipped.contains(it.id)) {
        if (it.penalty == Penalty.Dnf) {
          return StatisticResult(name = averageName, result = Long.MAX_VALUE)
        }

        result += it.time
      }
      related += it
    }

    return StatisticResult(
      name = averageName,
      result = result / (solves.size - nNotCountingTimes),
      related = related,
      skipped = skipped
    )
  }

  return null
}

/**
 * Function to take groups of sub averages on each [size] length.
 */
internal fun collectAverages(
  averageName: String,
  data: Solves,
  size: Int
): MutableList<StatisticResult> {
  val solves = data.values.toTypedArray()
  val averages = mutableListOf<StatisticResult>()
  var i = 0
  while (i < solves.size) {
    val avg = Solves()
    var j = 0
    while (j < size) {
      if (j >= solves.size) break
      avg += solves[i + j++]
    }
    averages += calculateAverage(averageName = averageName, data = avg)!!
    if (i + size >= solves.size) break
    i++
  }
  return averages
}
