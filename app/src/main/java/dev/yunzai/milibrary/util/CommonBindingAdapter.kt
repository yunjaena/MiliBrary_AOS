package dev.yunzai.milibrary.util

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setTextInputLayoutErrorText(view: TextInputLayout, errorText: String?) {
    view.error = errorText
}

@BindingAdapter("errorText")
fun setTextInputLayoutErrorText(view: TextInputLayout, @StringRes errorText: Int?) {
    if (errorText == null) {
        view.error = null
        return
    }
    view.error = view.context.getString(errorText)
}
