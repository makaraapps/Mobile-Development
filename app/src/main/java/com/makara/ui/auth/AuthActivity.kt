package com.makara.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
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
//        setupError()
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
                    startActivity(Intent(this@AuthActivity, AuthActivity::class.java))
                    finish()
                }
            }

            binding.loginButton.setOnClickListener {
                if (emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    emailEditText.error = "This field is required!"
                    passwordEditText.setError("This field is required!", null)
                } else if (emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    val email = binding.emailEditText.text.toString()
                    viewModel.saveSession(MakaraModel(email, "sample_token"))
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}