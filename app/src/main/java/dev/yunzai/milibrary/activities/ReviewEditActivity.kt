package dev.yunzai.milibrary.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.constant.EXTRA_EDIT_MODE
import dev.yunzai.milibrary.databinding.ActivityReviewEditBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import dev.yunzai.milibrary.viewmodels.ReviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewEditActivity : ViewBindingActivity<ActivityReviewEditBinding>() {
    override val layoutId: Int = R.layout.activity_review_edit
    private val bookDetailViewModel: BookDetailViewModel by viewModel()
    private val reviewViewModel: ReviewViewModel by viewModel()
    private var reviewId: Int = 0
    var isEditMode = false
    var bookId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        isEditMode = intent.getBooleanExtra(EXTRA_EDIT_MODE, false)
        bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        initObserver()
        useDefaultLoading(bookDetailViewModel)
        bookDetailViewModel.loadBookDetail(bookId)
        binding.submitButton.setOnSingleClickListener {
            val comment = binding.reviewEditText.text.trim().toString()
            val score = binding.ratingBar.rating.toDouble()
            when (isEditMode) {
                true -> reviewViewModel.editReview(bookId, reviewId, comment, score)
                false -> reviewViewModel.postReview(bookId, comment, score)
            }
        }
        if (isEditMode)
            reviewViewModel.getMyReview(bookId)
    }

    private fun initObserver() {
        bookDetailViewModel.bookDetailData.observe(this) { book ->
            with(book) {
                val rating = (averageScore ?: "0.0").toFloat()
                Glide.with(this@ReviewEditActivity)
                    .load(thumbnail)
                    .into(binding.bookImage)

                setAppBarTitle(title ?: "")
                binding.titleTextView.text = title ?: ""
                binding.authorTextView.text = authors ?: ""
                binding.categoryTextView.text = categoryName ?: ""
                binding.publishDateTextView.text = pubDate ?: ""
                binding.isbnDateTextView.text = getString(R.string.isbn_title, isbn ?: "")
            }
        }

        reviewViewModel.myReview.observe(this) { review ->
            reviewId = review.id!!
            if (review?.comment != null && binding.reviewEditText.text.isNullOrEmpty())
                binding.reviewEditText.setText(review.comment)

            if (review?.score != null && binding.ratingBar.rating == 0f)
                binding.ratingBar.rating = review.score.toFloat()
        }

        reviewViewModel.editReviewCompleteEvent.observe(this) {
            finish()
        }
    }
}
