# The FLTimer database binary format

The FLTimer app has its own binary format that is used to store data into files. Below is described
the main bytes structure to the format, where is presented in the form:

(`nBytes of each information`) `information "description"` `target information type`

# Header chunk

The FLTimer has some boolean and other meta properties used to adjust its behaviour. Below are
listed the main of those bu may sure that might have other fields and properties in the future
beyond these.

They are:

- [7 bytes] _"fltimer"_ signature (string);
- [1 byte] (UseInspection) (boolean);
- [1 byte] (ShowScramblesInDetailsUI) (boolean);
- [1 byte] (NetworkingModeOn) (boolean);
- [1 byte] (AskForTimerMode) (boolean);
- [2 bytes] number of session chunks (int).

*total = `13 bytes`*

Note that this header chunk might contain more information as the timer data grows up.

# Single `Solve` chunk

The root piece of data of this timer is the `Solve` type. The objects created from this type has
most important information, such as `time` and `scramble`. Below is described the amount of bytes
and the target type to those information, which are, then, stored into the main database file.

They are:

- [3 bytes] time (int);
- [1 byte] nBytes of the next string value (int);
- [x bytes] scramble (string);
- [1 byte] penalty (int: 0=ok,1=plusTwo,2=dnf);
- [1 byte] nBytes of the next string value (int);
- [x bytes] comment (string);
- [1 byte] nBytes of the next string value (int); (should be fixed length?)
- [x bytes] id (string)(id as an UUID).

*total = `at least 7 bytes`*

# Single `Session` chunk

Since the root piece of data is an `Solve` object, these objects are organized into "`Session`"
chunks. These chunks normally indicates different moments of the user training, indicating different
groups of data. For this reason, there are also important information about then that should be
stored into the main database file.

The relevant information are:

- [1 byte] nBytes of the next string value (int);
- [x bytes] session name (string);
- [2 bytes] number of solves (int);
- [9* bytes/chunk] `Solve` chunks (object Solve);
- [1 byte] `0xFF` byte indicating the end of the current chunk (int).

*total = `at least 4 bytes`*

# Bytes ordering

In this format specification, the bytes are either written and read from most significant bit to the
least significant one. For example, the hexadecimal number `0xaabbccdd` is written to the file in the
following sequence of single bytes:

- `[0xaa, 0xbb, 0xcc, 0xdd]`.
