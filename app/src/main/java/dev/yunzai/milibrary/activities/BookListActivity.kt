package dev.yunzai.milibrary.activities

import android.os.Bundle
import com.paginate.Paginate
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.BookLongListAdapter
import dev.yunzai.milibrary.base.activity.ViewBindingActivity
import dev.yunzai.milibrary.constant.EXTRA_BOOK_LIST_SORT_TYPE
import dev.yunzai.milibrary.databinding.ActivityBookListBinding
import dev.yunzai.milibrary.util.goToBookDetailActivity
import dev.yunzai.milibrary.viewmodels.BookListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListActivity : ViewBindingActivity<ActivityBookListBinding>() {
    override val layoutId: Int = R.layout.activity_book_list
    private val bookListViewModel: BookListViewModel by viewModel()
    private lateinit var bookLongListAdapter: BookLongListAdapter
    private lateinit var sortType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sortType = intent.getStringExtra(EXTRA_BOOK_LIST_SORT_TYPE) ?: run {
            finish()
            return
        }
        init()
    }

    private fun init() {
        setBaseAppBar(getString(R.string.new_book))
        setBackKey()
        initView()
        initObserver()
        bookListViewModel.getBookList(sortType)
    }

    private fun initView() {
        bookLongListAdapter = BookLongListAdapter {
            if (it.id != null)
                goToBookDetailActivity(it.id)
        }
        binding.bookListRecyclerView.adapter = bookLongListAdapter

        if (!bookListViewModel.bookList.isNullOrEmpty()) {
            bookLongListAdapter.add(bookListViewModel.bookList)
        }

        val pagingCallBack = object : Paginate.Callbacks {
            override fun onLoadMore() {
                bookListViewModel.getNextBookList()
            }

            override fun isLoading(): Boolean {
                return bookListViewModel.isLoading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return bookListViewModel.isEndOfSortList()
            }
        }
        Paginate.with(binding.bookListRecyclerView, pagingCallBack)
            .setLoadingTriggerThreshold(5)
            .addLoadingListItem(false)
            .build()
    }

    private fun initObserver() {
        bookListViewModel.sortBookListFetchEvent.observe(this) {
            if (!it.isNullOrEmpty())
                bookLongListAdapter.add(it)
        }
    }
}
