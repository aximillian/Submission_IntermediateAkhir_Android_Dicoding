package com.example.bstory.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.bstory.config.ext.formatDate
import com.example.bstory.databinding.ItemStoryBinding
import com.example.bstory.domain.story.Story


class StoryAdapter :
    PagingDataAdapter<Story, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            with(binding) {
                ivItemPhoto.load(story.photoUrl) {
                    placeholder(ColorDrawable(Color.LTGRAY))
                    transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
                }
                tvItemUserLabel.text = story.name.first().toString().uppercase()
                tvItemName.text = story.name
                tvItemDescription.text = story.description
                tvItemTimestamp.text = story.createdAt.formatDate()
                root.setOnClickListener {
                    val navigateToDetailStory =
                        HomeFragmentDirections.actionNavigationHomeToDetailStoryFragment(story.id)
                    it.findNavController().navigate(navigateToDetailStory)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}
