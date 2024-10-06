package com.neighborly.neighborlyandroid.login.ui

import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.android.material.snackbar.Snackbar
import com.neighborly.neighborlyandroid.databinding.ActivityLoginBinding

import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.market.ui.MarketActivity
import com.neighborly.neighborlyandroid.registration.ui.RegisterActivity

//TO DO:
// Implement Forgot Password
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels {LoginViewModel.Factory}
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val loginButtonListener = View.OnClickListener {
            if(binding.emailText.text.isNullOrEmpty() || binding.passwordText.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Please fill in both email and password", Snackbar.LENGTH_SHORT).show()
            }else{
                viewModel.onLoginButtonPress(binding.emailText.text.toString(),binding.passwordText.text.toString())
            }

        }
        val registerButtonListener = View.OnClickListener {
            viewModel.onRegisterButtonPress()
        }
        binding.registerButton.setOnClickListener(registerButtonListener)
        binding.loginButton.setOnClickListener(loginButtonListener)

        val loginStateObserver = Observer<LoginViewModel.LoginState> { state ->
            // Update the UI, in this case, a TextView.
            if(state.loading) {
                Log.d("MainActivity", "log in state is loading")
                binding.loginFormTable.visibility=View.GONE
                binding.progressBar.visibility=View.VISIBLE
            }else{
                Log.d("MainActivity", "log in state is not loading ")
                binding.loginFormTable.visibility=View.VISIBLE
                binding.progressBar.visibility=View.GONE

            }
            if(state.authorized) {
                launchMarketActivity()
                //move to a different activity
            }
            else if(state.credentialsDeclined) {
                Snackbar.make(binding.root, "Wrong Email or password. try again", Snackbar.LENGTH_SHORT).show()
            }
            else if (!state.error.isNullOrEmpty()){
                Log.d("MainActivity", state.error)

            }
            if(state.goToRegister){
                launchRegisterActivity()
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.loginState.observe(this, loginStateObserver)

    }
    private fun launchMarketActivity() {
        val intent = Intent(this, MarketActivity::class.java)
        startActivity(intent)
        finish() // Destroy LoginActivity

    }
    private fun launchRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish() // Destroy LoginActivity

    }
}