package com.github.boybeak.canvas

import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder
import com.github.boybeak.canvas.executor.IExecutor
import com.github.boybeak.canvas.executor.RenderExecutor

abstract class TwoDRenderer : AbsRenderer() {
    private var surfaceHolder: SurfaceHolder? = null
    private var dirtyRect = Rect()

    override fun onSurfaceCreated(holder: SurfaceHolder, executor: RenderExecutor) {
        super.onSurfaceCreated(holder, executor)
        surfaceHolder = holder
    }

    override fun onSurfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int,
        executor: RenderExecutor
    ) {
        super.onSurfaceChanged(holder, format, width, height, executor)
        dirtyRect.set(0, 0, width, height)
        executor.requestRender()
    }

    override fun onSurfaceDestroyed(holder: SurfaceHolder, executor: RenderExecutor) {
        super.onSurfaceDestroyed(holder, executor)
        surfaceHolder = null
    }

    override fun onRequestRender() {
        surfaceHolder?.run {
            val canvas = surface.lockCanvas(dirtyRect)
            onDraw(canvas)
            surface.unlockCanvasAndPost(canvas)
        }
    }

    abstract fun onDraw(canvas: Canvas)

}