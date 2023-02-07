package com.example.juegosnakeandroid.classes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import androidx.appcompat.widget.ActivityChooserView.InnerLayout
import com.example.juegosnakeandroid.enums.Direction
import kotlin.random.Random

class Snake(private var width: Int, private var height: Int, private val tailColor: Int, private val headColor: Int, private val appleColor: Int, private val textColor: Int) {

    private var losed = false
    var score: Int = 0
    var paused: Boolean = false
    private var tail: MutableList<Point> = arrayListOf(Point(width / 2, height / 2))
    private val head: Point = tail[0]

    companion object {
        private const val SPEED = 6
        private const val BLOCK_SIZE = 60
        private const val APPLE_RADIUS = 30F
        private const val TEXT_SIZE = 100
    }

    private var apple: Point? = null
    var direction = Direction.STOP

    private fun move() {
        if (!paused) {
            when (direction) {
                Direction.LEFT -> {
                    tail.forEachIndexed { index, point ->
                        run {
                            val newPoint: Point = point
                            newPoint.x -= SPEED
                            tail[index] = newPoint
                        }
                    }
                }
                Direction.UP -> {
                    tail.forEachIndexed { index, point ->
                        run {
                            val newPoint: Point = point
                            newPoint.y -= SPEED
                            tail[index] = newPoint
                        }
                    }
                }
                Direction.RIGHT -> {
                    tail.forEachIndexed { index, point ->
                        run {
                            val newPoint: Point = point
                            newPoint.x += SPEED
                            tail[index] = newPoint
                        }
                    }
                }
                Direction.DOWN -> {
                    tail.forEachIndexed { index, point ->
                        run {
                            val newPoint: Point = point
                            newPoint.y += SPEED
                            tail[index] = newPoint
                        }
                    }
                }
                else -> {}
            }
            if (apple != null) {
                if (head.x - apple!!.x < 2 && head.y - apple!!.y < 2) {
                    apple = null
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
                        if (index > 0 && paint.color == headColor)
                            paint.color = tailColor

                        canvas?.drawRect(point.x.toFloat(), point.y.toFloat(), (point.x + BLOCK_SIZE).toFloat(), (point.y + BLOCK_SIZE).toFloat(), paint)
                    }
                }
                if (direction != Direction.STOP)
                    move()
                return true
            }
        }
        return false
    }

}