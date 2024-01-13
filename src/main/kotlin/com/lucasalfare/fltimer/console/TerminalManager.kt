package com.lucasalfare.fltimer.console

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.Event
import com.lucasalfare.fltimer.getCurrentTime
import com.lucasalfare.fltimer.toTimestamp
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger


class MyTerminal(
  var onEventDown: () -> Unit = {},
  var onEventUp: () -> Unit = {}
) {
  init {
    runCatching {
      // attaches the global hook
      GlobalScreen.registerNativeHook()

      // turn all automatic log tracking off
      val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
      LogManager.getLogManager().reset()
      logger.setLevel(Level.OFF)
      logger.setUseParentHandlers(false)
    }.onSuccess {
      // define a global native listener that uses custom callbacks
      GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
        override fun nativeKeyPressed(e: NativeKeyEvent) {
          if (e.keyCode == NativeKeyEvent.VC_SPACE) {
            onEventDown()
          }
        }

        override fun nativeKeyReleased(e: NativeKeyEvent) {
          if (e.keyCode == NativeKeyEvent.VC_SPACE) {
            onEventUp()
          }
        }

        override fun nativeKeyTyped(e: NativeKeyEvent) {
          /*pass*/
        }
      })

      // when everything ok, just clears the "screen"
      this.render("")
    }.onFailure {
      error("BUG!!!!")
    }
  }

  fun render(data: Any) {
    clear()
    this.print(data)
  }

  private fun print(something: Any) {
    println(something)
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

// TODO: this must be deprecated and untouched until we find a way to detect
// TODO: "terminal" window state changes, such as "minimize" and "un-minimize".
class TerminalManager : EventManageable() {

  private lateinit var myTerminal: MyTerminal

  override suspend fun initialize() {
    myTerminal = MyTerminal(
      onEventUp = {
        this.notifyListeners(Event.TimerToggleUp, getCurrentTime())
      },
      onEventDown = {
        this.notifyListeners(Event.TimerToggleDown, getCurrentTime())
      }
    )

    initialized = true
  }

  override fun onEvent(event: Any, data: Any?) {
    if (event == Event.TimerSolveUpdate) {
      myTerminal.render((data as Long).toTimestamp())
    }
  }
}

fun main() {

}