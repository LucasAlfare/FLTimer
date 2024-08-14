package com.lucasalfare.fltimer.a_domain

interface Timer {

  var appDataState: AppDataState

  suspend fun startInspection()
  suspend fun stopInspection()
  suspend fun startTimer()
  suspend fun stopTimer()
}