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
        Food("Klepon", "Java" , "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eu non diam phasellus vestibulum lorem sed risus. Sed arcu non odio euismod lacinia at quis. Id donec ultrices tincidunt arcu non sodales neque sodales ut. Commodo ullamcorper a lacus vestibulum sed arcu. Pretium viverra suspendisse potenti nullam ac. Eu sem integer vitae justo eget magna fermentum. Tellus orci ac auctor augue mauris. Diam sollicitudin tempor id eu nisl nunc mi ipsum faucibus. Fames ac turpis egestas integer eget aliquet nibh. Dis parturient montes nascetur ridiculus. Orci eu lobortis elementum nibh. Bibendum arcu vitae elementum curabitur vitae nunc sed velit. Fringilla phasellus faucibus scelerisque eleifend. Potenti nullam ac tortor vitae purus faucibus ornare. Sit amet porttitor eget dolor morbi non arcu risus. Porttitor lacus luctus accumsan tortor posuere. Morbi tristique senectus et netus et malesuada. Et netus et malesuada fames ac turpis egestas. Arcu bibendum at varius vel. Montes nascetur ridiculus mus mauris vitae ultricies leo. Ullamcorper sit amet risus nullam eget felis eget. Pharetra diam sit amet nisl suscipit adipiscing bibendum est. Pharetra et ultrices neque ornare aenean euismod. Penatibus et magnis dis parturient montes nascetur ridiculus mus. Eu ultrices vitae auctor eu augue ut. Tristique senectus et netus et malesuada fames. Sit amet aliquam id diam maecenas. Ac feugiat sed lectus vestibulum mattis ullamcorper velit. Lectus quam id leo in vitae turpis massa. Lectus magna fringilla urna porttitor rhoncus dolor purus. Risus pretium quam vulputate dignissim suspendisse in. Eget sit amet tellus cras adipiscing enim eu. Accumsan tortor posuere ac ut. Arcu dui vivamus arcu felis. Pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet. At imperdiet dui accumsan sit amet nulla. Erat pellentesque adipiscing commodo elit at. Sed enim ut sem viverra aliquet. Dolor sit amet consectetur adipiscing. Enim nec dui nunc mattis enim ut tellus elementum sagittis. Netus et malesuada fames ac turpis egestas. Aliquam id diam maecenas ultricies. Ipsum a arcu cursus vitae congue mauris rhoncus. Vel orci porta non pulvinar neque laoreet. Ullamcorper malesuada proin libero nunc. Eros donec ac odio tempor orci dapibus ultrices in. Blandit massa enim nec dui nunc mattis. Mattis aliquam faucibus purus in massa tempor nec feugiat nisl. Consectetur lorem donec massa sapien faucibus et molestie. In aliquam sem fringilla ut morbi. Montes nascetur ridiculus mus mauris vitae ultricies leo integer. Orci nulla pellentesque dignissim enim sit amet venenatis. Vitae suscipit tellus mauris a diam maecenas sed. Velit egestas dui id ornare arcu. Egestas congue quisque egestas diam. Dui accumsan sit amet nulla facilisi. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper. Nam at lectus urna duis. At augue eget arcu dictum. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras. Interdum consectetur libero id faucibus nisl tincidunt eget nullam. Quam adipiscing vitae proin sagittis nisl rhoncus mattis. Pulvinar etiam non quam lacus. Bibendum enim facilisis gravida neque convallis a cras. Elementum nisi quis eleifend quam adipiscing vitae proin sagittis. Mauris sit amet massa vitae tortor condimentum lacinia. Potenti nullam ac tortor vitae purus faucibus ornare. Sit amet dictum sit amet justo donec enim. Interdum velit laoreet id donec ultrices tincidunt arcu. Massa massa ultricies mi quis hendrerit. Mollis aliquam ut porttitor leo a diam. Et pharetra pharetra massa massa. Vehicula ipsum a arcu cursus vitae. Netus et malesuada fames ac. Vitae proin sagittis nisl rhoncus mattis. Leo in vitae turpis massa sed. Feugiat in fermentum posuere urna nec tincidunt. Sed vulputate mi sit amet mauris commodo. Adipiscing enim eu turpis egestas. Lorem donec massa sapien faucibus et. Sapien nec sagittis aliquam malesuada bibendum arcu. Sed risus ultricies tristique nulla. Eget sit amet tellus cras adipiscing enim eu turpis. Fermentum leo vel orci porta. Morbi enim nunc faucibus a pellentesque sit. Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit. Dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh. Amet mauris commodo quis imperdiet. Sit amet nisl suscipit adipiscing bibendum est ultricies integer. Eget gravida cum sociis natoque penatibus et magnis dis. Purus ut faucibus pulvinar elementum integer. Ut lectus arcu bibendum at varius vel pharetra vel. Donec ac odio tempor orci dapibus. Eu turpis egestas pretium aenean. Eget gravida cum sociis natoque penatibus et. Scelerisque eu ultrices vitae auctor eu augue ut lectus. Pharetra diam sit amet nisl suscipit adipiscing. Posuere urna nec tincidunt praesent semper. Neque convallis a cras semper auctor neque vitae.", R.drawable.klepon),
        Food("Gudeg", "Java","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. A arcu cursus vitae congue mauris rhoncus aenean vel. Mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing. Purus in mollis nunc sed id semper risus in. Neque aliquam vestibulum morbi blandit cursus risus at ultrices. Orci a scelerisque purus semper eget duis at. Orci nulla pellentesque dignissim enim. Bibendum at varius vel pharetra vel. Tincidunt lobortis feugiat vivamus at augue eget. Ornare suspendisse sed nisi lacus. Scelerisque eleifend donec pretium vulputate. Et malesuada fames ac turpis egestas. Nisi est sit amet facilisis magna etiam tempor orci eu. Eu feugiat pretium nibh ipsum consequat nisl vel. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Leo urna molestie at elementum eu facilisis sed odio. Mollis aliquam ut porttitor leo a diam sollicitudin tempor id. Nibh venenatis cras sed felis eget velit aliquet sagittis id. In tellus integer feugiat scelerisque varius morbi. Tempor id eu nisl nunc mi ipsum faucibus vitae. Tortor aliquam nulla facilisi cras fermentum odio eu. Lobortis mattis aliquam faucibus purus in massa tempor nec feugiat. Tristique senectus et netus et malesuada fames ac turpis. Purus viverra accumsan in nisl nisi scelerisque. Morbi tristique senectus et netus. Viverra mauris in aliquam sem fringilla. Quisque id diam vel quam elementum pulvinar etiam. Duis at tellus at urna condimentum mattis pellentesque. Quis enim lobortis scelerisque fermentum. Varius duis at consectetur lorem donec massa sapien.", R.drawable.gudeg),
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