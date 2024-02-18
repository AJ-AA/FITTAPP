package com.example.fittapp.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fittapp.R

//NP:141350 ARENAL ARMESTO ANTONIO JOSE

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var radioButtonMale: RadioButton
    private lateinit var radioButtonFemale: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        // Inicializa vistas y botones
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)
        radioButtonMale = findViewById(R.id.radioButtonMale)
        radioButtonFemale = findViewById(R.id.radioButtonFemale)

        // Botón para registrarse
        // Dentro de tu función onClick del botón registerButton
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val gender = if (radioButtonMale.isChecked) "Male" else "Female"

            if (register(username, password, gender)) {
                // Registro exitoso, muestra un mensaje de éxito en forma de Toast
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                // Redirige al MainActivity después del registro exitoso
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Cierra la actividad actual para que no se pueda volver atrás
            } else {
                // Mostrar mensaje de error en caso de registro fallido
                Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun register(username: String, password: String, gender: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        values.put("gender", gender) // Agregar género a la base de datos

        val newRowId = db.insert("users", null, values)
        db.close()

        return newRowId != -1L
    }

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            // Crear la tabla si no existe
            val createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, " +
                    "password TEXT, " +
                    "gender TEXT);"
            db.execSQL(createTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // Aquí puedes manejar las actualizaciones de la base de datos
            // Por ejemplo, si deseas agregar una nueva columna o realizar otros cambios
            if (oldVersion < 2) {
                // Código para actualizar desde la versión 1 a la versión 2
                // Puedes agregar la columna "gender" aquí en la versión 2
                val addGenderColumnQuery = "ALTER TABLE users ADD COLUMN gender TEXT;"
                db.execSQL(addGenderColumnQuery)
            }
        }

        companion object {
            private const val DATABASE_NAME = "my_database.db"
            private const val DATABASE_VERSION = 2  // Aumenta la versión de la base de datos
        }
    }

    // Función para abrir la actividad de Términos y Condiciones
    fun openTermsActivity(view: View) {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
}
