package com.ikresimir.mbanking.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ikresimir.mbanking.R

class FirstRegistrationStepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_registration_step, container, false)
        val btnNext: Button = view.findViewById(R.id.btnNext)
        val txtFirstName: TextView = view.findViewById(R.id.txtFirstName)
        val txtLastName: TextView = view.findViewById(R.id.txtLastName)

        btnNext.setOnClickListener {
            val firstName = txtFirstName.text.toString()
            val lastName = txtLastName.text.toString()
            checkLength(firstName, lastName)
        }
        return view
    }

    fun checkLength(firstName: String, lastName: String) {
        if (firstName.length > 30 ||
            firstName.isEmpty() ||
            lastName.length > 30 ||
            lastName.isEmpty()
        ) {
            Toast.makeText(
                requireContext(),
                "Must be under 30 characters and not empty!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            startSecondFragment(firstName, lastName)
        }
    }

    fun startSecondFragment(firstName: String, lastName: String) {
        val bundle = Bundle().apply {
            putString("firstName", firstName)
            putString("lastName", lastName)
        }
        //Show second fragment and pass data to it
        val transaction = this.parentFragmentManager.beginTransaction()
        val secondFragment = SecondRegistrationStepFragment()
        secondFragment.arguments = bundle
        transaction.replace(R.id.flFragment, secondFragment).addToBackStack("firstFragment")
        transaction.commit()
    }

}