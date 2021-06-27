package dev.yunzai.milibrary.data.model

data class SignInResponse(
    val narasarangId: String,
    val nickname: String,
    val registeredAt: String,
    val tokens: JwtResponse
)
