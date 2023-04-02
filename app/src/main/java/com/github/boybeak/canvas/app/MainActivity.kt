package com.github.boybeak.canvas.app

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.opengl.GLES20
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.github.boybeak.canvas.*
import com.github.boybeak.canvas.app.fragment.Showcase2Fragment
import com.github.boybeak.canvas.app.fragment.ShowcaseFragment
import com.github.boybeak.canvas.app.renderer.ImageRenderer
import com.github.boybeak.canvas.app.renderer.ShapeRenderer
import com.github.boybeak.canvas.app.renderer.SimpleOpenGLRenderer
import com.github.boybeak.canvas.app.shape.SimpleTriangle
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.randomColor2dBtn).setOnClickListener {
            val random = Random(System.currentTimeMillis())
            fun nextColorBit(): Int {
                return random.nextInt(0xFF)
            }
            showcase(renderer = object : TwoDRenderer() {
                override fun onDraw(canvas: Canvas) {
                    val r = nextColorBit()
                    val g = nextColorBit()
                    val b = nextColorBit()
                    canvas.drawARGB(0XFF, r, g, b)
                }
            }, text = "change") {
                it.requestRender()
            }
            Toast.makeText(this, "Click button to change color", Toast.LENGTH_LONG).show()
        }

        findViewById<AppCompatButton>(R.id.randomColorGLBtn).setOnClickListener {
            val random = Random(System.currentTimeMillis())
            fun nextColorBit(): Float {
                return random.nextInt(100) / 100F
            }
            showcase(SimpleOpenGLRenderer(), text = "change") {
                it.queueEvent {
                    val r = nextColorBit()
                    val g = nextColorBit()
                    val b = nextColorBit()
                    Log.d(TAG, "surfaceCreated r=$r, g=$g, b=$b")
                    GLES20.glClearColor(r, g, b, 1F)
                    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
                }
                it.requestRender()
            }
            Toast.makeText(this, "Click button to change color", Toast.LENGTH_LONG).show()
        }
        findViewById<AppCompatButton>(R.id.triangleBtn).setOnClickListener {
            showcase(SimpleOpenGLRenderer(), text = "Draw Triangle") {
                it.queueEvent {
                    SimpleTriangle().draw()
                }
                it.requestRender()
            }
        }
        findViewById<AppCompatButton>(R.id.moveBtn).setOnClickListener {
            showcase(ShapeRenderer(), renderMode = ICanvasRenderer.RENDER_MODE_CONTINUOUSLY)
        }
        findViewById<AppCompatButton>(R.id.imageBtn).setOnClickListener {
            showcase(ImageRenderer(BitmapFactory.decodeResource(resources, R.drawable.dragon_ball)))
        }
        findViewById<AppCompatButton>(R.id.doubleBtn).setOnClickListener {
            Showcase2Fragment(ShapeRenderer(), ImageRenderer(BitmapFactory.decodeResource(resources, R.drawable.dragon_ball)))
                .show(supportFragmentManager, TAG)
        }
    }

    private fun <T : ICanvasRenderer> showcase(renderer: T, @RenderMode renderMode: Int = ICanvasRenderer.RENDER_MODE_WHEN_DIRTY,
                                               text: String = "", onClick: ((CanvasView) -> Unit)? = null) {
        ShowcaseFragment(renderer, text, renderMode, onClick).show(supportFragmentManager, TAG)
    }

}