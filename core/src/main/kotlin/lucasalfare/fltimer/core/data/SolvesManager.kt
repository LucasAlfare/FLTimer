@file:Suppress("NON_EXHAUSTIVE_WHEN")

package lucasalfare.fltimer.core.data

import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import java.util.*

class SolvesManager : Listenable(), EventListener {

  private lateinit var currentSolves: Solves

  private var tmpTime = 0L
  private var tmpScramble = ""
  private var tmpPenalty = Penalty.Ok

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      SolvesUpdateRequest -> {
        notifyListeners(SolvesUpdate, currentSolves)
      }

      SessionsUpdate -> {
        val props = data as Array<*>
        val currentSessionName = props[0] as String
        val currentSession = (props[1] as MutableMap<*, *>)[currentSessionName]!! as Session
        currentSolves = currentSession.solves
        notifyListeners(SolvesUpdate, currentSolves)
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

        notifyListeners(SolvesUpdate, currentSolves)
      }

      SolvesItemUpdate -> {
        notifyListeners(SolvesUpdate, currentSolves)
      }

      SolvesItemRemove -> {
        currentSolves -= data as UUID
        notifyListeners(SolvesUpdate, currentSolves)
      }

      SolvesClear -> {
        currentSolves.clear()
        notifyListeners(SolvesUpdate, currentSolves)
      }
    }
  }
}
