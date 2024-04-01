package com.neighborly.neighborlyandroid.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.lifecycle.lifecycleScope
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.login.ui.LoginActivity
import com.neighborly.neighborlyandroid.market.MarketActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels{MainViewModel.Factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state){
                    is MainViewModel.UiState.LaunchLogin -> launchLoginActivity()
                    is MainViewModel.UiState.LaunchMarket -> launchMarketActivity()
                    is MainViewModel.UiState.Loading -> showLoading()
                }
            }
        }
        }

    private fun showLoading() {
        Log.d("testLog","Loading")
    }

    private fun launchMarketActivity() {
        val intent = Intent(this, MarketActivity::class.java)
        startActivity(intent)
        finish() // Destroy MainActivity

    }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Destroy MainActivity
    }

}