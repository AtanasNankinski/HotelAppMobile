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
import com.example.hotelappmobile.Models.Client
import com.example.hotelappmobile.Models.ClientRequest
import com.example.hotelappmobile.ViewModels.CreateClientViewModel
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.databinding.FragmentCreateClientBinding

class CreateClientFragment : Fragment() {
    private lateinit var binding: FragmentCreateClientBinding
    private lateinit var viewModel: CreateClientViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateClientBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateClientViewModel::class.java)
        val view: View = binding.root

        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, Observer {
                binding.tvErrorMessage.text = it
            })

            isSuccessful.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Client created!", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.btnAddManager.setOnClickListener {
            val firstName: String = binding.etClientFirstName.text.toString()
            val lastName: String = binding.etClientLastName.text.toString()
            val email: String = binding.etClientEmail.text.toString()

            if(viewModel.validateFields(firstName, lastName, email)){
                val client: ClientRequest = ClientRequest(firstName, lastName, email, mainViewModel.user.value!!.id)
                viewModel.createClient(mainViewModel.user.value!!.token, client)
                clearFields()
            }
        }

        return view
    }

    private fun clearFields(){
        binding.etClientFirstName.text.clear()
        binding.etClientLastName.text.clear()
        binding.etClientEmail.text.clear()
    }
}