package com.makara.ui.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.makara.MainActivity
import com.makara.ViewModelFactory
import com.makara.data.local.pref.MakaraModel
import com.makara.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playingAnimation()
        observeLoginResponse()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun observeLoginResponse() {
        viewModel.loginResponse.observe(this@AuthActivity) { response ->
            if (!response.error) {
                // Trigger token retrieval
                viewModel.getSavedToken()
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.tokenRetrieved.observe(this@AuthActivity) { token ->
            if (token != null) {
                // Now we have the token, we can save the session
                val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
                saveSession(
                    MakaraModel(
                        userEmail,
                        "Bearer $token", // Here the "Bearer " prefix is added
                        true
                    )
                )
                // Navigate to MainActivity
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                finish()
            } else {
                // Handle the case where the token retrieval failed
                Toast.makeText(this, "Failed to retrieve authentication token.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun setupAction() {
        binding.apply {
            binding.signupButton.setOnClickListener {
                if (nameEditText.length() == 0 && emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    nameEditText.error = "This field is required!"
                    emailEditText.error = "This field is required!"
                    passwordEditText.setError("This field is required!", null)
                } else if (nameEditText.length() != 0 && emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    showLoading()
                    binding.apply {
                        viewModel.postSignup(
                            nameEditText.text.toString(),
                            emailEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    }
                    showToast()
                    viewModel.registerResponse.observe(this@AuthActivity) { response ->
                        if (!response.error!!) {
                            startActivity(Intent(this@AuthActivity, AuthActivity::class.java))
                            finish()
                        }
                    }
                }
            }

            binding.loginButton.setOnClickListener {
                Log.i("LoginListener", "Login button clicked")
                if (emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    emailEditText.error = "This field is required!"
                    passwordEditText.error = "This field is required!"
                } else if (emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    Log.i("LoginListener", "Logging in")
                    showLoading()
                    viewModel.postLogin(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                    Log.i("LoginListener", "Post login done")
                    viewModel.loginResponse.observe(this@AuthActivity) { response ->
                        if (!response.error) {
                            Log.i("LoginListener", "Login response is not error")
                            // Now we need to retrieve the token from the saved location
                            viewModel.getSavedToken()
                            Log.i("LoginListener", "Get saved token ${viewModel.getSavedToken()}")
                        }
                    }
                }
                showToast()
            }

        }
    }

    private fun playingAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this@AuthActivity) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        viewModel.toastText.observe(this@AuthActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSession(session: MakaraModel) {
        viewModel.saveSession(session)
    }

    companion object {
        private const val AUTH_KEY = "Bearer "
    }
}