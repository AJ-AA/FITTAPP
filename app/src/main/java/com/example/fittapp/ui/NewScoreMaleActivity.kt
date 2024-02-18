package com.example.fittapp.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fittapp.R

class NewScoreMaleActivity : AppCompatActivity() {

    private lateinit var enduranceSeekBar: SeekBar
    private lateinit var agilitySeekBar: SeekBar
    private lateinit var pullUpsSeekBar: SeekBar
    private lateinit var saveScoreButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_score_male)

        dbHelper = DatabaseHelper(this)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        enduranceSeekBar = findViewById(R.id.enduranceseekBar)
        agilitySeekBar = findViewById(R.id.AgilityseekBar2)
        pullUpsSeekBar = findViewById(R.id.pullUpsSeekBar)
        saveScoreButton = findViewById(R.id.save_score)
    }

    private fun setupListeners() {
        saveScoreButton.setOnClickListener {
            val endurance = enduranceSeekBar.progress
            val agility = agilitySeekBar.progress
            val pullUps = pullUpsSeekBar.progress
            saveScores(endurance, agility, pullUps)
        }
    }

    private fun saveScores(endurance: Int, agility: Int, pullUps: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues()

        values.put("endurance", endurance)
        values.put("agility", agility)
        values.put("pullUps", pullUps)

        val newRowId = db.insert("scores", null, values)
        db.close()

        if (newRowId != -1L) {
            Toast.makeText(this, "Puntuación guardada correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al guardar la puntuación", Toast.LENGTH_SHORT).show()
        }
    }

    // Asegúrate de tener una implementación adecuada de DatabaseHelper aquí
    // Esta implementación es solo un placeholder
    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            // Aquí deberías crear tu tabla de scores si aún no existe
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // Manejar actualizaciones de la base de datos
        }

        companion object {
            private const val DATABASE_NAME = "my_database.db"
            private const val DATABASE_VERSION = 1
        }
    }
}
