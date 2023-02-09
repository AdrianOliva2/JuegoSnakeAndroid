package com.example.juegosnakeandroid.surfaceviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.juegosnakeandroid.MainActivity
import com.example.juegosnakeandroid.R
import com.example.juegosnakeandroid.classes.Snake
import com.example.juegosnakeandroid.enums.Direction
import com.example.juegosnakeandroid.runnable.RunnableDraw
import kotlin.math.absoluteValue


class GameView(private val activity: MainActivity, context: Context): SurfaceView(context), SurfaceHolder.Callback {

    private var iniX: Float = -1F
    private var iniY: Float = -1F
    private lateinit var snake: Snake
    private lateinit var runnableDraw: RunnableDraw
    private lateinit var drawThread: Thread
    var paused: Boolean = false
        set(value) {
            if (this::snake.isInitialized) {
                field = value
                snake.paused = value
            }
        }

    @SuppressLint("UseCompatLoadingForDrawables")
    var bgImage: Drawable? = context.getDrawable(R.drawable.snake_game_background_portrait)

    init {
        holder.addCallback(this)
        setBackgroundColor(Color.BLACK)
        background = bgImage
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        val x: Float? = event?.x
        val y: Float? = event?.y
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                iniX = event.x
                iniY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (x != null && y != null) {
                    val desplX: Float = x - iniX
                    val desplY: Float = y - iniY

                    if (desplX.absoluteValue > desplY.absoluteValue)
                        if (desplX > 0)
                            snake.direction = Direction.RIGHT
                        else
                            snake.direction = Direction.LEFT
                    else
                        if (desplY > 0)
                            snake.direction = Direction.DOWN
                        else
                            snake.direction = Direction.UP
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.BLACK)
            bgImage?.draw(canvas)
        }
        if (!snake.draw(canvas)) {
            activity.lose(snake.score)
            surfaceDestroyed(holder)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        snake = Snake(this.width, this.height, Color.LTGRAY, Color.WHITE, Color.RED, Color.WHITE)
        runnableDraw = RunnableDraw(this)
        runnableDraw.run = true
        drawThread = Thread(runnableDraw)
        drawThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        runnableDraw.run = false

        while (retry) {
            try {
                drawThread.join()
                retry = false
            } catch (e: InterruptedException) {

            }
        }
    }

}