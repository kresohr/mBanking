package com.ikresimir.mbanking.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.viewmodel.LoginViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val loginViewModel = LoginViewModel(this)

        checkIfUserLogged(loginViewModel)
        val btnGetStarted: Button = findViewById(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            this.startActivity(intent)
        }
    }

    fun checkIfUserLogged(loginViewModel: LoginViewModel) {
        if (loginViewModel.checkIfUserLogged(this)) {
            val intent = Intent(this, AccountActivity::class.java)
            this.startActivity(intent)
            finish()
        } else {
            checkIfRegistered(loginViewModel)
        }
    }

    fun checkIfRegistered(loginViewModel: LoginViewModel) {
        if (loginViewModel.checkIfRegistered(this)) {
            val intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
            finish()
        }
    }


}