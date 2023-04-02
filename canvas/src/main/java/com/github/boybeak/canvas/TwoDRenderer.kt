package com.github.boybeak.canvas

import android.graphics.Canvas
import android.graphics.Rect
import android.view.SurfaceHolder

abstract class TwoDRenderer(@RenderMode renderMode: Int) : AbsRenderer(renderMode) {
    private var surfaceHolder: SurfaceHolder? = null
    private var dirtyRect = Rect()
    override fun surfaceCreated(holder: SurfaceHolder) {
        super.surfaceCreated(holder)
        surfaceHolder = holder
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        super.surfaceChanged(holder, format, width, height)
        dirtyRect.set(0, 0, width, height)
        requestRender()
    }
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
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