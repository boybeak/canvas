package com.github.boybeak.canvas

import android.opengl.EGL14
import android.util.Log
import android.view.SurfaceHolder
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.egl.EGLSurface

abstract class OpenGLRenderer(@RenderMode renderMode: Int) : AbsRenderer(renderMode) {

    companion object {
        private const val TAG = "OpenGLRenderer"
    }

    private val egl: EGL10 by lazy { EGLContext.getEGL() as EGL10 }
    private val display: EGLDisplay by lazy { egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY) }
    private lateinit var eglSurface: EGLSurface
    private lateinit var eglContext: EGLContext

    private fun createEGL(holder: SurfaceHolder) {
        egl.eglInitialize(display, null)

        val attributes = intArrayOf(
            EGL10.EGL_RED_SIZE, 8,
            EGL10.EGL_GREEN_SIZE, 8,
            EGL10.EGL_BLUE_SIZE, 8,
            EGL10.EGL_ALPHA_SIZE, 8,
            EGL10.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL10.EGL_NONE, 0,      // placeholder for recordable [@-3]
            EGL10.EGL_NONE
        )

        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        egl.eglChooseConfig(display, attributes, configs, configs.size, numConfigs)
        val config = configs[0]

        eglSurface = egl.eglCreateWindowSurface(
            display, config, holder.surface, intArrayOf(
                EGL14.EGL_NONE
            )
        )

        eglContext = egl.eglCreateContext(
            display, config, EGL10.EGL_NO_CONTEXT, intArrayOf(
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE
            )
        )
        egl.eglMakeCurrent(display, eglSurface, eglSurface, eglContext)

        onSurfaceCreated(egl, config!!)
    }
    private fun destroyEGL() {
        egl.eglDestroySurface(display, eglSurface)
        egl.eglMakeCurrent(display, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
            EGL10.EGL_NO_CONTEXT)
        egl.eglDestroyContext(display, eglContext)
        egl.eglTerminate(display)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        super.surfaceCreated(holder)
        post {
            createEGL(holder)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        super.surfaceChanged(holder, format, width, height)
        post { onSurfaceChanged(egl, width, height) }
        requestRender()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        super.surfaceDestroyed(holder)
        // Do not place this into post{} body, this lead to "requestBuffer:bufferQueue has no connected producer" problem
        destroyEGL()
    }

    override fun onRequestRender() {
        onDrawFrame(egl)
        egl.eglSwapBuffers(display, eglSurface)
    }

    fun queueEvent(task: Runnable) {
        post(task)
    }
    fun queueEvent(task: () -> Unit) {
        post { task.invoke() }
    }

    abstract fun onSurfaceCreated(gl10: EGL10, config: EGLConfig)
    abstract fun onSurfaceChanged(gl10: EGL10, width: Int, height: Int)
    abstract fun onDrawFrame(gl10: EGL10)

}