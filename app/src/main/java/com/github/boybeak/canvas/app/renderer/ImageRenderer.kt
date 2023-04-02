package com.github.boybeak.canvas.app.renderer

import android.graphics.Bitmap
import android.opengl.GLES20
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.OpenGLRenderer
import com.github.boybeak.canvas.app.texture.BitmapTexture
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig

class ImageRenderer(private val bitmap: Bitmap) : OpenGLRenderer(ICanvasRenderer.RENDER_MODE_WHEN_DIRTY) {

    private val vertexShader = """
        attribute vec4 av_Position;//顶点位置
        attribute vec2 af_Position;//纹理位置
        varying vec2 v_texPo;//纹理位置  与fragment_shader交互
        void main() {
            v_texPo = af_Position;
            gl_Position = av_Position;
        }
    """.trimIndent()
    private val fragmentShader = """
        precision mediump float;//精度 为float
        varying vec2 v_texPo;//纹理位置  接收于vertex_shader
        uniform sampler2D sTexture;//纹理
        void main() {
            gl_FragColor=texture2D(sTexture, v_texPo);
        }
    """.trimIndent()

    private lateinit var bmpTexture: BitmapTexture

    override fun onSurfaceCreated(gl10: EGL10, config: EGLConfig) {
        bmpTexture = BitmapTexture()
        bmpTexture.onSurfaceCreated(bitmap, vertexShader, fragmentShader)
    }

    override fun onSurfaceChanged(gl10: EGL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height);
    }

    override fun onDrawFrame(gl10: EGL10) {
        bmpTexture.draw();
    }
}