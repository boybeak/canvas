package com.github.boybeak.canvas

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView

class AbsCanvasView : SurfaceView {

    companion object {
        private const val TAG = "AbsCanvasView"
    }

    private var renderer: ICanvasRenderer? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setRenderer(renderer: ICanvasRenderer) {
        if (this.renderer != null) {
            throw IllegalStateException("Renderer has already set.")
        }
        holder.addCallback(renderer)
        this.renderer = renderer
        if (isAttachedToWindow) {
            this.renderer?.start()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
        renderer?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow")
        renderer?.stop()
    }

}