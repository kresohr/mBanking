package com.ikresimir.mbanking.viewmodel

import android.content.Context
import android.content.Intent
import com.ikresimir.mbanking.data.Repository
import com.ikresimir.mbanking.view.AccountActivity

class RegistrationViewModel(context: Context) {
    val repository = Repository(context)
    val context = context

    fun registerUser(firstName: String, lastName: String, PIN: String) {
        if (repository.registerUser(firstName, lastName, PIN, context)) {
            val intent = Intent(context, AccountActivity::class.java)
            context.startActivity(intent)
        }
    }
}