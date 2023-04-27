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

  FLTimerState.getCurrentActiveSession().solves.forEach { println(it) }

  // TODO: slower computers may not properly init the managers assynchrnously
  setupManagers(
    FrameManager(),
    TimerManager(),
    SessionManager(),
    SolvesManager(),
    ConfigurationsManager(),
    ScrambleManager()
  )
}
