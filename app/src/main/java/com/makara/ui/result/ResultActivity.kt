package com.makara.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.makara.R
import com.makara.data.local.FoodData
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

        val imageResult = binding.ivFood
        val titleResult = binding.tvTitleFood
        val descriptionResult = binding.tvDescriptionFood
        val locationResult = binding.tvFromFood

        if (intent.hasExtra("FOOD_NAME")) {
            val foodName = intent.getStringExtra("FOOD_NAME")
            titleResult.text = foodName

            val selectedFood = FoodData.foods.find { it.name.equals(foodName, ignoreCase = true) }

            selectedFood?.let {
                titleResult.text = it.name
                imageResult.setImageResource(it.photo)
                descriptionResult.text = it.description
                locationResult.text = it.from
            }
        }
    }
}