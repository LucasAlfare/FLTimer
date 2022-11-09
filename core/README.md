# The Core module

This module holds the code of the timer. This module doesn't includes any implementations for UI, since
then are stored in their respective modules.

This module is also treated as an sub-gradle project, which means that this have its own dependencies.

To work, the timer was built in several subpackages, which each one has its own meaning inside the
application flow. These packages are:
- `Configuration`: deals with any kind of property that is used to change the application behavior;
- `Data`: handles the data created by the application and also works with the persistence of that data;
- `Networking`: aims to handle all the setup to make the application connects into its custom server;
- `Scramble`: provides thee desired scrambles sequences to be shown over the application use;
- `Statistics`: calculates all the statistics from all the current active data, respecting the _WCA_ rules;
- `Timer`: main package that makes the application perform its root propose.

# Core architectures

This application is using Observer and Event Listening patterns. The reason for this choice is that the app
has to many single pieces of code. For example, there are scrambling management, networking, timming... Note
that all these features has its own meaning at the same time they need to work together. This means that is
potentially easy, if not take care, to mix codes from then, making the use of some architectures strategies
needed.

# Observer pattern

`Observer` pattern helps us to maintain the code decoupled in most parts of it. For example, insteado of having
"direct" access to `Configuration` inside `Timer` we can just set `Configration` as a listener/observer of
`Timer` and, from `Timer`, fire events that `Configuration` is interested on.
