import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.timer.TimerManager

suspend fun main() {
  setupManagers(
    FrameManager(),
    TimerManager()
  )
}