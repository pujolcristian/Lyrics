package com.talents.lyrics.feature_lyrics.commom

import androidx.fragment.app.Fragment
import com.talents.lyrics.feature_lyrics.dialogs.DialogInfo
import com.talents.lyrics.feature_lyrics.dialogs.DialogLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    private var dialogLoading: DialogLoading? = null

    private var dialogInfo: DialogInfo? = null

    fun showDialogLoading() {
        dismissAllDialogs()
        if (dialogLoading == null) {
            dialogLoading = DialogLoading()
            dialogLoading?.isCancelable = false
            if (!dialogLoading?.isAdded!!) {
                dialogLoading?.show(requireActivity().supportFragmentManager, TAG_LOADING_DIALOG)
            }
        }
    }

    fun dismissDialogLoading() {
        if (dialogLoading != null) {
            if (dialogLoading?.isAdded!!) {
                dialogLoading?.dismiss()
                dialogLoading = null
            }
        }
    }

    fun showInfoDialog(
        message: String,
        buttonAcceptClick: ((DialogInfo) -> Unit)?
    ) {
        dismissAllDialogs()
        if (dialogInfo == null) {
            dialogInfo = DialogInfo.newInstance(buttonAcceptClick = {
                buttonAcceptClick?.invoke(it)
                it.dismiss()
                dialogInfo = null
            }, message)
            dialogInfo?.isCancelable = false
            if (!dialogInfo?.isAdded!!) {
                dialogInfo?.show(requireActivity().supportFragmentManager, TAG_INFO_DIALOG)
            }
        }
    }

    private fun dismissAllDialogs() {
        (parentFragmentManager.findFragmentByTag(TAG_LOADING_DIALOG) as? DialogLoading)?.dismiss()
        (parentFragmentManager.findFragmentByTag(TAG_INFO_DIALOG) as? DialogInfo)?.dismiss()
    }

    companion object {
        private const val TAG_LOADING_DIALOG = "loadingDialog"
        private const val TAG_INFO_DIALOG = "infoDialog"
    }

}