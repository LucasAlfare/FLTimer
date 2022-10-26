package lucasalfare.fltimer.core.statistics

import lucasalfare.fltimer.core.data.Penalty
import lucasalfare.fltimer.core.data.Solves
import lucasalfare.fltimer.core.getCurrentTime
import lucasalfare.fltimer.core.toTimestamp
import java.text.SimpleDateFormat
import java.util.*
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
) {
  companion object {
    val notCalculatedResult = StatisticResult(name = "Not Calculated", result = -1L)
    val dnfResult = StatisticResult(name = "DNF statistic", result = -1L)
  }
}

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
): StatisticResult {
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

  return StatisticResult.dnfResult
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
    averages += calculateAverage(averageName = averageName, data = avg)
    if (i + size >= solves.size) break
    i++
  }
  return averages
}

fun Solves.buildAllStatisticResultsVisualization(): String {
  var result =
    """
      Generated by FLTimer in ${
      SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()).format(
        getCurrentTime()
      )
    }
      solves/total: ${this.size - this.nDNFs()}/${this.size}
      
      single
          best: ${this.best().result.toTimestamp()}
          worst: ${this.worst().result.toTimestamp()}
          
    """.trimIndent()

  val ao3 = arrayOf(
    this.rollingAverage(3),
    this.bestAverageOf(3),
    this.worstAverageOf(3)
  )

  val ao5 = arrayOf(
    this.rollingAverage(5),
    this.bestAverageOf(5),
    this.worstAverageOf(5)
  )

  val ao12 = arrayOf(
    this.rollingAverage(12),
    this.bestAverageOf(12),
    this.worstAverageOf(12)
  )

  val ao50 = arrayOf(
    this.rollingAverage(50),
    this.bestAverageOf(50),
    this.worstAverageOf(50)
  )

  val ao100 = arrayOf(
    this.rollingAverage(100),
    this.bestAverageOf(100),
    this.worstAverageOf(100)
  )

  val ao1000 = arrayOf(
    this.rollingAverage(1000),
    this.bestAverageOf(1000),
    this.worstAverageOf(1000)
  )

  if (ao3[0].name !== "DNF statistic") {
    result += """
      
      avg of 3
          current: ${ao3[0].result.toTimestamp()}
          best: ${ao3[1].result.toTimestamp()}
          worst: ${ao3[2].result.toTimestamp()}   
          
    """.trimIndent()
  }

  if (ao5[0].name !== "DNF statistic") {
    result += """
      
      avg of 5
          current: ${ao5[0].result.toTimestamp()}
          best: ${ao5[1].result.toTimestamp()}
          worst: ${ao5[2].result.toTimestamp()}   
          
    """.trimIndent()
  }

  if (ao12[0].name !== "DNF statistic") {
    result += """
      
      avg of 12
          current: ${ao12[0].result.toTimestamp()}
          best: ${ao12[1].result.toTimestamp()}
          worst: ${ao12[2].result.toTimestamp()}   
          
    """.trimIndent()
  }

  if (ao50[0].name !== "DNF statistic") {
    result += """
      
      avg of 50
          current: ${ao50[0].result.toTimestamp()}
          best: ${ao50[1].result.toTimestamp()}
          worst: ${ao50[2].result.toTimestamp()}  
           
    """.trimIndent()
  }

  if (ao100[0].name !== "DNF statistic") {
    result += """
      
      avg of 100
          current: ${ao100[0].result.toTimestamp()}
          best: ${ao100[1].result.toTimestamp()}
          worst: ${ao100[2].result.toTimestamp()}
          
    """.trimIndent()
  }

  if (ao1000[0].name !== "DNF statistic") {
    result += """
      
      avg of 1000
          current: ${ao1000[0].result.toTimestamp()}
          best: ${ao1000[1].result.toTimestamp()}
          worst: ${ao1000[2].result.toTimestamp()}
          
    """.trimIndent()
  }

  result += """
    
    Global mean: ${this.mean().result.toTimestamp()}
    Global average: ${this.globalAverage().result.toTimestamp()}
    
  """.trimIndent()

  result += """
    
  Times:    
  ${
    this.values.map { solve ->
      solve.time.toTimestamp()
    }.toString().replace("[", "").replace("]", "")
  }
  """.trimIndent()

  return result
}