package com.example.juegosnakeandroid.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegosnakeandroid.MainActivity
import com.example.juegosnakeandroid.R
import com.example.juegosnakeandroid.classes.CustomAdapter
import com.example.juegosnakeandroid.classes.Player
import com.example.juegosnakeandroid.db.DBHelper

class HighScoreActivity : PortraitActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)
        val btnVolver: Button = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener(this)
        val dbHelper = DBHelper(this)
        val playerList: MutableList<Player>? = dbHelper.getScores()
        if (!playerList.isNullOrEmpty()) {
            val highScoreList: RecyclerView = findViewById(R.id.highScoreList)
            val layoutManager = LinearLayoutManager(this)
            highScoreList.layoutManager = layoutManager
            val adapter = CustomAdapter(this, playerList)
            highScoreList.adapter = adapter
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}