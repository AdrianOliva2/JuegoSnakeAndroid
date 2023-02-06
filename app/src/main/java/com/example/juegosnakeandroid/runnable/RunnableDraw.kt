package com.example.juegosnakeandroid.runnable

import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Thread.sleep

class RunnableDraw(private val sfView: SurfaceView): Runnable {

    companion object {
        const val FPS: Int = 60
    }

    var run: Boolean = false
    private val sfHolder: SurfaceHolder = sfView.holder

    override fun run() {
        val tPS: Int = 1000 / FPS
        var startTime: Long
        var sleepTime: Long
        while (run) {
            var canvas: Canvas? = null
            startTime = System.currentTimeMillis()
            try {
                canvas = sfHolder.lockCanvas()
                if (canvas != null) {
                    synchronized(sfHolder) {
                        sfView.postInvalidate()
                    }
                }
            } finally {
                if (canvas != null) {
                    sfHolder.unlockCanvasAndPost(canvas)
                }
                sleepTime = tPS - (System.currentTimeMillis() - startTime)
                try {
                    if (sleepTime > 0) sleep(sleepTime)
                    else sleep(10)
                } catch (_: Exception) {}
            }
        }
    }

}