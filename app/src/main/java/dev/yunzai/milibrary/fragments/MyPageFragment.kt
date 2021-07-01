package dev.yunzai.milibrary.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.base.fragment.ViewBindingFragment
import dev.yunzai.milibrary.databinding.FragmentMyPageBinding
import dev.yunzai.milibrary.util.goToLoginActivity
import dev.yunzai.milibrary.util.goToMyReviewActivity
import dev.yunzai.milibrary.util.goToOssLibraryActivity
import dev.yunzai.milibrary.util.setOnSingleClickListener
import dev.yunzai.milibrary.viewmodels.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : ViewBindingFragment<FragmentMyPageBinding>() {
    override val layoutId: Int = R.layout.fragment_my_page
    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObserver()
        useDefaultLoading(myPageViewModel)
    }

    private fun initView() {
        binding.myReviewLayout.setOnSingleClickListener {
            requireContext().goToMyReviewActivity()
        }
        binding.openSourceLayout.setOnSingleClickListener {
            requireContext().goToOssLibraryActivity()
        }
        binding.logoutLayout.setOnSingleClickListener {
            myPageViewModel.logOut()
        }
    }

    private fun initObserver() {
        myPageViewModel.logOutEvent.observe(this) {
            when (it) {
                true -> {
                    Toast.makeText(requireContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                    requireContext().goToLoginActivity()
                }
                false -> {
                    Toast.makeText(requireContext(), getString(R.string.logout_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "MyPageFragment"

        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}
