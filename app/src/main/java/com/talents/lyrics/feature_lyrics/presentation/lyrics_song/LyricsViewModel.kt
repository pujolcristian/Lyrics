package com.talents.lyrics.feature_lyrics.presentation.lyrics_song

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.talents.lyrics.NetworkHelper
import com.talents.lyrics.R
import com.talents.lyrics.feature_lyrics.data.remote.ApiCallback
import com.talents.lyrics.feature_lyrics.data.remote.SearchLyrics
import com.talents.lyrics.feature_lyrics.data.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LyricsViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val lyricsSong = MediatorLiveData<String>()
    val nameSong = MediatorLiveData<String>()

    private val _isLoading = MediatorLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onMessageError = MediatorLiveData<String?>()
    val onMessageError: LiveData<String?> = _onMessageError


    fun searchLyricsSong(context: Context, nameArtist: String, nameSong: String) {
        if (networkHelper.hasConnection()) {
            _isLoading.value = true
            this.nameSong.value = nameSong

            songRepository.getLyricsSong(nameArtist, nameSong, object : ApiCallback<SearchLyrics> {
                override fun onSuccess(data: SearchLyrics?) {
                    _isLoading.postValue(false)
                    if (data != null) {
                        if (data.lyrics.isNotEmpty()) {
                            lyricsSong.value = data.lyrics
                        } else {
                            _onMessageError.postValue(context.getString(R.string.lyrics_no_found))
                        }
                    }
                }

                override fun onError(error: String?) {
                    _isLoading.postValue(false)
                    _onMessageError.postValue(context.getString(R.string.lyrics_no_found))
                }

            })
        } else {
            _onMessageError.postValue(context.getString(R.string.need_connection_internet))
        }
    }

    fun onNavigateUpClick(view: View) {
        view.findNavController().navigateUp()
    }
}

