package lucasalfare.fltimer.core.data.session

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import kotlin.random.Random


private const val StandardSessionName = "Standard"

class SessionManager : EventManageable() {

  private val sessions = mutableMapOf<String, Session>()
  private var currentSession: Session

  init {
    sessions[StandardSessionName] = Session(StandardSessionName)
    currentSession = sessions[StandardSessionName]!!

    tmpCreateSession("bilu teteia", 100)
    tmpCreateSession("repetiliano", 27)
  }

  private fun tmpCreateSession(name: String, nSolves: Int) {
    val testSession = Session(
      name = name
    )

    repeat(nSolves) {
      testSession.solves += Solve(time = 100 + Random.nextLong(60_000))
    }

    sessions[testSession.name] = testSession
  }

  override fun init() {
    notifyListeners(
      event = AppEvent.SessionsUpdate,
      data = arrayOf(currentSession.name, sessions),
      origin = this
    )
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      AppEvent.SessionsRequestUpdate -> {
        notifyListeners(
          event = AppEvent.SessionsUpdate,
          data = arrayOf(currentSession.name, sessions),
          origin = this
        )
      }

      AppEvent.SessionSwitch -> {
        val targetSessionName = data as String
        if (targetSessionName != currentSession.name) {
          currentSession = sessions[targetSessionName]!!
          notifyListeners(
            event = AppEvent.SessionsUpdate,
            data = arrayOf(currentSession.name, sessions),
            origin = this
          )
        }
      }
      else -> {}
    }
  }
}