package com.lucasalfare.fltimer.core.timer.fsm

import com.lucasalfare.fllistening.EventManageable
import com.lucasalfare.fltimer.core.FLTimerEvent

/**
 * Shortcuts for the main events needed inside the state
 * machine.
 */
val InputPress = FLTimerEvent.TimerToggleDown
val InputRelease = FLTimerEvent.TimerToggleUp

/**
 * Core interface to all classes that can ben treated as an "application state".
 *
 * The timer module is composed by a finite state machine, where all states are a
 * single piece of code.
 *
 * All that pieces shares these definitions in order to keep easy to handle different
 * behaviours of different implementations in a single place, as is done by a manager
 * class, for example.
 */
interface TimerState {

  /**
   * Receives the incoming application event and decides what to do with it.
   * Also receives custom properties, via [data] field.
   *
   * Must always return the "next state", or null if the incoming event doesn't
   * matter for this state piece.
   */
  fun handleInput(inputType: FLTimerEvent, data: Any? = null): TimerState?

  /**
   * Updates anything related to the state. Can be called outside the state.
   */
  fun update(eventManageable: EventManageable, data: Any? = null)

  /**
   * Cancels any kind of action that can be running in this state, such as
   * background tasks and/or coroutines.
   *
   * Can be called outside the state.
   */
  fun suspendState()
}
