package com.lucasalfare.fltimer

enum class TimerEvent {
  TimerToggleInspection,
  TimerInspectionUpdate,

  TimerToggleDown,
  TimerToggleUp,

  TimerFinish,
  TimerSolveUpdate,
  TimerReady,

  ScrambleGenerated,
  RequestScrambleGenerated
}