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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fittapp.R

class MainPage : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 101
    private val CALL_PERMISSION_CODE = 102
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var photoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        val username = intent.getStringExtra("USERNAME") ?: ""
        val emailTextView = findViewById<TextView>(R.id.email)
        emailTextView.text = username

        photoImageView = findViewById(R.id.photoImageView)
        val openWebButton = findViewById<Button>(R.id.buttonOpenWeb)
        openWebButton.setOnClickListener {
            openWebPage()
        }

        val viewScoresButton = findViewById<Button>(R.id.ViewScores)
        viewScoresButton.setOnClickListener {
            navigateToScoreActivity()
        }
        val newScoreButton = findViewById<Button>(R.id.RegisterNewScore)
        newScoreButton.setOnClickListener {
            navigateToNewScoreActivity()
        }
        //val mapButton: Button = findViewById(R.id.mapButton)
        //mapButton.setOnClickListener {
            // Inicia la MapsActivity
          //  val intent = Intent(this, MapsActivity::class.java)
         //   startActivity(intent)
        //}

    }

    private fun navigateToNewScoreActivity() {
        // Asegúrate de que esta lógica de navegación es lo que esperas
        val gender = intent.getStringExtra("GENDER") ?: ""
        val intent = when (gender) {
            "Female" -> Intent(this, NewScoreFemaleActivity::class.java)
            "Male" -> Intent(this, NewScoreMaleActivity::class.java)
            else -> null
        }
        intent?.let { startActivity(it) }
    }

    private fun navigateToScoreActivity() {
        val gender = intent.getStringExtra("GENDER") ?: ""
        val intent = when (gender) {
            "Female" -> Intent(this, ViewFemaleScoreActivity::class.java)
            "Male" -> Intent(this, ViewMaleScoreActivity::class.java)
            else -> null
        }
        intent?.let { startActivity(it) }
    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Permission denied to use the camera", Toast.LENGTH_SHORT).show()
                }
            }
            CALL_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall("1234567890")
                } else {
                    Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap?
            photoImageView.setImageBitmap(imageBitmap)
        }
    }

    fun shareText(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    fun openWebPage() {
        val url = "https://www.Google.com"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_CODE)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Inicia tu Activity de configuraciones aquí
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
