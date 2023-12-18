package com.makara.ui.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.makara.MainActivity
import com.makara.R
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
                if (emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    emailEditText.error = "This field is required!"
                    passwordEditText.setError("This field is required!", null)
                } else if (emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    showLoading()
                    binding.apply {
                        viewModel.postLogin(
                            emailEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                        viewModel.login()
                        viewModel.loginResponse.observe(this@AuthActivity) { response ->
                            if (!response.error) {
                                saveSession(
                                    MakaraModel(
                                        response.loginResult?.email.toString(),
                                        AUTH_KEY + (response.loginResult?.token.toString()),
                                        true
                                    )
                                )
                                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                    showToast()
                }
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