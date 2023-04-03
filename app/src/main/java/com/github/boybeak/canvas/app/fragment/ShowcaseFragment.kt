package com.github.boybeak.canvas.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.github.boybeak.canvas.CanvasView
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.RenderMode
import com.github.boybeak.canvas.app.R

class ShowcaseFragment(private val renderer: ICanvasRenderer, private val actionText: String = "",
                       @RenderMode private val renderMode: Int = ICanvasRenderer.RENDER_MODE_WHEN_DIRTY,
                       private val onClick: ((CanvasView) -> Unit)? = null) : DialogFragment(R.layout.fragment_showcase) {

    companion object {
        private const val TAG = "ShowcaseFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val canvasView = view.findViewById<CanvasView>(R.id.surfaceView).apply {
            setRenderer(renderer)
            setRenderMode(renderMode)
        }
        canvasView.addCallback(object : CanvasView.Callback {
            override fun onThreadCreated() {
                Log.d(TAG, "onThreadCreated")
            }

            override fun onSurfaceCreated() {
                Log.d(TAG, "onSurfaceCreated")
            }

            override fun onSurfaceDestroyed() {
                Log.d(TAG, "onSurfaceDestroyed")
            }

            override fun onThreadDestroyed() {
                Log.d(TAG, "onThreadDestroyed")
            }
        })

        view.findViewById<AppCompatButton>(R.id.actionBtn).apply {
            if (actionText.isNotEmpty()) {
                text = actionText
            }
            if (onClick != null) {
                setOnClickListener { onClick.invoke(canvasView) }
            } else {
                setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}