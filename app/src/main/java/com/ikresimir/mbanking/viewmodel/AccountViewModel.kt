package com.ikresimir.mbanking.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ikresimir.mbanking.data.Repository
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.Transaction

class AccountViewModel(context: Context) {
    private val repository = Repository(context)
    fun logoutUser(context: Context) {
        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("loggedIn", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear().apply()
        Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
    }

    fun getAccountList(): List<Account> {
        return repository.getAccounts()
    }

    fun getAllTransactions(): List<Transaction> {
        return repository.getAllTransactions()
    }

    fun getAPIResponse() {
        repository.getAPIResponse()
    }
}