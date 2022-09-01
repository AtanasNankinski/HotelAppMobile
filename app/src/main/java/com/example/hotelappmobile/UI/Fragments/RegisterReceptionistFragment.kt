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
import com.example.hotelappmobile.Models.ReceptionistCall
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.ViewModels.RegisterReceptionistViewModel
import com.example.hotelappmobile.databinding.FragmentRegisterOwnerBinding
import com.example.hotelappmobile.databinding.FragmentRegisterReceptionistBinding

class RegisterReceptionistFragment : Fragment() {
    lateinit var binding: FragmentRegisterReceptionistBinding
    lateinit var viewModel: RegisterReceptionistViewModel
    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterReceptionistBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterReceptionistViewModel::class.java)
        val view: View = binding.root

        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, Observer { message->
                binding.tvErrorMessage.text = message
            })

            isCallSuccessfull.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Receptionist successfully added!", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnAddReceptionist.setOnClickListener {
            val name: String = binding.etReceptionistName.text.toString()
            val email: String = binding.etReceptionistEmail.text.toString()
            val password: String = binding.etReceptionistPassword.text.toString()

            if(viewModel.validateFields(name, email, password)){
                val body: ReceptionistCall = ReceptionistCall(name, email, password, mainViewModel.user.value!!.id.toString())
                viewModel.registerReceptionist(body, mainViewModel.user.value!!.token)
                clearFields()
            }
        }

        return view
    }

    private fun clearFields(){
        binding.etReceptionistName.text.clear()
        binding.etReceptionistEmail.text.clear()
        binding.etReceptionistPassword.text.clear()
    }
}