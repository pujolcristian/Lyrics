package com.talents.lyrics.feature_lyrics.data.repository

import com.talents.lyrics.feature_lyrics.data.remote.ApiCallback
import com.talents.lyrics.feature_lyrics.data.remote.ApiSongs
import com.talents.lyrics.feature_lyrics.data.remote.SearchLyrics
import com.talents.lyrics.feature_lyrics.data.remote.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val apiSongs: ApiSongs
) {

    fun getSongs(nameSongs: String, callback: ApiCallback<SearchResponse>) {
        val url = "${ApiSongs.BASE_URL}suggest/$nameSongs"

        val call = apiSongs.getSongs(url)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onError(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                callback.onError(t.message)
            }
        })
    }

    fun getLyricsSong(nameArtist: String, nameSong: String, callback: ApiCallback<SearchLyrics>) {
        val url = "${ApiSongs.BASE_URL}v1/$nameArtist/$nameSong"

        val call = apiSongs.getLyricsSong(url)

        call.enqueue(object : Callback<SearchLyrics> {
            override fun onResponse(call: Call<SearchLyrics>, response: Response<SearchLyrics>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onError(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<SearchLyrics>, t: Throwable) {
                callback.onError(t.message)
            }
        })
    }
}