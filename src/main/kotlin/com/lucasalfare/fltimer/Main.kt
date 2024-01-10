package com.lucasalfare.fltimer

import com.lucasalfare.fllistening.setupManagers
import com.lucasalfare.fltimer.console.TerminalManager
import com.lucasalfare.fltimer.timer.TimerManager

// cls; ./gradlew clean; ./gradlew jar; java -jar build/libs/FLTimer-1.0.jar
suspend fun main() {
  setupManagers(
    TimerManager(),
    TerminalManager()
//    ScreenManagerTest()
  )
}