package com.lucasalfare.fltimer.a_domain

import com.lucasalfare.fltimer.a_domain.models.Penalty

interface AppDataState {

  var timerState: TimerState
  var timerCurrentDisplayTime: Long
  var startInspectionMoment: Long
  var stopInspectionMoment: Long
  var startMoment: Long
  var stopMoment: Long
  var scramble: String
  var penalty: Penalty
  var comment: String

  // todo: include [inspectionEnabled: Boolean]
}

//class Kkk(
//  override var startInspectionMoment: Long = 0L,
//  override var stopInspectionMoment: Long = 0L,
//  override var startMoment: Long = 0L,
//  override var stopMoment: Long = 0L,
//  override var scramble: String = "",
//  override var penalty: Penalty = Penalty.Ok,
//  override var comment: String = ""
//) : AppDataState

//object Kkk : AppDataState {
//  private var _startInspectionMoment: Long = 0L
//  private var _stopInspectionMoment: Long = 0L
//  private var _startMoment: Long = 0L
//  private var _stopMoment: Long = 0L
//  private var _scramble: String = ""
//  private var _penalty: Penalty = Penalty.Ok
//  private var _comment: String = ""
//
//  override var startInspectionMoment: Long
//    get() = _startInspectionMoment
//    set(value) {
//      _startInspectionMoment = value
//    }
//
//  override var stopInspectionMoment: Long
//    get() = _stopInspectionMoment
//    set(value) {
//      _stopInspectionMoment = value
//    }
//
//  override var startMoment: Long
//    get() = _startMoment
//    set(value) {
//      _startMoment = value
//    }
//
//  override var stopMoment: Long
//    get() = _stopMoment
//    set(value) {
//      _stopMoment = value
//    }
//
//  override var scramble: String
//    get() = _scramble
//    set(value) {
//      _scramble = value
//    }
//
//  override var penalty: Penalty
//    get() = _penalty
//    set(value) {
//      _penalty = value
//    }
//
//  override var comment: String
//    get() = _comment
//    set(value) {
//      _comment = value
//    }
//}