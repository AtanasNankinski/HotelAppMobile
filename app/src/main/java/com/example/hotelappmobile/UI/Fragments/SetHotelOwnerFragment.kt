package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.HotelOwner
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.ViewModels.SetHotelOwnerViewModel
import com.example.hotelappmobile.databinding.FragmentSetHotelOwnerBinding

class SetHotelOwnerFragment : Fragment() {
    lateinit var viewModel: SetHotelOwnerViewModel
    lateinit var binding: FragmentSetHotelOwnerBinding
    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetHotelOwnerBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SetHotelOwnerViewModel::class.java)
        val view: View = binding.root

        mainViewModel.user.observe(viewLifecycleOwner, Observer { user->
            if (user != null){
                viewModel.getHotels(mainViewModel)
            }
        })

        viewModel.apply {
            hotelNames.observe(viewLifecycleOwner, Observer { hotels->
                if (hotels.isNotEmpty()){
                    hotels.add(0, "Choose Hotel")
                    binding.sHotelsDropDown.adapter = ArrayAdapter(requireActivity().applicationContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hotels)
                }
            })

            errorMessage.observe(viewLifecycleOwner, Observer { message->
                binding.tvErrorMessage.text = message
            })

            isSuccessful.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Owner Set Successfully!", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnSetHotelOwner.setOnClickListener {
            val email: String = binding.etOwnerInput.text.toString()
            val hotelName: String = binding.sHotelsDropDown.selectedItem.toString()

            if (viewModel.validateFields(email, hotelName)){
                val owner: HotelOwner = HotelOwner(email, hotelName)
                viewModel.setHotelOwner(mainViewModel.user.value!!.token, owner)
            }
        }

        return view
    }
}