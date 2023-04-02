package com.github.boybeak.canvas.app.renderer

import android.opengl.GLES20
import android.opengl.Matrix
import android.os.SystemClock
import com.github.boybeak.canvas.ICanvasRenderer
import com.github.boybeak.canvas.OpenGLRenderer
import com.github.boybeak.canvas.app.shape.Square
import com.github.boybeak.canvas.app.shape.Triangle
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig


class ShapeRenderer : OpenGLRenderer() {

    private val TAG = "MyGLRenderer"
    private var mTriangle: Triangle? = null
    private var mSquare: Square? = null

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private val mMVPMatrix = FloatArray(16)
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mRotationMatrix = FloatArray(16)
    private var mAngle = 0f

    override fun onSurfaceCreated(gl10: EGL10, config: EGLConfig) {
        // Set the background frame color
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        mTriangle = Triangle()
        mSquare = Square()
    }

    override fun onSurfaceChanged(gl10: EGL10, width: Int, height: Int) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height)
        val ratio = width.toFloat() / height
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1F, 1F, 3F, 7F)
    }

    override fun onDrawFrame(gl10: EGL10) {
        val scratch = FloatArray(16)
        // Create a rotation transformation for the triangle
        val time = SystemClock.uptimeMillis() % 4000L
        mAngle = 0.090f * time.toInt()
        // Draw background color
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        // Set the camera position (View matrix)
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0F, 0F, -3F, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        // Calculate the projection and view transformation
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
        // Draw square
        // Draw square
        mSquare!!.draw(mMVPMatrix)
        // Create a rotation for the triangle
        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
        // Create a rotation for the triangle
        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0F, 0F, 1.0f)
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0)
        // Draw triangle
        // Draw triangle
        mTriangle!!.draw(scratch)
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    fun getAngle(): Float {
        return mAngle
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    fun setAngle(angle: Float) {
        mAngle = angle
    }

}