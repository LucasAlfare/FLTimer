package com.lucasalfare.fltimer.data

import com.lucasalfare.fltimer.model.Penalty
import com.lucasalfare.fltimer.model.Preferences
import com.lucasalfare.fltimer.model.PuzzleCategory
import org.jetbrains.exposed.dao.id.LongIdTable

private const val DEFAULT_SESSION_NAME = "Default"

object PreferencesTable : LongIdTable("Preferences") {

  var useInspection = bool("use_inspection")
    .default(false)
  var showScramblesInUi = bool("show_scramble_in_ui_list")
    .default(true)
  var uiTheme = enumeration<Preferences.Companion.UiTheme>("ui_theme")
    .default(Preferences.Companion.UiTheme.Dark)
}

object SessionsTable : LongIdTable("Sessions") {

  var name = text("name")
    .uniqueIndex()
    .default(DEFAULT_SESSION_NAME)
  var puzzleCategory = enumeration<PuzzleCategory>("puzzle_category")
    .default(PuzzleCategory.RubiksCube)
}

object SolvesTable : LongIdTable("solves") {

  var time = long("time")
    .default(0L)
  var scramble = text("scramble")
    .default("")
  var penalty = enumeration<Penalty>("penalty")
    .default(Penalty.Ok)
  var comment = text("comment")
    .default("")
  var sessionName = text("session_name")
    .references(SessionsTable.name)
    .default(DEFAULT_SESSION_NAME)
}