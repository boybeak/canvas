package com.github.boybeak.canvas.executor

interface IExecutor {
    fun start()
    fun stop()
    fun isRunning(): Boolean
    fun post(r: Runnable)
    fun postDelayed(delayed: Long, r: Runnable)
    fun remove(r: Runnable)
}