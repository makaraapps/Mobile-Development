package com.makara

import UploadImageViewModel
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.makara.data.MakaraRepository
import com.makara.databinding.FragmentUploadImageBinding
import com.makara.di.Injection
import com.makara.ui.result.ResultActivity


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

        binding.ivBackArrow.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.btnUpload.setOnClickListener {
            showLoading()
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
                    activity?.let {
                        val intent = Intent (it, ResultActivity::class.java)
                        it.startActivity(intent)
                    }
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

    private fun showLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
