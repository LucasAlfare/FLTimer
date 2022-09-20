package lucasalfare.fltimer.core.networking


import io.socket.client.IO
import io.socket.client.Socket
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.EventManageable

class NetworkManager : EventManageable() {

  private var socket = IO.socket(LocalhostURI)

  override fun init() {
    socket.on(Socket.EVENT_CONNECT) { println("Connected to the server.") }
    socket.connect()
  }

  override fun onEvent(event: AppEvent, data: Any?) {
    when (event) {
      AppEvent.TimerCancel -> {
        socket.emit("hello", "android studio")
      }
      else -> {}
    }
  }
}
