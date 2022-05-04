package com.ikresimir.mbanking.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.ikresimir.mbanking.data.Repository
import com.ikresimir.mbanking.view.AccountActivity

class LoginViewModel(context: Context) {
    val repository = Repository(context)
    fun checkIfUserLogged(context: Context): Boolean {
        return (repository.checkIfLoggedIn(context))
    }

    fun getUserFirstName(): String {
        return repository.getUserFirstName()
    }

    //Additional feature to log in with PIN
    fun checkLoginData(userPin: String, context: Context): Boolean {
        return repository.checkLoginData(userPin, context)
    }

    fun checkIfRegistered(context: Context): Boolean {
        return repository.checkIfRegistered(context)
    }

    fun showAccount(context: Context) {
        val intent = Intent(context, AccountActivity::class.java)
        repository.remainLoggedIn(context)
        Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
        context.startActivity(intent)

    }

    //Just for debugging
    fun deleteAllEntries(context: Context) {
        repository.deleteAllEntries(context)
    }
}