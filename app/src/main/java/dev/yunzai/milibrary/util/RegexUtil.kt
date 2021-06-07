package dev.yunzai.milibrary.util

const val PASSWORD_CHECK_REGEX =
    """^(?=.*[A-Za-z])(?=.*\d)(?=.*[${'$'}@${'$'}!%*#?&])[A-Za-z\d${'$'}@${'$'}!%*#?&]{8,}${'$'}"""

fun String.isPasswordValid() = PASSWORD_CHECK_REGEX.toRegex().matches(this)
