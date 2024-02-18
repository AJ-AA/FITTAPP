import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Creación de la tabla de puntuaciones.
        val createScoresTable = """
        CREATE TABLE IF NOT EXISTS scores (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER,
            endurance INTEGER,
            agility INTEGER,
            pullUps INTEGER,
            hangBar INTEGER,
            FOREIGN KEY(user_id) REFERENCES users(id)
        );
    """.trimIndent()

        // Creación de la tabla de usuarios.
        val createUsersTable = """
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL,
            password TEXT NOT NULL,
            gender TEXT
        );
    """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createScoresTable)

        Log.d("DatabaseHelper", "Tablas creadas correctamente en onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private const val DATABASE_NAME = "my_database.db"
        private const val DATABASE_VERSION = 2
    }
}
