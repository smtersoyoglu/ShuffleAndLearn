package com.smtersoyoglu.shuffleandlearn.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.smtersoyoglu.shuffleandlearn.R
import com.smtersoyoglu.shuffleandlearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        // Handle BottomNavigationView visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // Handle bottom navigation item selections
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    // Navigate to HomeFragment, clearing back stack
                    navController.popBackStack(R.id.homeFragment, false)
                    true
                }
                R.id.learnedWordsFragment -> {
                    // Navigate to LearnedWordsFragment
                    if (navController.currentDestination?.id != R.id.learnedWordsFragment) {
                        navController.navigate(R.id.learnedWordsFragment)
                    }
                    true
                }
                R.id.wordGameFragment -> {
                    // Navigate to WordGameFragment
                    if (navController.currentDestination?.id != R.id.wordGameFragment) {
                        navController.navigate(R.id.wordGameFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }
}
