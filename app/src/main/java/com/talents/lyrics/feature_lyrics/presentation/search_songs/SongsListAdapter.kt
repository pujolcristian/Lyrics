package com.talents.lyrics.feature_lyrics.presentation.search_songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.talents.lyrics.core.models.DataSong
import com.talents.lyrics.databinding.ItemListSongsBinding
import com.talents.lyrics.feature_lyrics.commom.DataBoundListAdapter


class SongsListAdapter(
    private val albumsListResultCallback: SongsListResultCallback,
) :
    DataBoundListAdapter<DataSong, ItemListSongsBinding>(AlbumDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemListSongsBinding {
        return ItemListSongsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bind(binding: ItemListSongsBinding, item: DataSong) {
        binding.dataSong = item
        binding.urlImgAlbum = item.album.cover

        binding.setOnItemClickListener {
            binding.dataSong?.let { itemAlbum ->
                albumsListResultCallback.onItemClicked(itemAlbum)
            }
        }
    }

    private class AlbumDiffCallback : DiffUtil.ItemCallback<DataSong>() {
        override fun areItemsTheSame(oldItem: DataSong, newItem: DataSong): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.artist == newItem.artist &&
                    oldItem.album == newItem.album
        }

        override fun areContentsTheSame(oldItem: DataSong, newItem: DataSong): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.artist == newItem.artist &&
                    oldItem.album == newItem.album
        }
    }
}