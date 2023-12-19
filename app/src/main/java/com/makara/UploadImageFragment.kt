package com.makara

import UploadImageViewModel
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.makara.data.MakaraRepository
import com.makara.databinding.FragmentUploadImageBinding
import com.makara.di.Injection
import java.util.UUID


class UploadImageFragment : Fragment() {
    private lateinit var binding: FragmentUploadImageBinding
    private val viewModel: UploadImageViewModel by viewModels {
        ViewModelFactory(Injection.provideRepository(requireContext()), Injection.provideMakaraPreference(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            imagePath?.let {
                viewModel.uploadImageAndPredict(it.toUri())
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uploadResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is MakaraRepository.PredictionResult.Success -> {
                    // TODO: Handle successful prediction, maybe navigate or update UI
                    Toast.makeText(context, result.foodName, Toast.LENGTH_SHORT).show()
                }
                is MakaraRepository.PredictionResult.Error -> {
                    // TODO: Handle error, show a message to the user
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
