import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.*

class DatabaseTests {

  private val databaseUrl = "jdbc:sqlite::memory:" // Usando banco de dados SQLite em memória
  private lateinit var connection: Connection

  @BeforeTest
  fun setUp() {
    connection = DriverManager.getConnection(databaseUrl)
    sqlExecute(
      "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(255))",
      targetConnection = connection
    )
  }

  @AfterTest
  fun tearDown() {
    connection.close()
  }

  @Test
  fun testSqlExecute() {
    sqlExecute("INSERT INTO users (id, name) VALUES (1, 'John')", targetConnection = connection)
    val resultSet = sqlExecuteQuery("SELECT * FROM users WHERE id = 1", targetConnection = connection)
    assertNotNull(resultSet)
    assertTrue(resultSet.next())
    assertEquals(1, resultSet.getInt("id"))
    assertEquals("John", resultSet.getString("name"))
  }

  @Test
  fun testSqlExecuteQuery() {
    sqlExecute("INSERT INTO users (id, name) VALUES (1, 'John')", targetConnection = connection)
    val resultSet = sqlExecuteQuery("SELECT * FROM users WHERE id = 1", targetConnection = connection)
    assertNotNull(resultSet)
    assertTrue(resultSet.next())
    assertEquals(1, resultSet.getInt("id"))
    assertEquals("John", resultSet.getString("name"))
  }
}