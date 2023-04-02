package com.github.boybeak.canvas

import androidx.annotation.IntDef
import com.github.boybeak.canvas.ICanvasRenderer.Companion.RENDER_MODE_CONTINUOUSLY
import com.github.boybeak.canvas.ICanvasRenderer.Companion.RENDER_MODE_WHEN_DIRTY

@IntDef(RENDER_MODE_CONTINUOUSLY, RENDER_MODE_WHEN_DIRTY)
@Retention(AnnotationRetention.SOURCE)
annotation class RenderMode