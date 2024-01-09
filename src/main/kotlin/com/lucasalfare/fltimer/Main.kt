package com.lucasalfare.fltimer

import com.lucasalfare.fllistening.setupManagers

suspend fun main() {
  setupManagers(
    TimerManager(),
    ScreenManagerTest()
  )
}