package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.ClientRequest
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class CreateClientViewModel : ViewModel() {
    private val repository: HotelActionsRepository = HotelActionsRepository()
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isSuccessful: MutableLiveData<Boolean> = MutableLiveData(false)

    fun validateFields(firstName: String, lastName: String, email: String): Boolean{
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            errorMessage.value = "There are empty fields!"
            return false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errorMessage.value = ""
                return true
            }else {
                errorMessage.value = "Email is in wrong format!"
                return false
            }
        }
    }

    fun createClient(token: String, client: ClientRequest){
        viewModelScope.launch {
            try {
                val response = repository.createClient("Bearer $token", client)

                if (response.isSuccessful){
                    isSuccessful.value = true
                }else if (response.code() == 422){
                    errorMessage.value = "Error - bad credentials!"
                    isSuccessful.value = false
                }else {
                    errorMessage.value = "Error ${response.code()} - ${response.message()}"
                    isSuccessful.value = false
                }
            }catch (e: HttpException){
                errorMessage.value = "Error ${e.code()} - ${e.message()}"
            }catch (e: Exception){
                errorMessage.value = "Error"
            }
        }
    }
}