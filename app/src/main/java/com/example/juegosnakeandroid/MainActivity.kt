package com.example.juegosnakeandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.juegosnakeandroid.activities.PortraitActivity
import com.example.juegosnakeandroid.surfaceviews.GameView

class MainActivity : PortraitActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnJugar: Button = findViewById(R.id.btnJugar)
        btnJugar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnJugar -> {
                setContentView(GameView(this, this))
            }
        }
    }
}