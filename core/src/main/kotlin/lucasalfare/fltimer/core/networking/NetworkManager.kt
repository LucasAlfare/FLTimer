package lucasalfare.fltimer.core.networking


import io.socket.client.IO
import io.socket.client.Socket
import lucasalfare.fltimer.core.AppEvent
import lucasalfare.fltimer.core.AppEvent.*
import lucasalfare.fltimer.core.EventManageable
import lucasalfare.fltimer.core.data.Solve
import lucasalfare.fltimer.core.data.Solves
import org.json.JSONArray
import org.json.JSONObject

class NetworkManager : EventManageable() {

  private var socket = IO.socket(LocalhostURI)

  private lateinit var users: MutableList<User>

  override fun init() {
    socket.on(Socket.EVENT_CONNECT) {
      println("Connected to the server.")
    }

    socket.on("NetworkingUsersUpdate") {
      users = parseServerUsers(it[0] as JSONArray)
      users.forEach { u -> println(u) }
      notifyListeners(NetworkingUsersUpdate, users)
    }

    socket.on("NetworkingAllUsersFinished") {
      val data = it[0] as Boolean
      notifyListeners(NetworkingAllUsersFinished, data)
    }

    socket.connect()
  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      TimerToggleUp -> {
        socket.emit("TimerToggle", data as Long)
      }
      else -> {}
    }
  }
}
