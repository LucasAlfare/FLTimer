package lucasalfare.fltimer.core.data.session

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventListener
import lucasalfare.fltimer.core.Listenable
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.data.Solves


private const val StandardSessionName = "Standard"

class SessionManager : Listenable(), EventListener {

  private val sessions = mutableMapOf<String, Session>()
  private var currentSession: Session

  init {
    sessions[StandardSessionName] = Session(StandardSessionName)
    currentSession = sessions[StandardSessionName]!!

    val testSession = Session(
      name = "bilu teteia",
      solves = Solves(
        Solve(time = 12345),
        Solve(time = 54321),
        Solve(time = 23456),
        Solve(time = 65432)
      )
    )

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