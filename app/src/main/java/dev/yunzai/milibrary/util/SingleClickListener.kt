package dev.yunzai.milibrary.util

import android.view.View
import com.orhanobut.logger.Logger

inline fun View.setOnSingleClickListener(
    interval: Long = 600L,
    crossinline action: (View) -> Unit
) {
    var clickable = true
    setOnClickListener {
        if (clickable) {
            clickable = false
            this.run {
                postDelayed({ clickable = true }, interval)
                action(this)
            }
        } else {
            Logger.d("Double Clicked wait for $interval")
        }
    }
}
