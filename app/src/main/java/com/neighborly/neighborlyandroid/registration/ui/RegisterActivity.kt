package com.neighborly.neighborlyandroid.registration.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.databinding.ActivityRegisterBinding
import com.neighborly.neighborlyandroid.login.ui.LoginActivity
import com.neighborly.neighborlyandroid.login.ui.LoginViewModel
import com.neighborly.neighborlyandroid.market.MarketActivity

//TODO
// Make UI more ergonomic


class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userRegister)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val registerButtonListener = View.OnClickListener {
            if(binding.passwordField.text.isNullOrEmpty() || binding.fullNameTextField.text.isNullOrEmpty()|| binding.confirmPasswordField.text.isNullOrEmpty()|| binding.emailTextField.text.isNullOrEmpty()){
                Snackbar.make(binding.root, "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
            }else if(binding.passwordField.text.toString() !=binding.confirmPasswordField.text.toString() ){
                Snackbar.make(binding.root, "Passwords don't match", Snackbar.LENGTH_SHORT).show()
            }else{

                viewModel.onRegisterButtonPress(binding.emailTextField.text.toString(),binding.passwordField.text.toString(),binding.fullNameTextField.text.toString())

            }
        }
        binding.registerButton.setOnClickListener(registerButtonListener)

        val registerStateObserver = Observer<RegisterViewModel.RegisterPageState> { state ->
            Log.d("MyLogger",state.toString())
            // Update the UI, in this case, a TextView.
            if(state.loading) {
                Log.d("MyError", "register state is loading")
                binding.progressSpinner.visibility=View.VISIBLE
            }else{
                Log.d("MyError", "register state is not loading ")
                binding.progressSpinner.visibility=View.GONE

            }
            if(state.goToLogin){
                Log.d("MyLogger","Launching Login activity from register page")
                launchLoginActivity()
            }
            if(state.accepted) {
                // display success
                Log.d("MyError","registration succcesful")
                launchMarketActivity()
                //move to a different activity
            }
            else if(state.emailAlreadyExists) {
                Snackbar.make(binding.root, "THis email is already registered", Snackbar.LENGTH_SHORT).show()
            }
            else if (!state.errors.isNullOrEmpty()){
                Snackbar.make(binding.root, "Error connecting to server, try again later", Snackbar.LENGTH_SHORT).show()
                Log.d("MainActivity", state.errors.toString())

            }

        }
        val backButtonListener = View.OnClickListener {
            binding.progressSpinner.visibility=View.VISIBLE
            launchLoginActivity()
        }
        binding.registrationBackButton.setOnClickListener(backButtonListener)

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.registerPageState.observe(this, registerStateObserver)
        }

    private fun launchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Destroy Registration ACtivity
    }
    private fun launchMarketActivity() {
        val intent = Intent(this, MarketActivity::class.java)
        startActivity(intent)
        finish() // Destroy Registration Activity

    }


}