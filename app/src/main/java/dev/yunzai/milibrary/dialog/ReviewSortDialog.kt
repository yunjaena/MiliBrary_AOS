package dev.yunzai.milibrary.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.constant.SORT_TYPE_DATE
import dev.yunzai.milibrary.constant.SORT_TYPE_SCORE
import dev.yunzai.milibrary.databinding.DialogReviewSortBinding

class ReviewSortDialog(
    context: Context
) {
    private val dialog = Dialog(context)
    var binding: DialogReviewSortBinding
    private var sortTypeListener: ((String) -> Unit)? = null
    private var currentSortType: String? = null

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_review_sort, null, false)
        dialog.setContentView(binding.root)
        binding.confirmButton.setOnClickListener {
            if (!currentSortType.isNullOrEmpty())
                sortTypeListener?.invoke(currentSortType!!)
            dialog.dismiss()
        }
        binding.sortTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentSortType = when (checkedId) {
                R.id.date_radio_button -> SORT_TYPE_DATE
                R.id.rating_radio_button -> SORT_TYPE_SCORE
                else -> SORT_TYPE_DATE
            }
        }
    }

    fun show() {
        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun setSortType(type: String) {
        currentSortType = when (type) {
            SORT_TYPE_DATE -> {
                binding.dateRadioButton.isChecked = true
                SORT_TYPE_DATE
            }
            SORT_TYPE_SCORE -> {
                binding.ratingRadioButton.isChecked = true
                SORT_TYPE_SCORE
            }
            else -> {
                binding.dateRadioButton.isChecked = true
                SORT_TYPE_DATE
            }
        }
    }

    fun setOnSortTypeListener(listener: (String) -> Unit) {
        this.sortTypeListener = listener
    }
}
