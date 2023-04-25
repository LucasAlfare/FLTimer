import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.data.SolvesManager
import com.lucasalfare.fltimer.core.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.data.session.SessionManager
import com.lucasalfare.fltimer.core.timer.TimerManager

suspend fun main() {
  readAndDefineFLTimerStateFromFile()

  setupManagers(
    FrameManager(),
    TimerManager(),
    SessionManager(),
    SolvesManager()
  )
}
