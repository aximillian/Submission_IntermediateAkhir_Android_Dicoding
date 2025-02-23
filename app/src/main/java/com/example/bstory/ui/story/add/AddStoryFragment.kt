package com.example.bstory.ui.story.add

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.bstory.R
import com.example.bstory.base.FragmentBase
import com.example.bstory.config.Helper
import com.example.bstory.config.PreferenceConfig
import com.example.bstory.config.ext.gone
import com.example.bstory.config.ext.show
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.databinding.FragmentAddStoryBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class AddStoryFragment : FragmentBase<FragmentAddStoryBinding>() {
    private val preferenceManager: PreferenceConfig by inject()
    private val addStoryViewModel: AddStoryViewModel by inject()

    private var currentImageUri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat: Double? = null
    private var lon: Double? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            launchUCrop(uri)
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            launchUCrop(currentImageUri!!)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Helper.showErrorToast(requireActivity(), getString(R.string.location_denied))
        }
    }

    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    setImagePreview()
                }
            }
        }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding = FragmentAddStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.apply {
            tvUserLabel.text = preferenceManager.getName?.first().toString().uppercase()
        }
    }

    override fun initAction() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            ibCameraButton.setOnClickListener {
                currentImageUri = Helper.getImageUri(requireActivity())
                launcherCamera.launch(currentImageUri)
            }
            ibGalleryButton.setOnClickListener {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            smLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                lat = location.latitude
                                lon = location.longitude
                            }
                        }
                    }
                }
            }
            buttonAdd.setOnClickListener {
                val description = edAddDescription.text.toString()

                if (description.isNotEmpty() && currentImageUri != null) {
                    addStoryViewModel.addStory(
                        currentImageUri!!,
                        description,
                        lat ?: 0.0,
                        lon ?: 0.0
                    )
                } else {
                    Helper.showErrorToast(requireActivity(), getString(R.string.error_add_story))
                }
            }
        }
    }

    override fun initProcess() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun initObservers() {
        addStoryViewModel.addStoryResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is ApiResponse.Loading -> loadingButton.root.show()
                    is ApiResponse.Success -> {
                        loadingButton.root.gone()
                        Helper.showSuccessToast(requireActivity(), result.data.message)
                        findNavController().popBackStack()
                    }

                    is ApiResponse.Error -> {
                        loadingButton.root.gone()
                        Helper.showErrorToast(requireActivity(), result.errorMessage)
                    }

                    else -> loadingButton.root.gone()
                }
            }
        }
    }

    private fun launchUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this)
        }
    }

    private fun setImagePreview() {
        currentImageUri?.let {
            binding.ivPreviewStory.setImageURI(it)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}
