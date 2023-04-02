package com.github.boybeak.canvas.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.github.boybeak.canvas.AbsCanvasView
import com.github.boybeak.canvas.OpenGLRenderer
import com.github.boybeak.canvas.app.R

class Showcase2Fragment(private val renderer1: OpenGLRenderer, private val renderer2: OpenGLRenderer) : DialogFragment(R.layout.fragment_showcase2) {

    companion object {
        private const val TAG = "ShowcaseFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AbsCanvasView>(R.id.surfaceView1).setRenderer(renderer1)
        view.findViewById<AbsCanvasView>(R.id.surfaceView2).setRenderer(renderer2)

    }

}