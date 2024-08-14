package com.lucasalfare.fltimer.c_infra.ui.swing

import com.lucasalfare.fltimer.b_usecase.TimerService
import com.lucasalfare.fltimer.toTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.FlowLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities

private class MySwingScreen(
  val timerService: TimerService
) : JFrame("Timer") {

  private val display = JLabel(0L.toTimestamp())
//  val scramble = JLabel("loading...")

  init {
    setSize(400, 200)
    defaultCloseOperation = 3
    setLocationRelativeTo(null)

    this.addKeyListener(object : KeyAdapter() {
      override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_SPACE) {
          runBlocking {
            timerService.onToggleDown()
          }
        }
      }

      override fun keyReleased(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_SPACE) {
          runBlocking {
            timerService.onToggleUp()
          }
        }
      }
    })

    layout = FlowLayout()

//    add(scramble)
    add(display)
  }

  fun startScreen() {
    SwingUtilities.invokeLater {
      this.requestFocus()
      isVisible = true
      startStateObserverCoroutine()
    }
  }

  private fun startStateObserverCoroutine() {
    CoroutineScope(Job()).launch {
      while (true) {
        display.text = timerService.timer.appDataState.timerCurrentDisplayTime.toTimestamp()
      }
    }
  }
}

class MySwingScreenLauncher(
  private val timerService: TimerService
) {

  fun launch() {
    MySwingScreen(timerService).startScreen()
  }
}