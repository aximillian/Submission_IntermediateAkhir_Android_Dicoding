package com.example.bstory.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bstory.R
import com.example.bstory.base.FragmentBase
import com.example.bstory.config.AppConfig
import com.example.bstory.config.PreferenceConfig
import com.example.bstory.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.time.Duration.Companion.seconds

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SplashFragment : FragmentBase<FragmentSplashBinding>() {

    private val preferenceManager: PreferenceConfig by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        lifecycleScope.launch {
            delay(AppConfig.SPLASH_SCREEN_DURATION.seconds)
            if (preferenceManager.getToken.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_home)

            }
        }
    }

    override fun initObservers() {
    }

}