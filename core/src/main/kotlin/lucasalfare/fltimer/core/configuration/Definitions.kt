package lucasalfare.fltimer.core.configuration

/**
 * The main enumeration holding all configurations of the
 * application.
 *
 * Usually, each configuration has a value associated, but,
 * to keep easy to handle, the values are treated as type
 * [Any], making possible multiple types transit over the
 * application flow.
 */
enum class Config {

  /**
   * Determines if the timer will use inspection state or not.
   */
  UseInspection,

  /**
   * Determines if scrambles should be shown in the details screen of the UI.
   */
  ShowScramblesInDetailsUI,

  /**
   * This configuration determines if the current session is in
   * online mode. Its value is [Boolean].
   */
  NetworkingModeOn,

  /**
   * This configuration determines if the application should
   * still asking for the desired mode on startup.
   * Its value is [Boolean].
   */
  AskForTimerMode,
}