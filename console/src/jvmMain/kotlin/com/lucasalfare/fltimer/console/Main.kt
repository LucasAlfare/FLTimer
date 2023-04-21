package com.lucasalfare.fltimer.console

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.timer.TimerManager
import com.lucasalfare.fltimer.core.toTimestamp
import java.util.*

class ConsoleManager : EventManageable() {

  private val scanner = Scanner(System.`in`)
  private var state = false

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.TimerUpdate) {
      clear()
      println((data as Long).toTimestamp())
    }
  }

  override fun onInitiated() {
    Thread {
      while (true) {
        val typing = scanner.nextLine()
        if (typing == "") {
          state = if (!state) {
            notifyListeners(FLTimerEvent.TimerToggleUp, getCurrentTime())
            !state
          } else {
            notifyListeners(FLTimerEvent.TimerToggleDown, getCurrentTime())
            notifyListeners(FLTimerEvent.TimerToggleUp, getCurrentTime())
            !state
          }
        }
      }
    }.start()
  }

  override fun onNotInitiated() {

  }

  private fun clear() {
    // source: https://stackoverflow.com/a/33525703
    if (System.getProperty("os.name").contains("Windows")) {
      ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    } else {
      Runtime.getRuntime().exec("clear")
    }
  }
}

suspend fun main() {
  setupManagers(
    TimerManager(),
    ConsoleManager()
  )
}