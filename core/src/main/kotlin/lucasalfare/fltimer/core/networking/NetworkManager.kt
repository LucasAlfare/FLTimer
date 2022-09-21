package lucasalfare.fltimer.core.networking


import io.socket.client.IO
import io.socket.client.Socket
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.toTimestamp
import org.json.JSONObject

class NetworkManager : EventManageable() {

  private var socket = IO.socket(LocalhostURI)

  override fun init() {
    socket.on(Socket.EVENT_CONNECT) { println("Connected to the server.") }

    socket.on("UserTimerFinished") {
      println("a user finished a solve: ${(it[0] as JSONObject).getString("id")}")
    }

    socket.connect()
  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      AppEvent.TimerFinished -> {
        socket.emit("TimerFinished", data as Long)
      }
      else -> {}
    }
  }
}
