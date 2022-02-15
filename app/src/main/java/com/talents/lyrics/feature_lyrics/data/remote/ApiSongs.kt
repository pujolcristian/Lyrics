package com.talents.lyrics.feature_lyrics.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiSongs {

    @GET
    fun getSongs(@Url url: String): Call<SearchResponse>

    @GET
    fun getLyricsSong(@Url url: String): Call<SearchLyrics>

    companion object {
        const val BASE_URL = "https://api.lyrics.ovh/"
    }
}