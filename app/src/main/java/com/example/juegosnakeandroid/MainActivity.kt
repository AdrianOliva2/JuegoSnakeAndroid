package com.example.juegosnakeandroid

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.juegosnakeandroid.activities.FinalActivity
import com.example.juegosnakeandroid.activities.PortraitActivity
import com.example.juegosnakeandroid.runnable.RunnableDraw
import com.example.juegosnakeandroid.surfaceviews.GameView

class MainActivity: PortraitActivity(), View.OnClickListener {

    private lateinit var gameView: GameView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ctrLayoutMain: ConstraintLayout = findViewById(R.id.ctrLayoutMain)
        if (ctrLayoutMain.width > ctrLayoutMain.height)
            ctrLayoutMain.background = getDrawable(R.drawable.snake_menu_background_landscape)
        else if (ctrLayoutMain.height > ctrLayoutMain.width)
            ctrLayoutMain.background = getDrawable(R.drawable.snake_menu_background_portrait)
        val btnJugar: Button = findViewById(R.id.btnJugar)
        btnJugar.setOnClickListener(this)
        gameView = GameView(this, this)
    }

    fun lose(points: Int) {
        val intent = Intent(this, FinalActivity::class.java)
        intent.putExtra("points", points)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnJugar -> {
                setContentView(gameView)
            }
        }
    }

    override fun onBackPressed() {
        gameView.paused = true
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("¿Estás seguro que deseas salir?")
        alertDialog.setPositiveButton("Sí") { _: DialogInterface, _: Int ->
            run {
                this.finish()
                gameView.paused = false
            }
        }
        alertDialog.setNegativeButton("No") { _: DialogInterface, _: Int -> gameView.paused = false }
        alertDialog.create()
        alertDialog.show()
    }

}