package dev.yunzai.milibrary.activities

import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.base.hideKeyBoard
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.constant.EXTRA_BOOK_MARK_ID
import dev.yunzai.milibrary.databinding.ActivitiyBookmarkDetailBinding
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import dev.yunzai.milibrary.viewmodels.BookmarkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkDetailActivity : ViewBindingActivity<ActivitiyBookmarkDetailBinding>() {
    override val layoutId: Int = R.layout.activitiy_bookmark_detail
    private var bookmarkId: Int = -1
    private var bookId: Int = -1
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private val bookDetailViewModel: BookDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        bookmarkId = intent.getIntExtra(EXTRA_BOOK_MARK_ID, -1)
        bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        initView()
        initObserver()
        bookDetailViewModel.loadBookDetail(bookId)
        bookmarkViewModel.getMyBookmark(bookId)
    }

    private fun initView() {
        binding.submitButton.setOnSingleClickListener {
            val content = binding.contentEditText.text.toString().trim()
            bookmarkViewModel.updateBookmark(bookmarkId, content)
        }
    }

    private fun initObserver() {
        bookmarkViewModel.bookmark.observe(this) {
            if (binding.contentEditText.text.trim().isNotEmpty() || it == null) return@observe
            binding.contentEditText.setText(it.content)
        }

        bookmarkViewModel.bookmarkUpdateCompleteEvent.observe(this) {
            hideKeyBoard()
            Toast.makeText(this@BookmarkDetailActivity, R.string.complete_update_bookmark_content, Toast.LENGTH_SHORT)
                .show()
            finish()
        }

        bookDetailViewModel.bookDetailData.observe(this) { book ->
            with(book) {
                Glide.with(this@BookmarkDetailActivity)
                    .load(thumbnail)
                    .into(binding.bookImage)

                setAppBarTitle(getString(R.string.bookmark))
                binding.titleTextView.text = title ?: ""
                binding.authorTextView.text = authors ?: ""
                binding.categoryTextView.text = categoryName ?: ""
                binding.publishDateTextView.text = pubDate ?: ""
                binding.isbnDateTextView.text = getString(R.string.isbn_title, isbn ?: "")
            }
        }

        bookmarkViewModel.errorMessageEvent.observe(this) {
            if (it == null) return@observe
            Toast.makeText(this@BookmarkDetailActivity, it, Toast.LENGTH_SHORT).show()
        }
    }
}
