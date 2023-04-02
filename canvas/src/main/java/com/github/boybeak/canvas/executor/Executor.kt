package com.github.boybeak.canvas.executor

import android.os.Handler
import android.os.HandlerThread

open class Executor : IExecutor {
    private val renderThread = HandlerThread("Rendering")
    private val handler: Handler by lazy { Handler(renderThread.looper) }
    private var isRunning  = false

    override fun start() {
        renderThread.start()
        isRunning = true
    }

    override fun stop() {
        renderThread.quit()
        isRunning = false
    }

    override fun isRunning(): Boolean {
        return isRunning
    }

    override fun post(r: Runnable) {
        handler.post(r)
    }

    override fun postDelayed(delayed: Long, r: Runnable) {
        handler.postDelayed(r, delayed)
    }

    override fun remove(r: Runnable) {
        handler.removeCallbacks(r)
    }

    override fun runOnMyThread(r: Runnable) {
        if (Thread.currentThread() == renderThread) {
            r.run()
        } else {
            post(r)
        }
    }

}