package com.github.boybeak.canvas

import android.opengl.GLSurfaceView
import android.view.SurfaceHolder

interface ICanvasRenderer : SurfaceHolder.Callback {
    companion object {
        const val RENDER_MODE_CONTINUOUSLY = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        const val RENDER_MODE_WHEN_DIRTY = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
    fun start()
    fun stop()
}