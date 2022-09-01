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
import androidx.lifecycle.get
import com.example.hotelappmobile.Models.ClientRequest
import com.example.hotelappmobile.Models.ReservationRequest
import com.example.hotelappmobile.R
import com.example.hotelappmobile.ViewModels.CreateReservationViewModel
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.databinding.FragmentCreateReservationBinding

class CreateReservationFragment : Fragment() {
    private lateinit var binding: FragmentCreateReservationBinding
    private lateinit var viewModel: CreateReservationViewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateReservationViewModel::class.java)
        val view: View = binding.root

        viewModel.apply {
            errorMessage.observe(viewLifecycleOwner, Observer {
                binding.tvErrorMessage.text = it
            })

            isSuccessful.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(view.context, "Reservation created!", Toast.LENGTH_LONG).show()
                }
            })

            getCurrentHotel(mainViewModel.user.value!!.token, mainViewModel.user.value!!.id)
        }

        binding.btnClearRadioButton.setOnClickListener {
            binding.rbAdditionalServices.isChecked = false
        }

        binding.btnCreateReservation.setOnClickListener {
            val reservationType: String = binding.etReservationType.text.toString()
            val roomType: String = binding.etRoomType.text.toString()
            val roomNumber: String = binding.etRoomNumber.text.toString()
            val startDate: String = binding.etStartDate.text.toString()
            val endDate: String = binding.etEndDate.text.toString()
            val clientEmail: String = binding.etClientEmail.text.toString()
            var additionalService: String = "0"
            if (binding.rbAdditionalServices.isChecked){
                additionalService = "1"
            }

            if(viewModel.validateFields(reservationType, roomType, roomNumber, startDate, endDate, clientEmail)){
                if (!viewModel.currHotelName.value.isNullOrEmpty()){
                    val reservation: ReservationRequest = ReservationRequest(
                        reservationType,
                        roomType,
                        roomNumber,
                        startDate,
                        endDate,
                        additionalService,
                        clientEmail,
                        viewModel.currHotelName.value.toString()
                    )

                    viewModel.createReservation(mainViewModel.user.value!!.token, reservation)
                }else {
                    viewModel.errorMessage.value = "Error with the current hotel!"
                }
            }
        }

        return view
    }

    private fun clearFields(){

        binding.etClientEmail.text.clear()
    }
}