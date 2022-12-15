package lucasalfare.fltimer.core.data.session

import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.data.Session
import lucasalfare.fltimer.core.data.Solve
import kotlin.random.Random


const val DefaultSessionName = "Default"

class SessionManager : EventManageable() {

  private var sessions = mutableMapOf<String, Session>()
  private lateinit var currentActiveSession: Session

  init {
    sessions[DefaultSessionName] = Session(DefaultSessionName)
    currentActiveSession = sessions[DefaultSessionName]!!

    tmpCreateSession("bilu teteia", 10)
    //tmpCreateSession("repetiliano", 50)
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
      data = arrayOf(currentActiveSession.name, sessions),
      origin = this
    )

    initiated = true
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      AppEvent.SessionsRequestUpdate -> {
        notifyListeners(
          event = AppEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      AppEvent.SessionSwitch -> {
        val targetSessionName = data as String
        currentActiveSession = sessions[targetSessionName]!!

        println("SessionSwitch requested with arg $targetSessionName. The resulting session is\n${currentActiveSession}")

        notifyListeners(
          event = AppEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      AppEvent.PersistenceUpdate -> {
        val props = ((data as Array<*>)[1]) as Array<*>
        val targetSessionName = props[0] as String
        currentActiveSession = sessions[targetSessionName]!!

        sessions = props[1] as MutableMap<String, Session>

        notifyListeners(
          event = AppEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      else -> {}
    }
  }
}