package com.example.fittapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fittapp.R
import java.util.Locale

//NP:141350 ARENAL ARMESTO ANTONIO JOSE

class MainPage : Activity() {

    private val CAMERA_PERMISSION_CODE = 101

    private val PICK_IMAGE_REQUEST = 1

    private val CAPTURE_IMAGE_REQUEST = 2 // Nuevo código para capturar imágenes
    private lateinit var photoImageView: ImageView // Agrega la referencia al ImageView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        // Recupera el nombre de usuario del Intent
        val username = intent.getStringExtra("USERNAME") ?: ""

        // Obtén una referencia al TextView en tu layout
        val emailTextView = findViewById<TextView>(R.id.email)

        // Determina el idioma actual y establece el saludo correspondiente



        // Establece el saludo con el nombre de usuario en el TextView
        emailTextView.text = "$username"

        photoImageView = findViewById(R.id.photoImageView)
    }






    fun selectImage(view: View) {
        // Verifica si se tiene el permiso de la cámara antes de abrir la cámara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            // Si no se tiene el permiso, solicítalo al usuario
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    fun captureImage(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap?

            if (imageBitmap != null) {
                // Establece la imagen capturada en el ImageView
                photoImageView.setImageBitmap(imageBitmap)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    fun openTermsActivity(view: View) {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)

    }


}