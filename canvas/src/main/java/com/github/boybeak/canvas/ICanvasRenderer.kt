package com.github.boybeak.canvas

import android.opengl.GLSurfaceView
import android.view.SurfaceHolder
import com.github.boybeak.canvas.executor.IExecutor

interface ICanvasRenderer {
    companion object {
        const val RENDER_MODE_CONTINUOUSLY = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        const val RENDER_MODE_WHEN_DIRTY = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
    fun onSurfaceCreated(holder: SurfaceHolder, executor: IExecutor)
    fun onSurfaceChanged(holder: SurfaceHolder,
                         format: Int,
                         width: Int,
                         height: Int,
                         executor: IExecutor)
    fun onSurfaceDestroyed(holder: SurfaceHolder, executor: IExecutor)
    fun onRequestRender()
}