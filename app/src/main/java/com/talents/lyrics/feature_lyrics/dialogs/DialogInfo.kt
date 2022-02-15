package com.talents.lyrics.feature_lyrics.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.talents.lyrics.databinding.DialogInfoBinding


class DialogInfo : DialogFragment() {

    private lateinit var binding: DialogInfoBinding

    private var buttonAcceptClick: ((DialogInfo) -> Unit)? = null


    companion object {
        private const val ARGS_MESSAGE = "ARGS_MESSAGE"

        fun newInstance(
            buttonAcceptClick: ((DialogInfo) -> Unit)?,
            message: String
        ) = DialogInfo().apply {
            this.buttonAcceptClick = buttonAcceptClick
            arguments = Bundle().apply {
                putString(ARGS_MESSAGE, message)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogInfoBinding.inflate(inflater, container, false)

        arguments?.run {
            binding.message = this.getString(ARGS_MESSAGE, "")
        }

        binding.setOnAcceptClick {
            dialog?.dismiss()
            buttonAcceptClick?.invoke(this)
        }

        if (this.dialog != null && this.dialog!!.window != null) {
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }

        return binding.root
    }
}