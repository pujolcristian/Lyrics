package com.talents.lyrics.feature_lyrics.presentation.search_songs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.talents.lyrics.R
import com.talents.lyrics.core.models.DataSong
import com.talents.lyrics.databinding.FragmentSearchSongsBinding
import com.talents.lyrics.feature_lyrics.commom.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSongsFragment : BaseFragment(), SongsListResultCallback {

    private lateinit var binding: FragmentSearchSongsBinding

    private lateinit var adapter: SongsListAdapter

    private val searchSongsViewModel: SearchSongsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSongsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = searchSongsViewModel

        adapter = SongsListAdapter(this)
        binding.recyclerViewSongs.adapter = adapter


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.edittextSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    searchSongsViewModel.onSearchClick(p0!!.rootView)
                    return true
                }
                return false
            }

        })

        searchSongsViewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        searchSongsViewModel.onMessageError.observe(viewLifecycleOwner, messageErrorObserver)
        searchSongsViewModel.dataSongArtist.observe(viewLifecycleOwner, dataSongsObserver)

        return binding.root
    }

    override fun onItemClicked(dataSong: DataSong) {
        val nameSong = dataSong.title
        val nameArtist = dataSong.artist.name

        findNavController().navigate(
            SearchSongsFragmentDirections.navigateToLyricsFragment(
                nameArtist,
                nameSong
            )
        )
    }

    private val dataSongsObserver = Observer<MutableList<DataSong>> {
        adapter.submitList(it)
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
            showInfoDialog(errorMessage, buttonAcceptClick = null)
        }
    }

}