package com.talents.lyrics.feature_lyrics.presentation.search_songs

import com.talents.lyrics.core.models.DataSong

interface SongsListResultCallback {
    fun onItemClicked(dataSong: DataSong)
}