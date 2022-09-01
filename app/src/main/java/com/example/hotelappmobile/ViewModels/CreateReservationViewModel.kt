package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.ReservationRequest
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class CreateReservationViewModel : ViewModel() {
    private val repository: HotelActionsRepository = HotelActionsRepository()
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isSuccessful: MutableLiveData<Boolean> = MutableLiveData(false)
    val currHotelName: MutableLiveData<String> = MutableLiveData()

    fun validateFields(
        reservationType: String,
        roomType: String,
        roomNumber: String,
        startDate: String,
        endDate: String,
        clientEmail: String
    ): Boolean{
        if (reservationType.isEmpty() || roomType.isEmpty() || roomNumber.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || clientEmail.isEmpty()){
            errorMessage.value = "There are empty fields!"
            return false
        } else {
            if (roomNumber.all { char-> char.isDigit() }){
                if (Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()){
                    errorMessage.value = ""
                    return true
                }else {
                    errorMessage.value = "Email is in wrong format!"
                    return false
                }
            }
            else {
                errorMessage.value = "Room number requires number!"
                return false
            }
        }
    }

    fun getCurrentHotel(token: String, receptionistId: Int){
        viewModelScope.launch {
            try {
                val response = repository.getHotelByReceptionist("Bearer $token", receptionistId)
                if (response.isSuccessful){
                    currHotelName.value = response.body()!!.hotelName
                    errorMessage.value = ""
                }else {
                    errorMessage.value = "Error loading current hotel info!"
                }
            }catch (e: Exception){
                errorMessage.value = e.message
            }
        }
    }

    fun createReservation(token: String, client: ReservationRequest){
        viewModelScope.launch {
            try {
                val response = repository.createReservation("Bearer $token", client)

                if (response.isSuccessful){
                    isSuccessful.value = true
                }else if (response.code() == 422){
                    errorMessage.value = "Error - bad credentials or wrong client email!"
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