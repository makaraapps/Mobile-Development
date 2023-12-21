package com.makara.ui.result

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.makara.DetailFoodFragmentArgs
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
        val learnMoreResult = binding.tvLearnMore

        if (intent.hasExtra("FOOD_NAME")) {
            val foodName = intent.getStringExtra("FOOD_NAME")
            titleResult.text = foodName

            val selectedFood = FoodData.foods.find { it.name.equals(foodName, ignoreCase = true) }

            selectedFood?.let {food ->
                titleResult.text = food.name
                imageResult.setImageResource(food.photo)
                descriptionResult.text = food.description
                locationResult.text = food.from
                learnMoreResult.setOnClickListener {
                    openLearnMoreLink(food.link)
                }
            }
        }
    }
    private fun openLearnMoreLink(learnMoreLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(learnMoreLink))
        startActivity(intent)
    }
}