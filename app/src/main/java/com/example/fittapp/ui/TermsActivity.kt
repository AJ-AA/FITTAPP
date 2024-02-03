package com.example.fittapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fittapp.R

//NP:141350 ARENAL ARMESTO ANTONIO JOSE

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        // Encuentra la referencia al botón "Close" por su ID
        val closeButton = findViewById<View>(R.id.closeButton)

        // Agrega un evento de clic al botón
        closeButton.setOnClickListener {
            // Cierra la actividad actual y vuelve a la actividad anterior (MainActivity)
            finish()
        }
    }
}
