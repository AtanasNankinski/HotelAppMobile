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
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.HotelUser
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.ViewModels.RegisterManagerViewModel
import com.example.hotelappmobile.databinding.FragmentRegisterManagerBinding

class RegisterManagerFragment : Fragment() {
    lateinit var binding: FragmentRegisterManagerBinding
    lateinit var viewModel: RegisterManagerViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterManagerBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterManagerViewModel::class.java)
        val view: View = binding.root

        mainViewModel.user.observe(viewLifecycleOwner, Observer { user->
            if (user != null){
                viewModel.getHotelsByOwner(user.token, user.id)
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
                    Toast.makeText(view.context, "Manager successfully added!", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnAddManager.setOnClickListener {
            val name: String = binding.etManagerName.text.toString()
            val email: String = binding.etManagerEmail.text.toString()
            val password: String = binding.etManagerPassword.text.toString()
            val hotel_name: String = binding.sHotelsDropDown.selectedItem.toString()

            if(viewModel.validateFields(name, email, password, hotel_name)){
                val body: HotelUser = HotelUser(name, email, password, 3, hotel_name)
                viewModel.registerManager(body, mainViewModel.user.value!!.token)
            }
        }

        return view
    }
}