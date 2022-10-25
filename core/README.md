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