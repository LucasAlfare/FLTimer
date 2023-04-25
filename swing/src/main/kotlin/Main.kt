import com.lucasalfare.fltimer.core.timer.asyncRoutine
import com.lucasalfare.fltimer.core.toTimestamp
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.JLabel

class Manager

class Main : JFrame() {

  val display = JLabel(0L.toTimestamp())

  init {
    setSize(600, 300)
    defaultCloseOperation = EXIT_ON_CLOSE
    layout = BorderLayout()
  }

  fun update() {
    asyncRoutine {

    }
  }
}