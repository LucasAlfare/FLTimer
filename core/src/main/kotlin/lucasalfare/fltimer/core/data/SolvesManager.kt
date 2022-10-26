@file:Suppress("NON_EXHAUSTIVE_WHEN")

package lucasalfare.fltimer.core.data

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.statistics.buildAllStatisticResultsVisualization
import lucasalfare.fltimer.core.statistics.getStats
import java.util.*

class SolvesManager : EventManageable() {

  private lateinit var currentSolves: Solves

  private var tmpTime = 0L
  private var tmpScramble = ""
  private var tmpPenalty = Penalty.Ok

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      SolvesUpdateRequest -> {
        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      SessionsUpdate -> {
        val props = data as Array<*>
        val currentSessionName = props[0] as String
        val currentSession = (props[1] as MutableMap<*, *>)[currentSessionName]!! as Session
        currentSolves = currentSession.solves
        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      ScrambleGenerated -> {
        val props = data as Array<*>
        tmpScramble = props[1] as String
      }

      TimerInspectionUpdate -> {
        val props = data as Array<*>
        tmpPenalty = props[1] as Penalty
      }

      TimerFinished -> {
        tmpTime = data as Long

        val solve = Solve(
          time = tmpTime,
          scramble = tmpScramble,
          penalty = tmpPenalty
        )

        currentSolves += solve

        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      SolvesItemUpdate -> {
        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      SolvesItemRemove -> {
        currentSolves -= data as UUID
        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      SolvesClear -> {
        currentSolves.clear()
        notifyListeners(event = SolvesUpdate, data = currentSolves, origin = this)
      }

      StatisticRequest -> {
        val statisticResponseData = when (data as String) {
          "all" -> {
            currentSolves.buildAllStatisticResultsVisualization()
          }

          else -> {
            "lalalala"
          }
        }

        notifyListeners(event = StatisticsResponse, data = statisticResponseData, origin = this)
      }

      else -> {}
    }
  }
}
