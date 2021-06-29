package dev.yunzai.milibrary.activities

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.databinding.ActivityBookDetailBinding
import dev.yunzai.milibrary.util.goToReviewEditActivity
import dev.yunzai.milibrary.util.goToReviewListActivity
import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import dev.yunzai.milibrary.viewmodels.ReviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailActivity : ViewBindingActivity<ActivityBookDetailBinding>() {
    override val layoutId: Int = R.layout.activity_book_detail
    private val bookDetailViewModel: BookDetailViewModel by viewModel()
    private val reviewViewModel: ReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        setBaseAppBar()
        setBackKey()
        useDefaultLoading(bookDetailViewModel)
        binding.ratingDetailArrowImageView.setOnClickListener {
            val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
            goToReviewListActivity(bookId)
        }
    }

    override fun onResume() {
        super.onResume()
        val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        reviewViewModel.getMyReview(bookId)
        bookDetailViewModel.loadBookDetail(bookId)
    }

    private fun initObserver() {
        bookDetailViewModel.bookDetailData.observe(this) { book ->
            with(book) {
                val rating = (averageScore ?: "0.0").toFloat()

                Glide.with(this@BookDetailActivity)
                    .load(thumbnail)
                    .into(binding.bookImage)

                setAppBarTitle(title ?: "")
                binding.titleTextView.text = title ?: ""
                binding.authorTextView.text = authors ?: ""
                binding.categoryTextView.text = categoryName ?: ""
                binding.publishDateTextView.text = pubDate ?: ""
                binding.isbnDateTextView.text = getString(R.string.isbn_title, isbn ?: "")
                binding.ratingTextView.text = rating.toString()
                binding.ratingBar.rating = rating
                binding.descriptionTextView.text = description ?: ""
            }
        }
        reviewViewModel.myReview.observe(this) { review ->
            val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
            if (review.bookId == null) {
                binding.myRatingGroup.visibility = View.GONE
                binding.myReviewEditTextView.text = getString(R.string.write_review)
                binding.myReviewEditTextView.setOnClickListener {
                    goToReviewEditActivity(bookId, false)
                }
                return@observe
            }
            binding.myRatingGroup.visibility = View.VISIBLE
            binding.myReviewEditTextView.text = getString(R.string.edit_review)
            binding.myReviewCreatedDateTextView.text = review.createdAt
            binding.myRatingBar.rating = (review.score ?: 0).toFloat()
            binding.myReviewTextView.text = review.comment ?: ""
            binding.myReviewEditTextView.setOnClickListener {
                goToReviewEditActivity(bookId, true)
            }
        }
    }
}
