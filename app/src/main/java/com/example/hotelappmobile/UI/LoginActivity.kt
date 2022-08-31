package com.example.hotelappmobile.UI

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.LoginBody
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
                val body = LoginBody(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                viewModel.login(body)
            }
        }

        viewModel.errorMessage.observe(this, Observer { errorMessage->
            binding.tvErrorMessage.text = errorMessage
            binding.tvErrorMessage.isVisible = true
        })

        viewModel.fullUser.observe(this, Observer { user->
            if (user != null){
                intent.putExtra("id", user.id)
                intent.putExtra("user_name", user.userName)
                intent.putExtra("email", user.email)
                intent.putExtra("user_type", user.userType)
                intent.putExtra("token", user.token)
                startActivity(intent)
            }
        })
    }
}