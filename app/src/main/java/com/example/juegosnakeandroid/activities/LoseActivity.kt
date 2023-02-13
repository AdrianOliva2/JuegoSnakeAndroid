package com.example.juegosnakeandroid.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.juegosnakeandroid.R
import com.example.juegosnakeandroid.classes.Player
import com.example.juegosnakeandroid.db.DBHelper

class LoseActivity : PortraitActivity(), View.OnClickListener {

    private val dbHelper = DBHelper(this)
    private lateinit var txtNombre: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lose)
        txtNombre = findViewById(R.id.inputTextNombre)
        val btnInsertarPuntuacion: Button = findViewById(R.id.btnInsertarPuntuacion)
        btnInsertarPuntuacion.setOnClickListener(this)
        val txtScore: TextView = findViewById(R.id.txtScore)
        txtScore.text = "Score: ${intent.getIntExtra("score", 0)}"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnInsertarPuntuacion -> {
                dbHelper.insertScore(Player(txtNombre.text.toString(), intent.getIntExtra("score", 0)))
                startActivity(Intent(this, HighScoreActivity::class.java))
            }
        }
    }
}