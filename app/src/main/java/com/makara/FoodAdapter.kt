package com.makara

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makara.databinding.ItemFoodBinding

class FoodAdapter(private val callback: (Food) -> Unit): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>()  {
    private val listFood = ArrayList<Food>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemFoodBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemFoodBinding)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
       holder.bind(listFood[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFoods(foods: List<Food>?){
        if(foods.isNullOrEmpty()) return
        this.listFood.clear()
        this.listFood.addAll(foods)
        notifyDataSetChanged()
    }


    inner class FoodViewHolder(private val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food){
            binding.apply {
                ivFood.setImageResource(food.photo)
                tvTitleFood.text = food.name
                tvDescriptionFood.text = food.description

                root.setOnClickListener {
                    callback(food)
                }
            }
        }
    }


}