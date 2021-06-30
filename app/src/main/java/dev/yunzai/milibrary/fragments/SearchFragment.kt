package dev.yunzai.milibrary.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import com.orhanobut.logger.Logger
import com.paginate.Paginate
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.BookLongListAdapter
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.base.hideKeyBoard
import dev.yunzai.milibrary.databinding.FragmentSearchBinding
import dev.yunzai.milibrary.util.getEmojiFilter
import dev.yunzai.milibrary.util.goToBookDetailActivity
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.BookListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search
    private var target = TITLE
    private val bookListViewModel: BookListViewModel by viewModel()
    private lateinit var bookLongListAdapter: BookLongListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    private fun initView() {
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        bookLongListAdapter = BookLongListAdapter {
            if (it.id != null)
                requireContext().goToBookDetailActivity(it.id)
        }
        binding.bookSearchRecyclerView.adapter = bookLongListAdapter

        if (!bookListViewModel.searchList.isNullOrEmpty()) {
            bookLongListAdapter.add(bookListViewModel.searchList)
        }

        binding.searchEditText.filters = arrayOf(getEmojiFilter())
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Logger.d("SearchFragment : $position")
                when (position) {
                    0 -> target = TITLE
                    1 -> target = AUTHOR
                }
            }
        }
        binding.searchButton.setOnSingleClickListener {
            search()
        }

        val pagingCallBack = object : Paginate.Callbacks {
            override fun onLoadMore() {
                bookListViewModel.getNextSearchBook()
            }

            override fun isLoading(): Boolean {
                return bookListViewModel.isLoading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return bookListViewModel.isEndOfList()
            }
        }
        Paginate.with(binding.bookSearchRecyclerView, pagingCallBack)
            .setLoadingTriggerThreshold(5)
            .addLoadingListItem(false)
            .build()
    }

    private fun initObserver() {
        bookListViewModel.bookListFetchEvent.observe(this) {
            if (!it.isNullOrEmpty())
                bookLongListAdapter.add(it)
        }
    }

    private fun search() {
        requireActivity().hideKeyBoard()
        bookLongListAdapter.clear()
        val searchTarget = target
        val query = binding.searchEditText.text.toString().trim()
        bookListViewModel.searchBook(searchTarget, query)
    }

    companion object {
        const val TAG = "SearchFragment"
        const val TITLE = "title"
        const val AUTHOR = "author"

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
