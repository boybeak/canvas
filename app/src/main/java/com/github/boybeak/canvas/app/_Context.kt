package com.github.boybeak.canvas.app

import android.app.Activity
import android.content.Intent

fun Activity.gotoActivity(activityClz: Class<out Activity>) {
    startActivity(Intent(this, activityClz))
}