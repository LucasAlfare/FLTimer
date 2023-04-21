package lucasalfare.fltimer.console

import com.lucasalfare.fltimer.core.*
import com.lucasalfare.fltimer.core.timer.TimerManager
import java.util.*

class ConsoleManager : EventManageable() {

  private val scanner = Scanner(System.`in`)
  private var state = false

  override fun init() {
    Thread {
      while (true) {
        val typing = scanner.nextLine()
        if (typing == "") {
          state = if (!state) {
            notifyListeners(AppEvent.TimerToggleUp, getCurrentTime())
            !state
          } else {
            notifyListeners(AppEvent.TimerToggleDown, getCurrentTime())
            notifyListeners(AppEvent.TimerToggleUp, getCurrentTime())
            !state
          }
        }
      }
    }.start()
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    if (event == AppEvent.TimerUpdate) {
      clear()
      println((data as Long).toTimestamp())
    }
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