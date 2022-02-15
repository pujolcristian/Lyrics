package com.talents.lyrics.feature_lyrics.data.remote

interface ApiCallback<T> {

    fun onSuccess(data: T?)
    fun onError(error: String?)

}