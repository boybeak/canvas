package com.github.boybeak.canvas

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.SurfaceHolder
import com.github.boybeak.canvas.ICanvasRenderer.Companion.RENDER_MODE_CONTINUOUSLY
import com.github.boybeak.canvas.ICanvasRenderer.Companion.RENDER_MODE_WHEN_DIRTY

abstract class AbsRenderer(@RenderMode val renderMode: Int) : ICanvasRenderer {

    companion object {
        private const val TAG = "AbsRenderer"
        private const val WHAT_RENDERER = 0
    }

    private val renderThread = HandlerThread("Rendering")
    private val callback = Handler.Callback { msg ->
        when(msg.what) {
            WHAT_RENDERER -> {
                if (isSurfaceAlive) {
                    onRequestRender()
                    if (renderMode == RENDER_MODE_CONTINUOUSLY) {
                        requestRender()
                    }
                }
            }
        }
        true
    }
    private val handler: Handler by lazy { Handler(renderThread.looper, callback) }

    @Volatile
    private var isRenderThreadAlive = false
    @Volatile
    internal var isSurfaceAlive = false
        private set

    init {
        if (renderMode != RENDER_MODE_CONTINUOUSLY && renderMode != RENDER_MODE_WHEN_DIRTY) {
            throw IllegalArgumentException("renderMode must be RENDER_MODE_CONTINUOUSLY or RENDER_MODE_WHEN_DIRTY")
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        isSurfaceAlive = true
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        isSurfaceAlive = false
    }

    override fun start() {
        renderThread.start()
        isRenderThreadAlive = true
    }

    override fun stop() {
        renderThread.quit()
        isRenderThreadAlive = false
    }

    fun post(r: Runnable) {
        if (!isRenderThreadAlive) {
            return
        }
        handler.post(r)
    }

    fun postDelayed(delayed: Long, r: Runnable) {
        if (!isRenderThreadAlive) {
            return
        }
        handler.postDelayed(r, delayed)
    }

    fun requestRender() {
        if (!isRenderThreadAlive) {
            return
        }
        if (!isSurfaceAlive) {
            return
        }
        handler.sendEmptyMessage(WHAT_RENDERER)
    }

    internal abstract fun onRequestRender()

}