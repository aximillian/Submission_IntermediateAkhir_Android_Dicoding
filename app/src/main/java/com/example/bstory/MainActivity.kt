package com.example.bstory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bstory.config.AppConfig
import com.example.bstory.config.ext.gone
import com.example.bstory.config.ext.show
import com.example.bstory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHostBottomBar =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        with(binding) {
            navView.setupWithNavController(navControllerBottomBar)
            navControllerBottomBar.addOnDestinationChangedListener { _, destination, _ ->
                if (AppConfig.navBarDestination.contains(destination.id)) {
                    navView.show()
                } else {
                    navView.gone()
                }
            }
        }
    }
}