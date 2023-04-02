package com.github.boybeak.canvas

import android.view.SurfaceHolder
import com.github.boybeak.canvas.executor.RenderExecutor

abstract class AbsRenderer : ICanvasRenderer {

    companion object {
        private const val TAG = "AbsRenderer"
    }

    override fun onSurfaceCreated(holder: SurfaceHolder, executor: RenderExecutor) {
    }

    override fun onSurfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int,
        executor: RenderExecutor
    ) {
    }

    override fun onSurfaceDestroyed(holder: SurfaceHolder, executor: RenderExecutor) {
    }
}