package com.talents.lyrics.feature_lyrics.data.remote

import com.talents.lyrics.core.models.DataSong


data class SearchResponse(var data: MutableList<DataSong>)

data class SearchLyrics(var lyrics: String)