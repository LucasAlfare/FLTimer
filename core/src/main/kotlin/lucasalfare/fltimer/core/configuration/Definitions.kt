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
  UseInspection
}