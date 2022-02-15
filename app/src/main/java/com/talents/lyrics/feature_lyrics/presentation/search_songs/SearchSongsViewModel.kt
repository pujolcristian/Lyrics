package com.talents.lyrics.feature_lyrics.presentation.search_songs

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.talents.lyrics.NetworkHelper
import com.talents.lyrics.R
import com.talents.lyrics.core.models.DataSong
import com.talents.lyrics.feature_lyrics.data.remote.ApiCallback
import com.talents.lyrics.feature_lyrics.data.remote.SearchResponse
import com.talents.lyrics.feature_lyrics.data.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchSongsViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val nameSong = MediatorLiveData<String>()

    private val _dataSongs = MediatorLiveData<MutableList<DataSong>>()
    val dataSongArtist: LiveData<MutableList<DataSong>> = _dataSongs

    private val _isLoading = MediatorLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onMessageError = MediatorLiveData<String?>()
    val onMessageError: LiveData<String?> = _onMessageError

    private fun searchSong(view: View) {
        _isLoading.value = true

        songRepository.getSongs(nameSong.value!!, object : ApiCallback<SearchResponse> {
            override fun onSuccess(data: SearchResponse?) {
                _isLoading.postValue(false)
                if (data != null) {
                    if (!data.data.isNullOrEmpty()) {
                        _dataSongs.value = data.data
                    } else {
                        _onMessageError.value = view.context.getString(R.string.song_no_found)
                    }
                }
            }

            override fun onError(error: String?) {
                _isLoading.postValue(false)
                _onMessageError.postValue(error)
            }
        })
    }

    fun onSearchClick(view: View) {
        if (networkHelper.hasConnection()) {
            if (isValidForm(view.context)) {
                searchSong(view)
            }
        } else {
            _onMessageError.postValue(view.context.getString(R.string.need_connection_internet))
        }
    }

    private fun isValidForm(context: Context): Boolean {
        val textSearch = nameSong.value?.trim()

        if (textSearch.isNullOrEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.enter_song_name),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}