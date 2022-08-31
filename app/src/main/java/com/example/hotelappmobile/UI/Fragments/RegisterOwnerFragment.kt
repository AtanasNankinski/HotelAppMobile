package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.ViewModels.RegisterOwnerViewModel
import com.example.hotelappmobile.databinding.FragmentRegisterOwnerBinding

class RegisterOwnerFragment : Fragment() {
    private lateinit var binding: FragmentRegisterOwnerBinding
    private lateinit var viewModel: RegisterOwnerViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterOwnerBinding.inflate(inflater, container, false)
        val view: View = binding.root

        //val stringArray: List<String> = listOf("Sunshine", "Test One", "Godsmack")
        //val adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stringArray)
        viewModel = ViewModelProvider(this).get(RegisterOwnerViewModel::class.java)
        mainViewModel.user.observe(viewLifecycleOwner, Observer { user->
            if (user != null){
                viewModel.getHotels(mainViewModel)
            }
        })

        viewModel.hotelNames.observe(viewLifecycleOwner, Observer { hotels->
            if (hotels.isEmpty()){
                binding.tvErrorMessage.isVisible = true
            }else {
                //val adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hotels)
                binding.sHotelsDropDown.adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hotels)
            }
        })

        return view
    }
}