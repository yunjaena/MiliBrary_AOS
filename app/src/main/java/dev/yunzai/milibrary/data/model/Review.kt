package dev.yunzai.milibrary.data.model

data class Review(
    val bookId: Int? = null,
    val comment: String? = null,
    val createdAt: String? = null,
    val id: Int? = null,
    val narasarangId: String? = null,
    val score: Double? = null,
    val nickname: String? = null,
    val book: Book? = null
)
