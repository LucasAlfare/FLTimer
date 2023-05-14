package com.lucasalfare.fltimer.ui.theme

import androidx.compose.ui.text.font.FontFamily

/**
 * This class offers the mechanics to initialize its companion fields based on
 * the argument callback functions calls.
 *
 * These callbacks can be defined in any platform, making possible to abstract
 * custom code based on those platforms.
 */
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

    fun defineBold(initializationCallback: () -> FontFamily) {
      if (!(this::bold.isInitialized)) {
        bold = initializationCallback()
      }
    }

    fun defineItalic(initializationCallback: () -> FontFamily) {
      if (!(this::italic.isInitialized)) {
        italic = initializationCallback()
      }
    }
  }
}