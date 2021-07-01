package dev.yunzai.milibrary.activities

import android.os.Bundle
import com.paginate.Paginate
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.ReviewLongListAdapter
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.SORT_TYPE_DATE
import dev.yunzai.milibrary.constant.SORT_TYPE_SCORE
import dev.yunzai.milibrary.databinding.ActivityMyReviewListBinding
import dev.yunzai.milibrary.dialog.ReviewSortDialog
import dev.yunzai.milibrary.util.goToBookDetailActivity
import dev.yunzai.milibrary.viewmodels.ReviewViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyReviewListActivity : ViewBindingActivity<ActivityMyReviewListBinding>() {
    override val layoutId: Int = R.layout.activity_my_review_list
    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var reviewLongListAdapter: ReviewLongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
        reviewViewModel.getMyReviewList(true)
    }

    private fun initView() {
        setBaseAppBar(getString(R.string.my_review))
        setBackKey()
        reviewLongListAdapter = ReviewLongListAdapter {
            if (it.bookId == null) return@ReviewLongListAdapter
            goToBookDetailActivity(it.bookId)
        }
        binding.reviewRecyclerView.adapter = reviewLongListAdapter
        val pagingCallBack = object : Paginate.Callbacks {
            override fun onLoadMore() {
                reviewViewModel.getMyReviewList()
            }

            override fun isLoading(): Boolean {
                return reviewViewModel.isLoading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return reviewViewModel.nextUrl == null
            }
        }

        setSortTypeButtonText()

        Paginate.with(binding.reviewRecyclerView, pagingCallBack)
            .setLoadingTriggerThreshold(5)
            .addLoadingListItem(false)
            .build()

        binding.sortTypeButton.setOnClickListener {
            ReviewSortDialog(this@MyReviewListActivity).apply {
                setOnSortTypeListener {
                    reviewViewModel.setMyReviewSortType(it)
                }
                setSortType(reviewViewModel.sortType.value!!)
                show()
            }
        }
    }

    private fun initObserver() {
        reviewViewModel.listClearEvent.observe(this) {
            reviewLongListAdapter.clear()
        }

        reviewViewModel.reviewListEvent.observe(this) {
            if (!it.isNullOrEmpty())
                reviewLongListAdapter.add(it)
        }

        reviewViewModel.sortType.observe(this) {
            setSortTypeButtonText()
        }
    }

    private fun setSortTypeButtonText() {
        binding.sortTypeButton.text = when (reviewViewModel.sortType.value) {
            SORT_TYPE_DATE -> getString(R.string.write_date)
            SORT_TYPE_SCORE -> getString(R.string.score)
            else -> getString(R.string.write_date)
        }
    }
}
