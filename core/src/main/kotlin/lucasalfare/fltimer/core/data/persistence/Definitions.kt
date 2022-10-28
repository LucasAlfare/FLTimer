package lucasalfare.fltimer.core.data.persistence

/**
- header chunk:
(4 bytes) "fltimer" (string)
(2 bytes) (UseInspection) (boolean)
(2 bytes) (ShowScramblesInDetailsUI) (boolean)
(2 bytes) (NetworkingModeOn) (boolean)
(2 bytes) (AskForTimerMode) (boolean)
(4 bytes) number of session chunks (int)
total = 16 bytes

- solve chuck:
(4 bytes) time (int)
(4 bytes) scramble (string)
(3 bytes) penalty (char)
(4 bytes) comment (string)
(4 bytes) id (string)(id as an UUID)
total = 19 bytes

- session chunk:
(4 bytes) session name (string)
(4 bytes) number of solves (int)
(19 bytes) each solve (object Solve)
total = at least 8 bytes
 */
