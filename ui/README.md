# Application UI patterns

In this module are stored the base piece of UI used and globally used by any platform build. This
components should work by themselves standalone, discarding the needing of being used in group.
E.g.: The `Display()` composable can be called alone and it will work well, since `uiManager` and
a `TimerManager` instance is set or if there's another way to fire the related events.

The UI is being built in order to use a entity called _uiManager_. This field provides access to
application events propagation and event listening.

Also, in most components, they are built using the Jetpack Compose _side effects_ approach.
Normally, all composables defines a `DisposableEffect` over its first lines to attach _function
callbacks_ onto the global `uiManager` field.

The `DiposableEffect` side effect was choose once it provides a way to "remove" the previous
attached function callback, which is a nice way to manage those callbacks if the UI needs repaint or
something like that.