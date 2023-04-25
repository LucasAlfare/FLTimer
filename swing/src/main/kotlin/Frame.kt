import com.lucasalfare.fltimer.core.toTimestamp
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.JLabel

class Frame : JFrame() {

  val display = JLabel(0L.toTimestamp())

  init {
    setSize(600, 300)
    defaultCloseOperation = EXIT_ON_CLOSE
    layout = BorderLayout()

    this.add(display, BorderLayout.NORTH)
  }
}