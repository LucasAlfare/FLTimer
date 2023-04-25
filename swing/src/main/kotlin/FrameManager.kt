import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.getCurrentTime
import com.lucasalfare.fltimer.core.toTimestamp
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class FrameManager : EventManageable() {

  private val frame = Frame()

  override fun onInitiated() {

  }

  override fun onNotInitiated() {
    frame.addKeyListener(object : KeyListener {
      override fun keyTyped(e: KeyEvent) {
        // pass
      }

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

    frame.display.isFocusable = false
    frame.isVisible = true
    frame.requestFocus()

    initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    if (event == FLTimerEvent.TimerUpdate) {
      frame.display.text = (data as Long).toTimestamp()
    }
  }
}