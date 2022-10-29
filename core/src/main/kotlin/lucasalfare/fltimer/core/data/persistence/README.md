# FLTimer persistence format

The FLTimer app has its own binary format that is used to store data into files. Below is described
the main bytes structure to the format, where is presented in the form:

(`nBytes of each information`) `information "description"` `target information type`

# Header chunk

- (4 bytes) _"fltimer"_ (string);
- (2 bytes) (UseInspection) (boolean);
- (2 bytes) (ShowScramblesInDetailsUI) (boolean);
- (2 bytes) (NetworkingModeOn) (boolean);
- (2 bytes) (AskForTimerMode) (boolean);
- (4 bytes) number of session chunks (int).

*total = `16 bytes`*

Note that this header chunk might contain more information as the timer data grows up.

# Single Solve chunk

- (4 bytes) time (int);
- (4 bytes) nBytes of the next string value (int);
- (x bytes) scramble (string);
- (2 bytes) penalty (char);
- (4 bytes) nBytes of the next string value (int);
- (x bytes) comment (string);
- (4 bytes) nBytes of the next string value (int); (should be fixed length?)
- (x bytes) id (string)(id as an UUID).

*total = `at least 18 bytes`*

# Single Session chunk

- (4 bytes) nBytes of the next string value (int);
- (x bytes) session name (string);
- (4 bytes) number of solves (int);
- (18* bytes) each solve (object Solve).

*total = `at least 8 bytes`*
