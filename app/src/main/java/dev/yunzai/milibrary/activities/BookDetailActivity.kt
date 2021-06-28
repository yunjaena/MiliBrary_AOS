package dev.yunzai.milibrary.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.databinding.ActivityBookDetailBinding
import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailActivity : ViewBindingActivity<ActivityBookDetailBinding>() {
    override val layoutId: Int = R.layout.activity_book_detail
    private val bookDetailViewModel: BookDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        initView()
        initObserver()
        bookDetailViewModel.loadBookDetail(bookId)
    }

    private fun initView() {
        setBaseAppBar()
        setBackKey()
        useDefaultLoading(bookDetailViewModel)
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
    }
}
