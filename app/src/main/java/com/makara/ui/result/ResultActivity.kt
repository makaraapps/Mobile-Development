package com.makara.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.makara.R
import com.makara.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.ivBackArrow.setOnClickListener{
            onBackPressed()
        }
    }
}