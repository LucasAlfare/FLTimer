package com.lucasalfare.fltimer.core.networking


import com.lucasalfare.fllistener.EventManageable
import io.socket.client.IO
import io.socket.client.Socket
import com.lucasalfare.fltimer.core.FLTimerEvent.*
import org.json.JSONArray

class NetworkManager : EventManageable() {

  private var socket = IO.socket(LocalhostURI)

  private lateinit var users: MutableList<User>

  override fun onInitiated() {

  }

  override fun onNotInitiated() {
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
      println(data)
      notifyListeners(NetworkingAllUsersFinished, data)
    }

    socket.connect()
    initiated = true
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      TimerToggleUp -> {
        socket.emit("TimerToggle", data as Long)
      }
      else -> {}
    }
  }
}
