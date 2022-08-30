package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.RegisterOwnerViewModel
import com.example.hotelappmobile.databinding.FragmentRegisterOwnerBinding

class RegisterOwnerFragment : Fragment() {
    lateinit var binding: FragmentRegisterOwnerBinding
    lateinit var viewModel: RegisterOwnerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterOwnerBinding.inflate(inflater, container, false)
        val view: View = binding.root

        //val stringArray: List<String> = listOf("Sunshine", "Test One", "Godsmack")
        //val adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stringArray)
        viewModel = ViewModelProvider(this).get(RegisterOwnerViewModel::class.java)

        viewModel.getHotels()
        viewModel.hotelNames.observe(viewLifecycleOwner, Observer { hotels->
            if (hotels.isEmpty()){
                binding.tvErrorMessage.isVisible = true
            }else {
                //val adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hotels)
                binding.sHotelsDropDown.adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hotels)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error->
            binding.tvErrorMessage.isVisible = true
            binding.tvErrorMessage.text = error.toString()
        })

        return view
    }
}