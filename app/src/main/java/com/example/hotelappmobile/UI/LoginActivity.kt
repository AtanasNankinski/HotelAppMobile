package com.example.hotelappmobile.UI

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.FullUser
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
        val sharedPreference =  getSharedPreferences("USER_PREFERENCE",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        setContentView(view)
        // View model and intent declaration
        val intent = Intent(this, MainActivity::class.java)
        checkIfUserIsLogged(sharedPreference, intent, editor)
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
        })

        viewModel.fullUser.observe(this, Observer { user->
            if (user != null){
                saveUserLocally(editor, user)
                navActivityWithIntent(intent, user)
            }
        })
    }

    private fun saveUserLocally(editor: SharedPreferences.Editor, user: FullUser){
        editor.putInt("id", user.id)
        editor.putString("user_name", user.userName)
        editor.putString("email", user.email)
        editor.putInt("user_type", user.userType)
        editor.putString("token", user.token)
        editor.commit()
    }

    private fun navActivityWithIntent(intent: Intent, user: FullUser){
        intent.putExtra("id", user.id)
        intent.putExtra("user_name", user.userName)
        intent.putExtra("email", user.email)
        intent.putExtra("user_type", user.userType)
        intent.putExtra("token", user.token)

        startActivity(intent)
    }

    private fun checkIfUserIsLogged(sharedPref: SharedPreferences, intent: Intent, editor: SharedPreferences.Editor){
        val id: Int = sharedPref.getInt("id", 0)
        val userName: String = sharedPref.getString("user_name", "").orEmpty()
        val email: String = sharedPref.getString("email", "").orEmpty()
        val userType: Int = sharedPref.getInt("user_type", 0)
        val token: String = sharedPref.getString("token", "").orEmpty()

        if (id != 0 && userName.isNotEmpty() && email.isNotEmpty() && userType != 0 && token.isNotEmpty()){
            val user: FullUser = FullUser(id, userName, email, userType, token)
            navActivityWithIntent(intent, user)
        }else {
            editor.clear()
            editor.commit()
        }
    }
}