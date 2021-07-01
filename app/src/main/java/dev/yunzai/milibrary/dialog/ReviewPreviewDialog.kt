package dev.yunzai.milibrary.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.databinding.DialogReviewPreviewBinding

class ReviewPreviewDialog(
    private val context: Context
) {
    private val dialog = Dialog(context)
    var binding: DialogReviewPreviewBinding
    var bookClickListener: ((Int) -> Unit)? = null
    var review: Review? = null

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_review_preview, null, false)
        dialog.setContentView(binding.root)
        binding.confirmButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.bookCardView.setOnClickListener {
            if (review == null || review?.bookId == null) return@setOnClickListener
            bookClickListener?.invoke(review?.bookId!!)
            dialog.dismiss()
        }
    }

    fun show(review: Review) {
        this.review = review
        Glide.with(context)
            .load(review.book?.thumbnail)
            .into(binding.bookImage)

        binding.titleTextView.text = review.book?.title ?: ""
        binding.authorTextView.text = review.book?.authors ?: ""
        binding.categoryTextView.text = review.book?.categoryName ?: ""
        binding.publishDateTextView.text = review.book?.pubDate ?: ""
        binding.commentTextView.text = review.comment ?: ""
        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}
