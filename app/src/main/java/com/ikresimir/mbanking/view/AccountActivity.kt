package com.ikresimir.mbanking.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.Transaction
import com.ikresimir.mbanking.view.adapter.ViewPagerAdapter
import com.ikresimir.mbanking.viewmodel.AccountViewModel

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val accountViewModel = AccountViewModel(this)

        //Connect to API and populate local DB
        val thread = Thread {
            getAPIresponse(accountViewModel)
        }
        thread.start()

        //Allow time for DB to load
        Thread.sleep(2_000)

        val btnLogout: ImageView = findViewById(R.id.logout)
        val accountList = getAccounts(accountViewModel)
        val transactionList = getTransactions(accountViewModel)

        //ViewPager2
        val adapter = ViewPagerAdapter(accountList, transactionList, this)
        val viewPagerAdapter: ViewPager2 = findViewById(R.id.viewPager)
        viewPagerAdapter.adapter = adapter

        btnLogout.setOnClickListener {
            accountViewModel.logoutUser(this)
            val intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
            finish()
        }
    }

    private fun getTransactions(accountViewModel: AccountViewModel): List<Transaction> {
        val listOfTransactions = accountViewModel.getAllTransactions()
        return listOfTransactions
    }

    fun getAccounts(accountViewModel: AccountViewModel): List<Account> {
        val listOfAccounts = accountViewModel.getAccountList()
        return listOfAccounts
    }

    fun getAPIresponse(accountViewModel: AccountViewModel) {
        accountViewModel.getAPIResponse()
    }
}