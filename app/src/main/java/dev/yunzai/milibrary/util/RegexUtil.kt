package dev.yunzai.milibrary.util

const val PASSWORD_CHECK_REGEX =
    """^(?=.*[a-zA-Z])(?=.*[!@#${'$'}%^*+=-])(?=.*[0-9]).{8,}${'$'}"""

fun String.isPasswordValid() = PASSWORD_CHECK_REGEX.toRegex().matches(this)
