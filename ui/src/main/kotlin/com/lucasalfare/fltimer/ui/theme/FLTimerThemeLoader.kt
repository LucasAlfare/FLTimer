package com.lucasalfare.fltimer.ui.theme

import androidx.compose.ui.text.font.FontFamily

// TODO:
//  this file should be used to offer abstract functions to
//  be used to load theme on each platform (desktop, mobile),
//  mostly due to font loading being different on each one.

class MyFontFamilies {
  companion object {
    lateinit var regular: FontFamily

    lateinit var bold: FontFamily

    lateinit var italic: FontFamily

    fun defineRegular(initializationCallback: () -> FontFamily) {
      if (!(this::regular.isInitialized)) {
        regular = initializationCallback()
      }
    }

    fun defineBold(initializationCallback: () -> FontFamily): FontFamily {
      if (!(this::bold.isInitialized)) {
        bold = initializationCallback()
      }

      return bold
    }

    fun defineItalic(initializationCallback: () -> FontFamily): FontFamily {
      if (!(this::italic.isInitialized)) {
        italic = initializationCallback()
      }

      return italic
    }
  }
}