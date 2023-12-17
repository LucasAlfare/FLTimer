import com.lucasalfare.fltimer.data.DATABASE_URL
import com.lucasalfare.fltimer.data.SessionsTable
import com.lucasalfare.fltimer.data.SolvesTable
import com.lucasalfare.fltimer.data.initDatabase
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test
import kotlin.test.assertTrue

class DatabaseTests {

  // in memory target URL
  private val testDatabaseUrl = "jdbc:sqlite:file:test?mode=memory&cache=shared"

  @Test
  fun `initDatabase should create tables`() {
    // Call the function you want to test
    // note: with actual file works, but with in memory, don't
    initDatabase(DATABASE_URL)

    // Verify that tables are created
    transaction {
      assertTrue(SessionsTable.exists())
      assertTrue(SolvesTable.exists())
    }
  }
}