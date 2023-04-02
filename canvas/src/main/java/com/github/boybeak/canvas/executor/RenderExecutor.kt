package com.github.boybeak.canvas.executor

import android.util.Log
import android.view.SurfaceHolder
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.RenderMode

abstract class RenderExecutor : Executor(), SurfaceHolder.Callback {

    companion object {
        private const val TAG = "RenderExecutor"
    }

    private var renderMode = ICanvasRenderer.RENDER_MODE_WHEN_DIRTY

    private val renderTask = object : Runnable {
        override fun run() {
            if (!isSurfaceAvailable) {
                return
            }
            Log.d(TAG, "renderTask renderMode=$renderMode")
            onRequestRender()
            if (renderMode == ICanvasRenderer.RENDER_MODE_CONTINUOUSLY) {
                post(this)
            }
        }
    }
    private var isSurfaceAvailable = false
    private val isContinuouslyRendering: Boolean get() = renderMode == ICanvasRenderer.RENDER_MODE_CONTINUOUSLY && isSurfaceAvailable

    fun setRenderMode(@RenderMode renderMode: Int) {
        if (renderMode != ICanvasRenderer.RENDER_MODE_CONTINUOUSLY && renderMode != ICanvasRenderer.RENDER_MODE_WHEN_DIRTY) {
            throw IllegalArgumentException("renderMode must be RENDER_MODE_CONTINUOUSLY or RENDER_MODE_WHEN_DIRTY")
        }
        if (isRunning()) {
            throw IllegalStateException("You can not change renderMode when running")
        }
        this.renderMode = renderMode
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        isSurfaceAvailable = true
        Log.e(TAG, "surfaceCreated")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.e(TAG, "surfaceChanged renderMode=$renderMode")
        requestRender()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopContinuouslyRendering()
        isSurfaceAvailable = false
    }

    /*private fun startContinuouslyRendering() {
        Log.d(TAG, "startContinuouslyRendering")
        if (isContinuouslyRendering) {
            return
        }
        post(renderTask)
    }*/
    private fun stopContinuouslyRendering() {
        remove(renderTask)
    }
    fun requestRender() {
        if (!isSurfaceAvailable) {
            return
        }
        post(renderTask)
    }

    abstract fun onRequestRender()

}