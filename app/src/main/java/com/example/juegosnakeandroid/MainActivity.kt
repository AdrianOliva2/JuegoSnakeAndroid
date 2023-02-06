package com.example.juegosnakeandroid

import android.app.Activity
import android.os.Bundle
import com.example.juegosnakeandroid.surfaceviews.GameView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }
}