import com.lucasalfare.fltimer.data.SessionsTable
import com.lucasalfare.fltimer.data.SolvesTable
import com.lucasalfare.fltimer.data.initDatabase
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import kotlin.test.assertTrue

class DatabaseTests {

  // in memory target URL
  private val testDatabaseUrl = "jdbc:sqlite:file:mode=memory"

  @Test
  fun `initDatabase should create tables`() {
    // Call the function you want to test
    initDatabase(testDatabaseUrl)

    // Verify that tables are created
    transaction {
      assertTrue(SessionsTable.exists())
      assertTrue(SolvesTable.exists())
    }
  }
}