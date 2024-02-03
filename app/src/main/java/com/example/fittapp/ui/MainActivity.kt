package com.example.fittapp.ui
import DatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fittapp.R
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

//NP:141350 ARENAL ARMESTO ANTONIO JOSE

class MainActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var spinnerLanguage: Spinner
    private var selectedLanguage: String = "en" // Valor predeterminado en inglés
    private var currentLanguage: String = "en" // Idioma actual de la aplicación



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this)

        // Inicializa las variables de SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Configura el Spinner y su adaptador

        setContentView(R.layout.activity_main)
        spinnerLanguage = findViewById(R.id.spinner_language)

        val languageOptions = arrayOf("English", "Español")  // Lista de opciones de idioma
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languageOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter

        currentLanguage = sharedPreferences.getString("SELECTED_LANGUAGE", "en") ?: "en"
        applyLanguage(currentLanguage, false)

        // Configura el Spinner para reflejar el idioma actual
        val languagePosition = when (currentLanguage) {
            "en" -> 0 // English
            "es" -> 1 // Español
            else -> 0
        }
        spinnerLanguage.setSelection(languagePosition)

        // Inicializa vistas y botones
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox)

        // Verifica si las credenciales se guardaron previamente
        val savedUsername = sharedPreferences.getString("USERNAME", "")
        val savedPassword = sharedPreferences.getString("PASSWORD", "")

        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            // Las credenciales se guardaron previamente, completa los campos de entrada
            usernameEditText.setText(savedUsername)
            passwordEditText.setText(savedPassword)
            rememberMeCheckBox.isChecked = true
        }

        // Botón para iniciar sesión
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (login(username, password)) {
                // Inicio de sesión exitoso, redirige a la actividad MainPage
                val intent = Intent(this, MainPage::class.java)
                intent.putExtra("USERNAME", username)

                if (rememberMeCheckBox.isChecked) {
                    // Guardar las credenciales en las preferencias compartidas
                    editor.putString("USERNAME", username)
                    editor.putString("PASSWORD", password)
                    editor.apply()
                }

                startActivity(intent)
            } else {
                // Mostrar mensaje de error en caso de inicio de sesión fallido
                Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para registrarse
        registerButton.setOnClickListener {
            // Redirige a la actividad RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Inicializa la base de datos
        dbHelper = DatabaseHelper(this)

        // Configura el Listener del Spinner para cambiar el idioma y aplicarlo
        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val language = when (position) {
                    0 -> "en" // Inglés
                    1 -> "es" // Español
                    else -> "en"
                }

                // Guardar el idioma seleccionado en las preferencias compartidas
                if (language != sharedPreferences.getString("SELECTED_LANGUAGE", "en")) {
                    editor.putString("SELECTED_LANGUAGE", language)
                    editor.apply()

                    applyLanguage(language, true)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun applyLanguage(languageCode: String, shouldRecreate: Boolean) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        if (shouldRecreate) {
            recreate() // Reinicia la actividad solo si es necesario aplicar el cambio de idioma
        }
    }

    private fun setAppLocale(localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate() // Reinicia la actividad para aplicar el nuevo idioma
    }

    // Función para borrar todos los datos de la base de datos
    private fun clearAllData() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM users") // Elimina toda la tabla "users"
        db.close()
    }

    // Función para realizar el inicio de sesión
    private fun login(username: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val columns = arrayOf("username")
        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor: Cursor = db.query("users", columns, selection, selectionArgs, null, null, null)

        val result = cursor.count > 0
        cursor.close()
        db.close()

        return result
    }

    // Función para abrir la actividad de Términos y Condiciones
    fun openTermsActivity(view: View) {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }

}

