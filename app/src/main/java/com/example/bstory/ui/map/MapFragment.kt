package com.example.bstory.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bstory.R
import com.example.bstory.base.FragmentBase
import com.example.bstory.config.Helper
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class MapFragment : FragmentBase<FragmentMapBinding>(), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by inject()
    private val boundsBuilder = LatLngBounds.Builder()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        mapViewModel.getAllStoriesWithLocation()
        val mapFragment =
            childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun initObservers() {

    }

    override fun onMapReady(gMap: GoogleMap) {
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isIndoorLevelPickerEnabled = true
        gMap.uiSettings.isCompassEnabled = true
        gMap.uiSettings.isMapToolbarEnabled = true
        setMapStyle(gMap)

        mapViewModel.storyResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResponse.Loading -> Helper.showSuccessToast(requireActivity(), getString(R.string.loading))

                is ApiResponse.Success -> {
                    result.data.listStory.forEach { story ->
                        val latLng = LatLng(story.lat, story.lon)
                        gMap.addMarker(MarkerOptions().position(latLng).title(story.name))
                        boundsBuilder.include(latLng)
                    }

                    val bounds: LatLngBounds = boundsBuilder.build()
                    gMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                }

                is ApiResponse.Error -> Helper.showErrorToast(requireActivity(), result.errorMessage)

                else -> {}
            }

        }

    }

    private fun setMapStyle(gMap: GoogleMap) {
        try {
            val success =
                gMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireActivity(),
                        R.raw.map_style
                    )
                )
            if (!success) {
                Helper.showErrorToast(requireActivity(), "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Helper.showErrorToast(requireActivity(), "Can't find style. Error: $exception")
        }
    }

}