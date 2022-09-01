package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.HotelUser
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

        viewModel = ViewModelProvider(this).get(RegisterOwnerViewModel::class.java)
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

            isCallSuccessfull.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Owner successfully added!", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnAddOwner.setOnClickListener {
            val name: String = binding.etOwnerName.text.toString()
            val email: String = binding.etOwnerEmail.text.toString()
            val password: String = binding.etOwnerPassword.text.toString()
            val hotel_name: String = binding.sHotelsDropDown.selectedItem.toString()

            if (viewModel.validateFields(name, email, password, hotel_name)){
                val body: HotelUser = HotelUser(name, email, password, 2, hotel_name)
                viewModel.registerOwner(body, mainViewModel.user.value!!.token)
                clearFields()
            }
        }

        return view
    }

    private fun clearFields(){
        binding.etOwnerName.text.clear()
        binding.etOwnerEmail.text.clear()
        binding.etOwnerPassword.text.clear()
    }
}