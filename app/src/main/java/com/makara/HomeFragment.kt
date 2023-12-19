package com.makara

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.makara.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val dummyData = listOf(
        Food("Sate Padang", "West Sumatra" , "Sate Padang traces its roots back to the culinary traditions of the Minangkabau people in West Sumatra, Indonesia. Prepared with the distinctive flavors of Padang, this satay has become a legendary culinary heritage.\n"+
                "\n"+
                "Main Ingredients: Small-cut beef skewered on bamboo sticks, served with a richly flavored Padang sauce.\n", R.drawable.satepadang),

        Food("Molen", "Indonesia","Molen, although originating from European spring rolls (lumpia), has become a highly popular snack in Indonesia. Over time, molen has been adapted into various flavor variations.\n"+
                "\n"+
                "Main Ingredients: Thin spring roll pastry filled with various options such as banana, chocolate, cheese, or coconut, then fried to a golden brown.\n", R.drawable.molen),

        Food("Nasi Goreng", "Indonesia","Nasi Goreng originated from the tradition of turning leftover rice into a delicious dish. It may have been inspired by the Javanese practice of avoiding food waste.\n"+
                "\n"+
                "Main Ingredients: Fried rice mixed with seasonings like sweet soy sauce, onions, eggs, and can be enhanced with various additional ingredients according to personal taste.\n", R.drawable.nasgor),

        Food("Kerak Telor", "Betawi, Jakarta","Kerak Telor hails from Betawi, Jakarta, and has a long history as a traditional dish served during special events.\n"+
                "\n"+
                "Main Ingredients: A mixture of glutinous rice, chicken eggs, grated coconut, dried shrimp (ebi), and Betawi's distinctive spices.\n", R.drawable.keraktelor),

        Food("Papeda", "Papua","Papeda is a traditional dish of the Papua people in Indonesia. Sago, abundant in Papua, is processed into a distinctive main dish.\n"+
                "\n"+
                "Main Ingredients: Sago boiled to a thick consistency, served with fish in a richly spiced broth.\n", R.drawable.papeda),

        Food("Bika Ambon", "Medan","Bika Ambon originates from Medan, Indonesia, and has become a well-known souvenir from the region.\n"+
                "\n"+
                "Main Ingredients: A mixture of wheat flour, eggs, coconut milk, and pandan leaves to impart a unique flavor and aroma.\n", R.drawable.bikaambon),

        Food("Seblak", "Bandung","Seblak initially evolved from the traditional Betawi soup called soto. Today, Seblak has developed its own culinary identity and is highly popular in Bandung.\n"+
                "\n"+
                "Main Ingredients: Crackers, tofu, noodles, meatballs, and Seblak's distinctive seasonings, often cooked with a high level of spiciness according to individual preferences.\n", R.drawable.seblak),
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodAdapter{
            val gotoDetailFoodFragment = HomeFragmentDirections.actionHomeFragmentToDetailFoodFragment()
            gotoDetailFoodFragment.food = it

            findNavController().navigate(gotoDetailFoodFragment)
        }
        binding.rvFood.adapter = adapter
        binding.rvFood.layoutManager = LinearLayoutManager(context)
        adapter.setFoods(dummyData)


    }
}
