package com.github.boybeak.canvas.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.github.boybeak.canvas.AbsCanvasView
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.OpenGLRenderer
import com.github.boybeak.canvas.app.R

class ShowcaseFragment(private val renderer: ICanvasRenderer, private val actionText: String = "",
                       private val onClick: OnClickListener? = null) : DialogFragment(R.layout.fragment_showcase) {

    companion object {
        private const val TAG = "ShowcaseFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AbsCanvasView>(R.id.surfaceView).setRenderer(renderer)

        view.findViewById<AppCompatButton>(R.id.actionBtn).apply {
            if (actionText.isNotEmpty()) {
                text = actionText
            }
            if (onClick != null) {
                setOnClickListener(onClick)
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
        renderer.stop()
    }

}