package com.example.fittapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fittapp.R

//NP:141350 ARENAL ARMESTO ANTONIO JOSE

class ViewFemaleScoreActivity : AppCompatActivity() {

    private lateinit var scoresRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_female_score)

        scoresRecyclerView = findViewById(R.id.scoresRecyclerView)
        scoresRecyclerView.layoutManager = LinearLayoutManager(this)

        val scoresList = loadScoresFromDatabase()
        val scoresAdapter = ScoresAdapter(scoresList)
        scoresRecyclerView.adapter = scoresAdapter
    }

    private fun loadScoresFromDatabase(): List<Score> {
        val scoresList = mutableListOf<Score>()
        val dbHelper = RegisterActivity.DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf("id", "endurance", "agility", "barHang", "pullUps")

        val cursor = db.query(
            "scores",   // La tabla para consultar
            projection, // Las columnas a retornar
            null,       // Las columnas para la cláusula WHERE
            null,       // Los valores para la cláusula WHERE
            null,       // No agrupar las filas
            null,       // No filtrar por grupos de filas
            null        // El orden de sort
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val endurance = getInt(getColumnIndexOrThrow("endurance"))
                val agility = getInt(getColumnIndexOrThrow("agility"))
                val barHang = getInt(getColumnIndexOrThrow("barHang"))
                val pullUps = getInt(getColumnIndexOrThrow("pullUps"))
                scoresList.add(Score(id, endurance, agility, barHang, pullUps))
            }
        }
        cursor.close()
        db.close()

        return scoresList
    }

}