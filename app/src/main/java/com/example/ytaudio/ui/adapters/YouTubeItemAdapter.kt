package com.example.ytaudio.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ytaudio.databinding.ItemYoutubeBinding
import com.example.ytaudio.vo.YouTubeItem


class YouTubeItemsAdapter(private val onClick: (YouTubeItem) -> Unit) :
    PagingDataAdapter<YouTubeItem, RecyclerView.ViewHolder>(YouTubeItemDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as YouTubeItemViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        YouTubeItemViewHolder.create(parent, onClick)


    class YouTubeItemViewHolder private constructor(
        private val binding: ItemYoutubeBinding,
        private val onClick: (item: YouTubeItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: YouTubeItem?) {
            item?.let {
                binding.youTubeItem = item
                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onClick: (item: YouTubeItem) -> Unit
            ): YouTubeItemViewHolder {
                val binding =
                    ItemYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return YouTubeItemViewHolder(binding, onClick)
            }
        }
    }
}


private class YouTubeItemDiffCallback : DiffUtil.ItemCallback<YouTubeItem>() {

    override fun areItemsTheSame(oldItem: YouTubeItem, newItem: YouTubeItem): Boolean =
        oldItem.videoId == newItem.videoId

    override fun areContentsTheSame(oldItem: YouTubeItem, newItem: YouTubeItem): Boolean =
        oldItem == newItem
}