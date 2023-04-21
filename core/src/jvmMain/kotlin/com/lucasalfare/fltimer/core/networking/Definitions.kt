package com.lucasalfare.fltimer.core.networking

import com.lucasalfare.fltimer.core.data.Solve
import com.lucasalfare.fltimer.core.data.Solves
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI

val LocalhostURI: URI = URI.create("http://localhost:3000")

data class User(val id: String, var solves: Solves)

fun parseServerUsers(input: JSONArray): MutableList<User> {
  val users = mutableListOf<User>()
  var i = 0
  while (i < input.length()) {
    val curr = input[i] as JSONObject
    val id = curr.get("id").toString()
    val solves = curr.getJSONArray("solves")
    val parsedSolves = Solves()
    var j = 0
    while (j < solves.length()) {
      parsedSolves += Solve(time = (solves[j] as Int).toLong())
      j++
    }
    users += User(id = id, solves = parsedSolves)
    i++
  }
  return users
}
