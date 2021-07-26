package dev.yunzai.milibrary.data.model

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.HttpException

data class ErrorResponse(
    val error: Error?
)

fun Throwable.toErrorResponse(): ArrayList<String>? {
    if (this !is HttpException) return null
    val body = this.response()?.errorBody()
    val gson = Gson()
    val adapter: TypeAdapter<ErrorResponse> = gson.getAdapter(ErrorResponse::class.java)
    return try {
        val errorMessage: ErrorResponse = adapter.fromJson(body?.string())
        errorMessage.error?.messages
    } catch (e: Exception) {
        null
    }
}
