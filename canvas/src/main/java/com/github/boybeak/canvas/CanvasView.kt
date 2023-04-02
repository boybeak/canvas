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
    private val stateRememberCallback = StateRememberCallback()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        holder.addCallback(renderExecutor)
        holder.addCallback(stateRememberCallback)
    }

    fun setRenderer(renderer: ICanvasRenderer) {
        if (this.renderer != null) {
            throw IllegalStateException("Renderer has already set.")
        }
        val callback = object : SurfaceHolder.Callback {
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
        }
        // Make sure that renderer create and init correctly
        stateRememberCallback.perform(callback)
        holder.addCallback(callback)
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

    private class StateRememberCallback : SurfaceHolder.Callback {

        companion object {
            private const val STATE_IDLE = 0
            private const val STATE_CREATED = 1
            private const val STATE_CHANGED = 2
            private const val STATE_DESTROYED = -1
        }

        private var state = STATE_IDLE

        private var createHolder: SurfaceHolder? = null

        private var changeHolder: SurfaceHolder? = null
        private var format: Int = 0
        private var width: Int = 0
        private var height: Int = 0

        override fun surfaceCreated(holder: SurfaceHolder) {
            state = STATE_CREATED
            createHolder = holder
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            state = STATE_CHANGED
            changeHolder = holder
            this.format = format
            this.width = width
            this.height = height
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            state = STATE_DESTROYED
            createHolder = null
            changeHolder = null
            format = 0
            width = 0
            height = 0
        }

        fun perform(callback: SurfaceHolder.Callback) {
            when(state) {
                STATE_CREATED -> {
                    callback.surfaceCreated(createHolder!!)
                }
                STATE_CHANGED -> {
                    callback.surfaceCreated(createHolder!!)
                    callback.surfaceChanged(changeHolder!!, format, width, height)
                }
            }
        }

    }

}