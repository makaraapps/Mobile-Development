package com.makara

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.makara.databinding.FragmentUploadImageBinding


class UploadImageFragment : Fragment() {
    private lateinit var binding: FragmentUploadImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUploadImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagePath = UploadImageFragmentArgs.fromBundle(arguments as Bundle).imagePath

        if (imagePath != null) {
            binding.ivFood.setImageURI(imagePath.toUri())
        }

        binding.btnUpload.setOnClickListener {
            val gotoDetailFragment = UploadImageFragmentDirections.actionUploadImageFragmentToDetailFoodFragment()

            findNavController().navigate(gotoDetailFragment)
        }
    }
}