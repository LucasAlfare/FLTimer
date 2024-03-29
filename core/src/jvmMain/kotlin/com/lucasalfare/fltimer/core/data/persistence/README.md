# The FLTimer database binary format

The FLTimer app has its own binary format that is used to store data into files. Below is described
the main bytes structure to the format, where is presented in the form:

| ` Number of Bytes` | `Description` | `Target type` |
|--------------------|---------------|---------------|

# Header chunk

The FLTimer has some boolean and other meta properties used to adjust its behaviour. Below are
listed the main of those but may sure that might have other fields and properties in the future.

They header data is:

| Number of bytes | Description                 | Target type |
|-----------------|-----------------------------|-------------|
| 7               | _"fltimer"_ signature       | String      |
| 1               | (UseInspection)             | boolean     |
| 1               | (ShowScramblesInDetailsUI)  | boolean     |
| 1               | (NetworkingModeOn)          | boolean     |
| 1               | (AskForTimerMode)           | boolean     |
| 2               | number of session chunks    | int         |
| 1               | nBytes of the next string   | int         |
| x               | current active session name | String      |
*total = `at least 14 bytes`*

Note that this header chunk might contain more information as the timer data fields grows up.

# Single `Session` chunk

Since the root piece of data is an `Solve` object, these objects are organized into "`Session`"
chunks. These chunks normally indicates different moments of the user training, denoting different
groups of data. For this reason, there are also important information about then that should be
stored into the main database file.

The relevant information are:

| Number of bytes | Description                     | Target type            |
|-----------------|---------------------------------|------------------------|
| 1               | nBytes of the next string value | int                    |
| x               | session name                    | string                 |
| 1               | current session category code   | int (codes: TODO)      |
| 2               | number of solves chunks         | int                    |
| 6*              | `Solve` chunk(s)                | Solve object type each |
*total = `at least 4 bytes`*

_Note*: minimum number of bytes required to store 1 (one) single solve chunk._

# Single `Solve` chunk

The root piece of data of this timer is the `Solve` type. The objects created from this type has
most important information, such as `time` and `scramble`. Below is described the amount of bytes
and the target type to those information, which are, then, stored into the main database file.
They are:

| Number of bytes              | Description               | Target type                |
|------------------------------|---------------------------|----------------------------|
| 4                            | time                      | int                        |
| 1                            | nBytes of the next string | int                        |
| x                            | scramble                  | string                     |
| 1                            | penalty code              | int (0=ok,1=plusTwo,2=dnf) |
| 1                            | nBytes of the next string | int                        |
| x                            | comment                   | string                     |
| *total = `at least 7 bytes`* |                           |                            |

Note: `Solve` objects has an `id` property. These IDs are in the Java `UUID` type, however,
as can be seen in the table above, these IDs are not stored in the database file. This happens
because, at least for now, doesn't have any reason to store IDs, since they are used only to
handle the case when having two (or more) `Solve` objects with same values (same `time`,
`scramble`, etc.) so IDs can be used to diff themselves.

# Bytes ordering

In this format specification, the bytes are either written and read from most significant bit (`MSB`)
to the least significant one (`LSB`). For example, the hexadecimal number `0xaabbccdd` is written to
the file in the following sequence of single bytes:

- `[0xaa, 0xbb, 0xcc, 0xdd]`.

# Parsing files
After getting the above specification, parse (read) and/or write files should be easy. Even epending on what
is being made with these files the only requirement to take information from it is just to follow the
specification above. However, inside the application we have some built-ins readers/writers to deal with
the files.

The data architecture of the application is built in a encapsulated way. This means that the data is not
placed in static fields of even accessible through object instances. Instead, they are managed based
on events calls flow over their custom managers, which performs the work.

For this reason, is not recommended, for example, read data directly from the file. For this is recommended
read the file information, store then into the application appropriated objects types and fire some events
indicating that information was read. Note that using this suggested approach the events can carry the
read data through function arguments or something like that.

In the other hand, writing the application data to files can be easy after getting the target information
that should be written. For example, always that a `SolvesUpdate` or `SessionsUpdate` events was fired
is possible to store those information in temporary fields and compile all of then at once when the
application fires an event indicating an, e.g., request to finish.

In conclusion:
- if doesn't exists a `fltimer_data.fltd` file:
  - app creates one;
  - initialize it with default values (header and a default/standard session);
- if database file exists:
  - application attempts to validate it;
  - file is parsed with its information to appropriated objects;
  - notify other module managers with that data parsed.

# General structure view

Below is demonstrated, using the above descriptions, how should look a full file containing some
`Session` chunks with their own `Solve` chunks.

- TODO
