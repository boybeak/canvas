package com.github.boybeak.canvas.app.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.github.boybeak.canvas.CanvasView
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.OpenGLRenderer
import com.github.boybeak.canvas.app.R

class Showcase2Fragment(private val renderer1: OpenGLRenderer, private val renderer2: OpenGLRenderer) : DialogFragment(R.layout.fragment_showcase2) {

    companion object {
        private const val TAG = "ShowcaseFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<CanvasView>(R.id.surfaceView1).apply {
            setRenderer(renderer1)
            setRenderMode(ICanvasRenderer.RENDER_MODE_CONTINUOUSLY)
        }
        view.findViewById<CanvasView>(R.id.surfaceView2).setRenderer(renderer2)

    }

}