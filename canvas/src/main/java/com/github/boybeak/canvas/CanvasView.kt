package com.github.boybeak.canvas

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.github.boybeak.canvas.executor.Executor
import com.github.boybeak.canvas.executor.RenderExecutor

open class CanvasView : SurfaceView {

    companion object {
        private const val TAG = "AbsCanvasView"
    }

    private var renderer: ICanvasRenderer? = null
    private val renderExecutor = object : RenderExecutor() {
        override fun onRequestRender() {
            renderer?.onRequestRender()
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        holder.addCallback(renderExecutor)
    }

    fun setRenderer(renderer: ICanvasRenderer) {
        if (this.renderer != null) {
            throw IllegalStateException("Renderer has already set.")
        }
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                renderer.onSurfaceCreated(holder, renderExecutor)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                renderer.onSurfaceChanged(holder, format, width, height, renderExecutor)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                renderer.onSurfaceDestroyed(holder, renderExecutor)
            }
        })
        this.renderer = renderer
    }

    fun setRenderMode(@RenderMode renderMode: Int) {
        if (renderer == null) {
            throw IllegalStateException("You must set renderer before calling setRenderMode")
        }
        renderExecutor.setRenderMode(renderMode)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        renderExecutor.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        renderExecutor.stop()
    }

    fun requestRender() {
        renderExecutor.requestRender()
    }

    fun queueEvent(event: Runnable) {
        renderExecutor.post(event)
    }

}