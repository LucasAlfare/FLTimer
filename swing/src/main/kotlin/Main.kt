import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.configuration.ConfigurationsManager
import com.lucasalfare.fltimer.core.FLTimerState
import com.lucasalfare.fltimer.core.data.SolvesManager
import com.lucasalfare.fltimer.core.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.data.session.SessionManager
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager

suspend fun main() {
  readAndDefineFLTimerStateFromFile()

  println(FLTimerState.getFLTimerState())

  setupManagers(
    FrameManager(),
    TimerManager(),
    SessionManager(),
    SolvesManager(),
    ConfigurationsManager(),
    ScrambleManager()
  )
}
