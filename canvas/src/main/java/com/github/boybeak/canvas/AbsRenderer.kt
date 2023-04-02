package com.github.boybeak.canvas

import android.view.SurfaceHolder
import com.github.boybeak.canvas.executor.IExecutor

abstract class AbsRenderer() : ICanvasRenderer {

    companion object {
        private const val TAG = "AbsRenderer"
    }

    override fun onSurfaceCreated(holder: SurfaceHolder, executor: IExecutor) {
    }

    override fun onSurfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int,
        executor: IExecutor
    ) {
    }

    override fun onSurfaceDestroyed(holder: SurfaceHolder, executor: IExecutor) {
    }

}