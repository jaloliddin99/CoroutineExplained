package com.jaloliddinabdullaev.coroutineexplained.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jaloliddinabdullaev.coroutineexplained.databinding.MainFragmentBinding
import com.jaloliddinabdullaev.coroutineexplained.ui.adapter.AnimalsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainFragment:Fragment(),AnimalsAdapter.OnImageCLickListener {

    private var _binding: MainFragmentBinding?=null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterMeme= AnimalsAdapter(this)

        binding.apply {
            recyclerView.apply {
                layoutManager= GridLayoutManager(requireContext(), 4)
                adapter= adapterMeme
                try {
                    viewModel.imageList.observe(viewLifecycleOwner) {
                        adapterMeme.submitList(it.data)
                    }
                }catch (e: Exception){
                }
            }
            viewModel.imageList.observe(viewLifecycleOwner){

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onImageClick(imageUrl: String, imageId: String, imageView: AppCompatImageView) {

    }


}