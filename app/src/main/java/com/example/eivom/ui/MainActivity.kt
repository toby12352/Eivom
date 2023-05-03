package com.example.eivom.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.eivom.R
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.eivom.data.MovieList
import com.google.android.material.navigation.NavigationView


const val MOVIEDATABASE_APPID = "9b548beeca2515183884381852406153"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val movieInfoAdapter = MovieInfoAdapter(::onMoviePosterClick)

    private lateinit var appBarConfig : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        )as NavHostFragment

        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)
    }

    private fun onMoviePosterClick(detail: MovieList) {
        Log.d(TAG, "Movie poster clicked")
    }
    override fun onSupportNavigateUp(): Boolean{
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}