package com.talents.lyrics.feature_lyrics.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MediatorLiveData
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("bindingEditTextValue")
fun bindingEditTextValue(textInputEditText: TextInputEditText, value: MediatorLiveData<String?>) {
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            value.value = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

@BindingAdapter("loadImageFromUrl")
fun bindingImageFromUrl(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        val url = imageUrl.replace("http://", "https://")
        Glide.with(imageView.context).load(url).into(imageView)
    }
}