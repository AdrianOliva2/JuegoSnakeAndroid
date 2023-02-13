package com.example.juegosnakeandroid.classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.media.MediaPlayer
import android.util.Log
import androidx.appcompat.widget.ActivityChooserView.InnerLayout
import com.example.juegosnakeandroid.R
import com.example.juegosnakeandroid.enums.Direction
import kotlin.concurrent.thread
import kotlin.random.Random

class Snake(private val context: Context, private var width: Int, private var height: Int, private val tailColor: Int, private val headColor: Int, private val appleColor: Int, private val textColor: Int) {

    private var losed = false
    var score: Int = 0
    var paused: Boolean = false
    private var tail: MutableList<Point> = arrayListOf(Point(width / 2, height / 2))
    private val head: Point = tail[0]
    private val eatSound: MediaPlayer = MediaPlayer.create(context, R.raw.snake_eat_sound)
    private val gameOverSound: MediaPlayer = MediaPlayer.create(context, R.raw.game_over_sound)

    companion object {
        private const val SPEED = 6
        private const val BLOCK_SIZE = 60
        private const val APPLE_RADIUS = 30F
        private const val TEXT_SIZE = 100
    }

    private var apple: Point? = null
    var direction = Direction.STOP

    //Method for snake eat apple
    private fun eatApple() {
        if (apple != null) {
            Log.i("CoordsSnakeApple", "Cabeza: (x: ${head.x}, y: ${head.y}), Manzana: (x: ${apple!!.x}, y: ${apple!!.y})")
            if (((head.x + BLOCK_SIZE > apple!!.x - APPLE_RADIUS.toInt()) && (head.y + BLOCK_SIZE > apple!!.y - APPLE_RADIUS.toInt())) && ((head.x - BLOCK_SIZE < apple!!.x + APPLE_RADIUS.toInt()) && (head.y - BLOCK_SIZE < apple!!.y + APPLE_RADIUS.toInt()))) {
                apple = null
                eatSound.start()
                eatSound.isLooping = false
                score += 10
                val lastTailBlock = tail[tail.size - 1]
                when (direction) {
                    Direction.UP -> {
                        tail += Point(lastTailBlock.x, (lastTailBlock.y + BLOCK_SIZE))
                    }
                    Direction.DOWN -> {
                        tail += Point(lastTailBlock.x, (lastTailBlock.y - BLOCK_SIZE))
                    }
                    Direction.LEFT -> {
                        tail += Point((lastTailBlock.x + BLOCK_SIZE), lastTailBlock.y)
                    }
                    Direction.RIGHT -> {
                        tail += Point((lastTailBlock.x - BLOCK_SIZE), lastTailBlock.y)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun move() {
        if (!paused) {
            for (i in tail.lastIndex downTo 0) {
                if (i > 0) {
                    val newPoint: Point = tail[i - 1]
                    tail[i].x = newPoint.x
                    tail[i].y = newPoint.y
                } else {
                    val newPoint: Point = tail[i]
                    when (direction) {
                        Direction.LEFT -> {
                            newPoint.x -= SPEED
                        }
                        Direction.UP -> {
                            newPoint.y -= SPEED
                        }
                        Direction.RIGHT -> {
                            newPoint.x += SPEED
                        }
                        Direction.DOWN -> {
                            newPoint.y += SPEED
                        }
                        else -> {}
                    }
                    tail[i] = newPoint
                }
            }
            if (apple != null) {
                eatApple()
            }
        }
    }

    fun draw(canvas: Canvas?): Boolean {
        if (apple == null) {
            apple = Point(
                (Random.nextInt(APPLE_RADIUS.toInt(), (width - APPLE_RADIUS.toInt()))),
                (Random.nextInt(APPLE_RADIUS.toInt(), (height - APPLE_RADIUS.toInt())))
            )
        }

        if ((head.x < 0 || head.x > this.width) || (head.y < 0 || head.y > this.height)) {
            losed = true
            gameOverSound.start()
            gameOverSound.isLooping = false
            return false
        } else {
            if (!losed) {
                var paint = Paint()
                paint.isAntiAlias = true
                paint.style = Paint.Style.FILL
                paint.color = appleColor
                canvas?.drawCircle(apple!!.x.toFloat(), apple!!.y.toFloat(), APPLE_RADIUS, paint)

                val iniX: Int = (width / 2) - TEXT_SIZE
                val iniY: Int = TEXT_SIZE + 20
                val endX: Int = iniX + TEXT_SIZE
                val endY: Int = iniY + TEXT_SIZE

                paint = Paint()
                paint.isAntiAlias = true
                paint.style = Paint.Style.FILL
                paint.color = textColor
                paint.textSize = TEXT_SIZE.toFloat()
                canvas?.drawText("Score: $score", iniX.toFloat(), iniY.toFloat(), paint)

                paint = Paint()
                paint.isAntiAlias = true
                paint.style = Paint.Style.FILL
                paint.color = headColor
                tail.forEachIndexed { index, point ->
                    run {
                        if (index > 0 && paint.color == headColor) {
                                paint.color = tailColor
                        }
                        canvas?.drawRect(
                            point.x.toFloat(),
                            point.y.toFloat(),
                            (point.x + BLOCK_SIZE).toFloat(),
                            (point.y + BLOCK_SIZE).toFloat(),
                            paint
                        )
                    }
                }
            }
            if (direction != Direction.STOP)
                move()
                return true
        }
        return false
    }

}