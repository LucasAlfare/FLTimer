import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.data.persistence.writeFLTimerStateToFile
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.toTimestamp
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class FrameManager : EventManageable() {

  private val frame = Frame()
  private var currentScramble = ""

  override fun onInitiated() {
//    println("[FrameManager] Instance initiated.")
    setupFrame()
  }

  override fun onNotInitiated() {
    if (currentScramble != "") {
      initiated = true
    }
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.TimerUpdate) {
      frame.display.text = (data as Long).toTimestamp()
    } else if (event == FLTimerEvent.ScrambleGenerated) {
      val props = data as Array<*>
      currentScramble = props[1] as String
      frame.scramble.text = currentScramble
    }
  }

  private fun setupFrame() {
    frame.addKeyListener(object : KeyAdapter() {
      override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == 32) {
          this@FrameManager.notifyListeners(FLTimerEvent.TimerToggleDown, getCurrentTime())
        }
      }

      override fun keyReleased(e: KeyEvent) {
        if (e.keyCode == 32) {
          this@FrameManager.notifyListeners(FLTimerEvent.TimerToggleUp, getCurrentTime())
        }
      }
    })

    frame.addWindowListener(object : WindowAdapter() {
      override fun windowClosing(e: WindowEvent?) {
        super.windowClosing(e)
        println("[FrameManager] Closing application and writing state to file...")
        writeFLTimerStateToFile()
      }
    })

    frame.display.isFocusable = false
    frame.isVisible = true
    frame.requestFocus()
  }
}