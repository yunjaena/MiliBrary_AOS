package dev.yunzai.milibrary.fragments

import android.os.Bundle
import android.view.View
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.BookmarkAdapter
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentBookMarkBinding
import dev.yunzai.milibrary.viewmodels.BookmarkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookMarkFragment : ViewBindingFragment<FragmentBookMarkBinding>() {
    override val layoutId: Int = R.layout.fragment_book_mark
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        bookmarkAdapter.clear()
        bookmarkViewModel.getMyAllBookmark()
    }

    private fun initView() {
        bookmarkAdapter = BookmarkAdapter(requireContext()) {
        }
        binding.bookmarkRecyclerView.adapter = bookmarkAdapter
    }

    private fun initObserver() {
        bookmarkViewModel.bookmarkFetchEvent.observe(this) {
            if (it == null) return@observe
            bookmarkAdapter.add(arrayListOf(it))
        }
    }

    companion object {
        const val TAG = "BookMarkFragment"

        @JvmStatic
        fun newInstance() = BookMarkFragment()
    }
}
