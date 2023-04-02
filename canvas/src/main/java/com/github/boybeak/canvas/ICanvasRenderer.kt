package com.github.boybeak.canvas

import android.opengl.GLSurfaceView
import android.view.SurfaceHolder
import com.github.boybeak.canvas.executor.IExecutor
import com.github.boybeak.canvas.executor.RenderExecutor

interface ICanvasRenderer {
    companion object {
        const val RENDER_MODE_CONTINUOUSLY = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        const val RENDER_MODE_WHEN_DIRTY = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
    fun onSurfaceCreated(holder: SurfaceHolder, executor: RenderExecutor)
    fun onSurfaceChanged(holder: SurfaceHolder,
                         format: Int,
                         width: Int,
                         height: Int,
                         executor: RenderExecutor)
    fun onSurfaceDestroyed(holder: SurfaceHolder, executor: RenderExecutor)
    fun onRequestRender()
}