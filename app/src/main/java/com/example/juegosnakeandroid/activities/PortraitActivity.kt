package com.example.juegosnakeandroid.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View

open class PortraitActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    override fun onBackPressed() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("¿Estás seguro que deseas salir?")
        alertDialog.setPositiveButton("Sí") { _: DialogInterface, _: Int ->
            this.finish()
        }
        alertDialog.setNegativeButton("No") { _: DialogInterface, _: Int -> {}}
        alertDialog.create()
        alertDialog.show()
    }
}