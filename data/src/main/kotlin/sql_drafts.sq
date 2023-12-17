-- SESSIONS TABLE

    -- creation of the table
    CREATE TABLE IF NOT EXISTS Sessions (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      CONSTRAINT unique_id UNIQUE (id)
    );

    -- inserting new session
    INSERT INTO Sessions (name) VALUES ('$name');

    -- getting specific session, based on its id
    SELECT * FROM Sessions WHERE id = $sessionId"

    -- getting all names from Sessions table
    SELECT name FROM Sessions

    -- getting all solves that are related to the specified session name
    SELECT Solves.*
    FROM Solves
    JOIN Sessions ON Solves.session_id = Sessions.id
    WHERE Sessions.name = '$sessionName';

    -- update session name
    UPDATE Sessions SET name = '$updatedName' WHERE id = $sessionId;

    -- clear 1 single session (first clear related solves, then the session)
    DELETE FROM Solves WHERE session_id = $sessionId;
    DELETE FROM Sessions WHERE id = $sessionId;

-- SOLVES TABLE

    -- creation of the table
    CREATE TABLE IF NOT EXISTS Solves (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      time INTEGER,
      scramble TEXT,
      penalty TEXT CHECK penalty IN ('ok', 'plus_two', 'dnf'),
      comment TEXT,
      session_id INT,
      FOREIGN KEY (session_id) REFERENCES Sessions(id),
      CONSTRAINT unique_id UNIQUE (id)
    );

    -- inserting a single solve in the target sessionId Session
    INSERT INTO Solves (time, scramble, penalty, comment, session_id) VALUES (
      $time,
      $scramble,
      ${penalty.name},
      $comment,
      $sessionId
    );

    -- getting all data of solves that are related to the sessionId
    SELECT * FROM Solves WHERE id = $solveId

    -- update 1 single solve with new values
    UPDATE Solves
    SET
      time = ${updatedSolve.time},
      scramble = '${updatedSolve.scramble}',
      penalty = '${updatedSolve.penalty.name}',
      comment = '${updatedSolve.comment}'
    WHERE id = $solveId;

    -- delete 1 single solve
    DELETE FROM Solves WHERE id = $solveId;