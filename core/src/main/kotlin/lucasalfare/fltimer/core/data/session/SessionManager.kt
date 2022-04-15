package lucasalfare.fltimer.core.data.session

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import kotlin.random.Random


private const val StandardSessionName = "Standard"

class SessionManager : Listenable(), EventListener {

  private val sessions = mutableMapOf<String, Session>()
  private var currentSession: Session

  init {
    sessions[StandardSessionName] = Session(StandardSessionName)
    currentSession = sessions[StandardSessionName]!!

    tmpCreateSession("bilu teteia", 500)
    tmpCreateSession("repetiliano", 27)
  }

  private fun tmpCreateSession(name: String, nSolves: Int) {
    val testSession = Session(
      name = name
    )

    repeat(nSolves) {
      testSession.solves += Solve(time = 500 + Random.nextLong(120000))
    }

    sessions[testSession.name] = testSession
  }

  override fun init() {
    notifyListeners(AppEvent.SessionsUpdate, arrayOf(currentSession.name, sessions))
  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      AppEvent.SessionSwitch -> {
        val targetSessionName = data as String
        if (targetSessionName != currentSession.name) {
          currentSession = sessions[targetSessionName]!!
          notifyListeners(AppEvent.SessionsUpdate, arrayOf(currentSession.name, sessions))
        }
      }
    }
  }
}