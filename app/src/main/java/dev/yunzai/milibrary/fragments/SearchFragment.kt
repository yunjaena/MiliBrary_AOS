package dev.yunzai.milibrary.fragments

import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentSearchBinding

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>() {
    override val layoutId: Int = R.layout.fragment_search

    companion object {
        const val TAG = "SearchFragment"

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
