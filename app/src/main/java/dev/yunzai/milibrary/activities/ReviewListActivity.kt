package dev.yunzai.milibrary.activities

import android.os.Bundle
import com.bumptech.glide.Glide
import com.paginate.Paginate
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.ReviewLongListAdapter
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_ID
import dev.yunzai.milibrary.databinding.ActivityReviewListBinding
import dev.yunzai.milibrary.viewmodels.BookDetailViewModel
import dev.yunzai.milibrary.viewmodels.ReviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewListActivity : ViewBindingActivity<ActivityReviewListBinding>() {
    override val layoutId: Int = R.layout.activity_review_list
    private val reviewViewModel: ReviewViewModel by viewModel()
    private val bookDetailViewModel: BookDetailViewModel by viewModel()
    private var bookId: Int = -1
    private lateinit var reviewLongListAdapter: ReviewLongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        initView()
        initObserver()
        reviewViewModel.getReviewList(bookId, true)
        bookDetailViewModel.loadBookDetail(bookId)
    }

    private fun initView() {
        reviewLongListAdapter = ReviewLongListAdapter()
        binding.reviewRecyclerView.adapter = reviewLongListAdapter
        val pagingCallBack = object : Paginate.Callbacks {
            override fun onLoadMore() {
                reviewViewModel.getReviewList(bookId)
            }

            override fun isLoading(): Boolean {
                return reviewViewModel.isLoading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return reviewViewModel.nextUrl == null
            }
        }
        Paginate.with(binding.reviewRecyclerView, pagingCallBack)
            .setLoadingTriggerThreshold(5)
            .addLoadingListItem(false)
            .build()
    }

    private fun initObserver() {
        reviewViewModel.listClearEvent.observe(this) {
            reviewLongListAdapter.clear()
        }

        reviewViewModel.reviewListEvent.observe(this) {
            if (!it.isNullOrEmpty())
                reviewLongListAdapter.add(it)
        }

        bookDetailViewModel.bookDetailData.observe(this) { book ->
            with(book) {
                Glide.with(this@ReviewListActivity)
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
    }
}
