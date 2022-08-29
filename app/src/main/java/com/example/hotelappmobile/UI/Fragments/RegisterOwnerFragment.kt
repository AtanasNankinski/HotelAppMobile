package com.example.hotelappmobile.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hotelappmobile.databinding.FragmentRegisterOwnerBinding

class RegisterOwnerFragment : Fragment() {
    lateinit var binding: FragmentRegisterOwnerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterOwnerBinding.inflate(inflater, container, false)
        val view: View = binding.root

        binding.btnLogin.setOnClickListener {

        }

        return view
    }
}