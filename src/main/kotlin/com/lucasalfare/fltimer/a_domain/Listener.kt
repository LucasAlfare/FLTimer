package com.lucasalfare.fltimer.a_domain

interface Listener {

  suspend fun onEvent(event: String, data: Any? = null)
}