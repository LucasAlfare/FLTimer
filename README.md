# My Puzzle Timer

This is my final repository to hold my _speedcubing_ timer project. For some years I have been practicing programming in
some contexts and now I decided to use all of this knowledge to build this application.

Doing this I hope to mix concepts of the most various programming topics, going from architecture decisions to graphic
rendering or even design stuff and/or networking.

Also, my aim in creating this is not to release something to compete with other applications of the same type but, instead,
to put my knowledge in check and see if I can really write the application from beginning to end.

# Application overview

I'm designing this app to be as customizable as possible.

Also, after finished, some of the separate packages should work as libraries, offering all their functions to be used anywhere.

# Project structure

The project is being built over sub gradle projects. They are:

- Core: holds "common" code used by any other module;
- UI: holds code related to the application UI. The UI here aims to be consistent between all platforms and, for this, makes use of the power of the Jetpack Compose;
- Desktop: module to hold code only useful to desktop version build;
- Mobile: module to hold code only useful to mobile (Android) version build;
- (TODO: Web);
- (TODO: iOS).

All of these sub-projects are treated as "modules", and may have dependency between themselves. 

Also, all these modules aims to be an individual gradle project, having its own build and dependencies, making possible manage individual and platform-specific code in a clean way.

# Features

I'm working on a timer which offers the main features that I need for myself. However, it is known that this might be useful for
different users, since they are very useful for their prupose. These features are:

- Timing solutions with good precision (is the nanosecond precision implementation worth it?);
- Great variety of scrambling tools;
- Good statistics measurement;
- A nice data management included.

There are other features in mind being planned to be implemented after the basic application is finished, such as:

- Specific training tools (blindfolded, speffz helpers, a nice keyboard for
sequence typing, and so on);
- Networking: 1 or more cubers will be able to connect each other in the timer and share their solutions and statistics in real time;
- Virtual 3D puzzles to play/time.

All of these features I plan to implement by myself as best as I can, using only my own knowledge. Reinventing the
wheel today is not a good thing to do, but when it comes to studying it is one of the most powerful weapons. :)
