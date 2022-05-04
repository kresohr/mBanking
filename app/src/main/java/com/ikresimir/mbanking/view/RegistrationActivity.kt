package com.ikresimir.mbanking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikresimir.mbanking.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val firstFragment = FirstRegistrationStepFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }
    }
}