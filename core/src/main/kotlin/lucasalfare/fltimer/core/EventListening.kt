package lucasalfare.fltimer.core

/**
 * Enumeration to hold all the events that can transit over the application flow.
 *
 * These events are not a data value, just works to "flag" someone interested that
 * it occurred. Or in other words, they can work as "boolean" directives, saying
 * if the event occurred or no.
 *
 * In some contexts, the event can be sent to listeners with a data object together
 * then, once sometimes is useful to handle the different calls of the same event,
 * for example.
 *
 * Also, the most events are documented with the format of data that it can hold
 * when fired from somewhere, useful to keep track of what is needed to implement
 * something.
 *
 * Normally, these data objects are written in a nullable [Any] variable, which
 * should be cast to the appropriated types according to event definition from here.
 */
enum class AppEvent {

  /**
   * Comes with a [Long] number representing the "exact" time the event
   * occurred.
   */
  TimerToggleDown,

  /**
   * Comes with a [Long] number representing the "exact" time the event
   * occuried.
   */
  TimerToggleUp,

  /**
   * Indicates that timer is ready to begin a new round.
   */
  TimerReady,

  /**
   * Indicates that timer is currently active.
   */
  TimerStarted,

  /**
   * Comes with [Long] number representing time.
   */
  TimerUpdate,

  /**
   * Comes with a [Long] number representing the current real elapsed time.
   */
  TimerFinished,

  /**
   * When fired, this event indicates the timer should be cancel any
   * running state and restores to the idle [ReadyState].
   */
  TimerCancel,

  /**
   * Comes with an [Array] of elements, being the index:
   * - 0: current countdown time;
   * - 1: current penalty.
   */
  TimerInspectionUpdate,

  /**
   * Comes with a [Solves] class object reference, which holds all the solves data.
   */
  SolvesUpdate,

  /**
   * TODO
   */
  SolvesItemUpdate,

  /**
   * Comes with a UUID id representing the item to be removed from main data.
   */
  SolvesItemRemove,

  /**
   * When fired, this event indicates request to clear all solves data from the current
   * session.
   */
  SolvesClear,

  /**
   * Comes with a [String] representing the name of the session to
   * switch to.
   */
  SessionSwitch,

  /**
   * Comes with an array of elements, being the index:
   * - 0: a [String] representing the name of current active session;
   * - 1: a [MutableMap]<String, Session> reference to all sessions.
   */
  SessionsUpdate,

  /**
   * Comes with an array of elements, being the index:
   * - 0: a [String] holding the last scramble generated by the application;
   * - 1: a [String] holding the newest and current scramble generated by the application.
   */
  ScrambleGenerated,

  /**
   * Comes with a array of elements, being the index:
   *
   * - 0: a [Config] enum item representing the configuration to be set;
   * - 1: the new value of the configuration, in the target type.
   */
  ConfigSet,

  /**
   * Comes with a reference of a [MutableMap<Config, Any>] object,
   * containing all configurations related to the application.
   *
   * This is fired always a configuration change, making possible listeners
   * handle its custom changes related to a single or multiple values.
   */
  ConfigsUpdate,

  EmptyEvent,

  /**
   * TODO
   */
  SolvesUpdateRequest,

  /**
   * Comes with a [String] value containing the current active scramble generated
   * by the application.
   */
  RequestScrambleGenerated,

  /**
   * TODO
   */
  SessionsRequestUpdate,

  /**
   * This event indicates that networking module also received a primary
   * "NetworkingUsersUpdate" event from the server. This comes with a list of
   * users matching the type [User].
   */
  NetworkingUsersUpdate,

  /**
   * This event indicates that networking module also received a primary
   * "NetworkingAllUsersFinished" event from the server. This should indicates that
   * all users inside the server finished their rounds and THIS client is able to
   * play again.
   */
  NetworkingAllUsersFinished
}

/**
 * This class is used to make children to be able to receive events from other [EventManageable]
 * instances. Also, this class is used to make any children be able to propagate events to other
 * interested [EventManageable] instances.
 */
abstract class EventManageable {

  /**
   * Holds all current objects that are listening to this instance.
   */
  var listeners = mutableListOf<EventManageable>()

  /**
   * Function that offers a custom initialization block.
   *
   * Normally this is the first function to be called, after
   * constructor and/or native init blocks.
   */
  abstract fun init()

  /**
   * Used to handle any kind of incoming event/data from outside
   * the instance.
   */
  abstract fun onEvent(event: AppEvent, data: Any?, origin: Any?)

  /**
   * Takes any object that can listen/handle events and adds it in this instance.
   * It will only add if is not added yet.
   */
  fun addListener(l: EventManageable) {
    if (!listeners.contains(l)) listeners.add(l)
  }

  /**
   * Function that pass its params to all previously added listeners of this instance.
   */
  fun notifyListeners(event: AppEvent, data: Any? = null, origin: Any? = null) {
    listeners.forEach {
      it.onEvent(event, data, origin)
    }
  }
}

/**
 * Takes all the managers from varargs and add each one to themselves.
 *
 * This is useful to make all managers communicate between themselves.
 *
 * Also, automatically calls initializations of all listeners, but only
 * when adding is finished.
 */
fun setupManagers(vararg managers: EventManageable) {
  managers.forEach { m1 ->
    managers.forEach { m2 ->
      if (m2 != m1) {
        m2.addListener(m1)
      }
    }
  }

  managers.forEach { it.init() }
}
