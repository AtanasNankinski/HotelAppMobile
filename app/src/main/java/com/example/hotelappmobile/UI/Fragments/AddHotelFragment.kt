package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.AddHotelViewModel
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.databinding.FragmentAddHotelBinding
import com.example.hotelappmobile.databinding.FragmentRegisterOwnerBinding

class AddHotelFragment : Fragment() {
    private lateinit var binding: FragmentAddHotelBinding
    private lateinit var viewModel: AddHotelViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHotelBinding.inflate(inflater, container, false)
        val view: View = binding.root
        viewModel = ViewModelProvider(this).get(AddHotelViewModel::class.java)

        binding.btnAddHotel.setOnClickListener {
            viewModel.apply {
                if (isFieldValid(binding.etAddHotelField.text.toString())){
                    addHotel(mainViewModel.user.value!!.token, binding.etAddHotelField.text.toString())
                    binding.etAddHotelField.text.clear()
                }
            }
        }

        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, Observer { message->
                binding.tvAddHotelError.text = message
            })

            isSuccessful.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Hotel added successfully!", Toast.LENGTH_LONG).show()
                }
            })
        }

        return view
    }
}