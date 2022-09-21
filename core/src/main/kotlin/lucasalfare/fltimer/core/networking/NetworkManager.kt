package lucasalfare.fltimer.core.networking


import io.socket.client.IO
import io.socket.client.Socket
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.toTimestamp
import org.json.JSONObject

class NetworkManager : EventManageable() {

  private var socket = IO.socket(LocalhostURI)

  override fun init() {
    socket.on(Socket.EVENT_CONNECT) { println("Connected to the server.") }
    socket.connect()

    println("NetworkingManager: $listeners")
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      TimerToggleDown, TimerToggleUp -> {
        println("event=$event, data=$data, origin=$origin")
      }
      else -> {}
    }
  }
}
