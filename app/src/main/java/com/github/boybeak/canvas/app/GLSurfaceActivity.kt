package com.github.boybeak.canvas.app

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.github.boybeak.canvas.app.renderer.GLSurfaceRenderer
import kotlin.random.Random

class GLSurfaceActivity : AppCompatActivity() {

    private val glSurfaceView: GLSurfaceView by lazy { findViewById(R.id.glSurfaceView) }
    private val actionBtn: AppCompatButton by lazy { findViewById(R.id.actionBtn) }
    private val random = Random(System.currentTimeMillis())
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glsurface)

        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(GLSurfaceRenderer())
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        actionBtn.setOnClickListener {
            if (index % 2 == 0) {
                val r = random.nextInt(100) / 100F
                val g = random.nextInt(100) / 100F
                val b = random.nextInt(100) / 100F
                glSurfaceView.queueEvent {
                    GLES20.glClearColor(r, g, b, 1F)
                    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
                }
            }
            glSurfaceView.requestRender()
            index++
        }
    }
}