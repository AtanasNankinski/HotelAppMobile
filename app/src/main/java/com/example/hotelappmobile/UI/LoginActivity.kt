package com.example.hotelappmobile.UI

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.LoginViewModel
import com.example.hotelappmobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding and view declaration
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // View model and intent declaration
        val intent = Intent(this, MainActivity::class.java)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // Values declaration
        var emailString = ""
        var passwordString = ""

        binding.btnLogin.setOnClickListener {
            emailString = binding.etEmail.text.toString()
            passwordString = binding.etPassword.text.toString()

            if (viewModel.checkCredentials(emailString, passwordString)){
                binding.tvErrorMessage.isVisible = false
                binding.tvErrorMessage.text = viewModel.errorMessage
                startActivity(intent)
            }else {
                binding.tvErrorMessage.text = viewModel.errorMessage
                binding.tvErrorMessage.isVisible = true
            }
        }
    }
}