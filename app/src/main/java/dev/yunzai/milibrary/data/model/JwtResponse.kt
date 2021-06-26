package dev.yunzai.milibrary.data.model

data class JwtResponse(
    val accessToken: AccessToken?,
    val refreshToken: RefreshToken?
)
