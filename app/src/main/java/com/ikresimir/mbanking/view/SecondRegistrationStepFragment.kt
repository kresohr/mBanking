package com.ikresimir.mbanking.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.viewmodel.RegistrationViewModel


class SecondRegistrationStepFragment : Fragment(R.layout.fragment_second_registration_step) {

    var firstName: String = ""
    var lastName: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second_registration_step, container, false)
        val txtChoosePin: TextView = view.findViewById(R.id.txtChoosePin)
        val btnEnter: Button = view.findViewById(R.id.btnEnter)
        val registrationViewModel = RegistrationViewModel(requireContext())

        //Auto-Focus the input field and show keyboard
        txtChoosePin.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(SHOW_FORCED, 0)

        getDataFromFirstFragment()

        btnEnter.setOnClickListener {
            val PIN = txtChoosePin.text.toString()
            checkPINLength(PIN, registrationViewModel)
        }

        return view
    }

    fun getDataFromFirstFragment() {
        firstName = requireArguments().getString("firstName").toString()
        lastName = requireArguments().getString("lastName").toString()
    }

    fun checkPINLength(PIN: String, registrationViewModel: RegistrationViewModel) {
        if (PIN.length != 6) {
            Toast.makeText(requireContext(), "Must be exactly 6 digits", Toast.LENGTH_SHORT).show()
        } else {
            registrationViewModel.registerUser(firstName, lastName, PIN)
            removeAllFromBackStack()
        }
    }

    fun removeAllFromBackStack(){
        val fm: FragmentManager = requireActivity().supportFragmentManager
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}