package com.talents.lyrics.feature_lyrics.presentation.lyrics_song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.talents.lyrics.R
import com.talents.lyrics.databinding.FragmentLyricsBinding
import com.talents.lyrics.feature_lyrics.commom.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LyricsFragment : BaseFragment() {

    private lateinit var binding: FragmentLyricsBinding

    private val lyricsViewModel: LyricsViewModel by viewModels()

    private val args: LyricsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLyricsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = lyricsViewModel


        lyricsViewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        lyricsViewModel.onMessageError.observe(viewLifecycleOwner, messageErrorObserver)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lyricsViewModel.searchLyricsSong(requireContext(), args.nameArtist, args.nameSong)
    }

    private val isLoadingObserver: Observer<Boolean> = Observer { isLoading ->
        if (isLoading) {
            showDialogLoading()
        } else {
            dismissDialogLoading()
        }
    }

    private val messageErrorObserver: Observer<String?> = Observer {
        if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
            val errorMessage = it
                ?: requireContext().getString(R.string.your_request_could_not_processed_try_later)
            showInfoDialog(errorMessage) {
                findNavController().navigateUp()
            }
        }
    }

}