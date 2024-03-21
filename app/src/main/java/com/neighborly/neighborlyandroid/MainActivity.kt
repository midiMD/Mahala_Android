package com.neighborly.neighborlyandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.neighborly.neighborlyandroid.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        binding.loginButton.setOnClickListener(loginButtonListener)

        val authObserver = Observer<Boolean> { isAuth ->
            // Update the UI, in this case, a TextView.
            if(isAuth) {
                Log.d("MainActivity", "user is authorized")

            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.userIsAuthorized.observe(this, authObserver)

    }
}