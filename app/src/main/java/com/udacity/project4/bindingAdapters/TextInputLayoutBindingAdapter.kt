package com.udacity.project4.bindingAdapters

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["app:errorMessage", "app:shouldDisplayError"])
    fun setError(
        textInputLayout: TextInputLayout,
        errorMessage: String?,
        isEnabled: Boolean
    ) {
        if (isEnabled) {
            textInputLayout.error = errorMessage
        } else {
            textInputLayout.error = null
        }
        textInputLayout.isErrorEnabled = isEnabled
    }
}