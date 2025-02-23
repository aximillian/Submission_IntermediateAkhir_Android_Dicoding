package com.example.bstory.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bstory.R
import com.example.bstory.base.FragmentBase
import com.example.bstory.config.Helper
import com.example.bstory.config.ext.gone
import com.example.bstory.config.ext.show
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject

class LoginFragment : FragmentBase<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        playAnimation()
    }

    override fun initAction() {
        binding.apply {
            loginButton.setOnClickListener {
                val email = edLoginEmail.text?.trim().toString()
                val password = edLoginPassword.text?.trim().toString()

                if (email.isEmpty()) {
                    edLoginEmail.error = getString(R.string.error_empty_field)
                }
                if (password.isEmpty()) {
                    edLoginPassword.error = getString(R.string.error_empty_field)
                }
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.login(email, password)
                }

            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        binding.apply {
            loginViewModel.loginResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ApiResponse.Loading -> loadingButton.root.show()
                    is ApiResponse.Success -> {
                        Helper.showSuccessToast(requireActivity(), result.data.message)
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                    }

                    is ApiResponse.Error -> {
                        loadingButton.root.gone()
                        Helper.showErrorToast(requireActivity(), result.errorMessage)
                    }

                    else -> binding.root.gone()
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val icon = ObjectAnimator.ofFloat(appIcon, View.ALPHA, 1f).setDuration(100)
            val title = ObjectAnimator.ofFloat(loginHeadline, View.ALPHA, 1f).setDuration(100)
            val email = ObjectAnimator.ofFloat(edLoginEmail, View.ALPHA, 1f).setDuration(100)
            val password = ObjectAnimator.ofFloat(edLoginPassword, View.ALPHA, 1f).setDuration(100)
            val login = ObjectAnimator.ofFloat(loginButton, View.ALPHA, 1f).setDuration(100)
            val register = ObjectAnimator.ofFloat(registerButton, View.ALPHA, 1f).setDuration(100)

            val together = AnimatorSet().apply {
                playTogether(login, register)
            }

            AnimatorSet().apply {
                playSequentially(icon, title, email, password, together)
                start()
            }
        }
    }
}