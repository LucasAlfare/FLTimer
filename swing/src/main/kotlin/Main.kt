import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.fltimer.core.configuration.ConfigurationsManager
import com.lucasalfare.fltimer.core.data.persistence.readAndDefineFLTimerStateFromFile
import com.lucasalfare.fltimer.core.scramble.ScrambleManager
import com.lucasalfare.fltimer.core.timer.TimerManager

suspend fun main() {
  readAndDefineFLTimerStateFromFile()

  // TODO: slower computers may not properly init the managers assynchrnously
  setupManagers(
    FrameManager(),
    TimerManager(),
    ConfigurationsManager(),
    ScrambleManager()
  )
}
