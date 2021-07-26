package dev.yunzai.milibrary.fragments

import android.os.Bundle
import android.view.View
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.adapter.BookListAdapter
import dev.yunzai.milibrary.adapter.ReviewListAdapter
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.constant.SORT_TYPE_BOOK_YEAR_DESC_QRT_DESC
import dev.yunzai.milibrary.databinding.FragmentHomeBinding
import dev.yunzai.milibrary.dialog.ReviewPreviewDialog
import dev.yunzai.milibrary.util.goToBookDetailActivity
import dev.yunzai.milibrary.util.goToBookListActivity
import dev.yunzai.milibrary.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {
    override val layoutId: Int = R.layout.fragment_home
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var randomBookListAdapter: BookListAdapter
    private lateinit var newBookListAdapter: BookListAdapter
    private lateinit var randomReviewListAdapter: ReviewListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
        fetchData()
    }

    private fun initView() {
        randomBookListAdapter = BookListAdapter {
            if (it.id != null)
                requireContext().goToBookDetailActivity(it.id)
        }
        newBookListAdapter = BookListAdapter {
            if (it.id != null)
                requireContext().goToBookDetailActivity(it.id)
        }
        randomReviewListAdapter = ReviewListAdapter {
            ReviewPreviewDialog(requireContext()).apply {
                bookClickListener = {
                    requireContext().goToBookDetailActivity(it)
                }
                show(it)
            }
        }
        binding.randomBookRecyclerView.adapter = randomBookListAdapter
        binding.newBookRecyclerView.adapter = newBookListAdapter
        binding.randomReviewRecyclerView.adapter = randomReviewListAdapter
        binding.refreshLayout.setOnRefreshListener {
            fetchData()
        }
        binding.loadMoreNewBookListButton.setOnClickListener {
            requireContext().goToBookListActivity(SORT_TYPE_BOOK_YEAR_DESC_QRT_DESC)
        }
    }

    private fun fetchData() {
        randomBookListAdapter.clear()
        newBookListAdapter.clear()
        randomReviewListAdapter.clear()
        homeViewModel.getNewBookList()
        homeViewModel.getRandomBookList()
        homeViewModel.getRandomReview()
        if (binding.refreshLayout.isRefreshing) {
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun initObserver() {
        homeViewModel.randomBookList.observe(this) {
            if (it?.books.isNullOrEmpty()) {
                return@observe
            }
            randomBookListAdapter.add(it!!.books!!)
        }

        homeViewModel.newBookList.observe(this) {
            if (it?.books.isNullOrEmpty()) {
                return@observe
            }
            newBookListAdapter.add((it!!.books!!))
        }

        homeViewModel.randomReview.observe(this) {
            if (it == null) {
                return@observe
            }
            randomReviewListAdapter.add(arrayListOf(it))
        }
    }

    companion object {
        const val TAG = "HomeFragment"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
