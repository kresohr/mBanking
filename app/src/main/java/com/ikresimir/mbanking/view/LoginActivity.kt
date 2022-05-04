package com.ikresimir.mbanking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.viewmodel.LoginViewModel
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnFingerprint: ImageView = findViewById(R.id.btnAuthenticate)
        val lblLoginTitle: TextView = findViewById(R.id.lblLoginTitle)
        getUserFirstName(lblLoginTitle)

        btnFingerprint.setOnClickListener {
            biometricAuthentication()
            biometricPrompt.authenticate(promptInfo)
        }
    }

    fun biometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    Toast.makeText(this@LoginActivity, "Error: " + errString, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@LoginActivity, "Successfull!", Toast.LENGTH_SHORT)
                        .show()
                    grantAccess()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    Toast.makeText(this@LoginActivity, "Authentication failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Use your biometric login to access your account")
            .setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()
    }

    fun grantAccess() {
        val loginViewModel = LoginViewModel(this)
        loginViewModel.showAccount(this)
    }

    fun getUserFirstName(lblLoginTitle: TextView) {
        val loginViewModel = LoginViewModel(this)
        lblLoginTitle.text = "Hello, ${loginViewModel.getUserFirstName()}"
    }
}