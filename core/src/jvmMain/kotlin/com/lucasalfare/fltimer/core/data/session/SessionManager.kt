package com.lucasalfare.fltimer.core.data.session

import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.data.Session
import com.lucasalfare.fltimer.core.data.Solve
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

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      FLTimerEvent.SessionsRequestUpdate -> {
        notifyListeners(
          event = FLTimerEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      FLTimerEvent.SessionSwitch -> {
        val targetSessionName = data as String
        currentActiveSession = sessions[targetSessionName]!!

        println("SessionSwitch requested with arg $targetSessionName. The resulting session is\n${currentActiveSession}")

        notifyListeners(
          event = FLTimerEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      FLTimerEvent.PersistenceUpdate -> {
        val props = ((data as Array<*>)[1]) as Array<*>
        val targetSessionName = props[0] as String
        currentActiveSession = sessions[targetSessionName]!!

        sessions = props[1] as MutableMap<String, Session>

        notifyListeners(
          event = FLTimerEvent.SessionsUpdate,
          data = arrayOf(currentActiveSession.name, sessions),
          origin = this
        )
      }

      else -> {}
    }
  }

  override fun onInitiated() {
    notifyListeners(
      event = FLTimerEvent.SessionsUpdate,
      data = arrayOf(currentActiveSession.name, sessions),
      origin = this
    )

    initiated = true
  }

  override fun onNotInitiated() {

  }
}