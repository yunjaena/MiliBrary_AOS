package dev.yunzai.milibrary.util

import android.text.InputFilter

fun getEmojiFilter() = InputFilter { source, start, end, dest, dstart, dend ->
    for (index in start until end) {
        val type = Character.getType(source[index]).toByte()
        if (type == Character.SURROGATE || type == Character.NON_SPACING_MARK || type == Character.OTHER_SYMBOL
        )
            return@InputFilter ""
    }
    null
}
