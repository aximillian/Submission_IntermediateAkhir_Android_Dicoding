package com.example.bstory.ui.story.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.bstory.base.FragmentBase
import com.example.bstory.config.ext.formatDate
import com.example.bstory.databinding.FragmentDetailStoryBinding
import com.example.bstory.domain.story.Story
import org.koin.android.ext.android.inject

class DetailStoryFragment : FragmentBase<FragmentDetailStoryBinding>() {

    private val detailStoryViewModel: DetailStoryViewModel by inject()

    private var storyId: String? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailStoryBinding = FragmentDetailStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        storyId = arguments?.getString("storyId")
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initProcess() {
        detailStoryViewModel.detailStory(storyId.toString())
    }

    override fun initObservers() {
        detailStoryViewModel.detailStory(storyId.toString()).observe(viewLifecycleOwner) { result ->
            setStoryDetail(result)
        }
    }

    private fun setStoryDetail(story: Story) {
        binding.apply {
            tvItemUserLabel.text = story.name.first().toString().uppercase()
            tvDetailDescription.text = story.description
            tvDetailName.text = story.name
            tvDetailTimestamp.text = story.createdAt.formatDate()
            ivDetailPhoto.load(story.photoUrl) {
                placeholder(ColorDrawable(Color.LTGRAY))
                transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
            }
        }
    }
}