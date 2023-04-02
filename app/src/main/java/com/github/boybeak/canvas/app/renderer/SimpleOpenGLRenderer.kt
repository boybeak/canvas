package com.github.boybeak.canvas.app.renderer

import com.github.boybeak.canvas.OpenGLRenderer
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig

class SimpleOpenGLRenderer() : OpenGLRenderer() {
    override fun onSurfaceCreated(gl10: EGL10, config: EGLConfig) {
    }

    override fun onSurfaceChanged(gl10: EGL10, width: Int, height: Int) {
    }

    override fun onDrawFrame(gl10: EGL10) {
    }
}