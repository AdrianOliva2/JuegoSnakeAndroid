package com.example.juegosnakeandroid.classes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import com.example.juegosnakeandroid.enums.Direction

class Snake(private var width: Int, private var height: Int, private val tailColor: Int, private val headColor: Int) {

    private var perdido = false
    var points: Int = 0

    companion object {
        private const val SPEED = 6
        private const val BLOCK_SIZE = 60
    }

    private var tail: MutableList<Point> = arrayListOf(Point(width / 2, height / 2))
    var direction = Direction.STOP

    private fun move() {
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
    }

    fun draw(canvas: Canvas?): Boolean {
        val head: Point = tail[tail.size - 1]
        if ((head.x < 0 || head.x > this.width) || (head.y < 0 || head.y > this.height)) {
            perdido = true
            return false
        } else {
            if (!perdido) {
                val paint = Paint()
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
            return false
        }
    }

}