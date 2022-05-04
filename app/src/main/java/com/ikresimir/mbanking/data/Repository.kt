package com.ikresimir.mbanking.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ikresimir.mbanking.database.Database
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.Transaction
import com.ikresimir.mbanking.model.User
import org.json.JSONObject
import org.json.JSONTokener
import java.net.URL
import org.mindrot.jbcrypt.*

const val API_URL = "URL GOES HERE"
var loggedIn = false
var existingUser = false


class Repository(context: Context) {
    var getResponse = ""
    val dao = Database.getInstance(context).userDao

    fun getAPIResponse() {
        try {
            getResponse = URL(API_URL).readText()
            deserializeJson()
        } catch (e: Exception) {
            println("Connection failed!")
            e.printStackTrace()
        }

    }

    fun deserializeJson() {
        val listOfAccounts = mutableListOf<Account>()
        val listOfTransactions = mutableListOf<Transaction>()

        val jsonObject = JSONTokener(getResponse).nextValue() as JSONObject
        val userId = jsonObject.getInt("user_id")
        val accountArray = jsonObject.getJSONArray("acounts")

        //GET all fields in "acounts" from JSON response
        for (accountItem in 0 until accountArray.length()) {
            val accountID = accountArray.getJSONObject(accountItem).getInt("id")
            val IBAN = accountArray.getJSONObject(accountItem).getString("IBAN")
            val accountBalance = accountArray.getJSONObject(accountItem).getString("amount")
            val currency = accountArray.getJSONObject(accountItem).getString("currency")
            val transactionArray =
                accountArray.getJSONObject(accountItem).getJSONArray("transactions")
            listOfAccounts.add(Account(accountID, IBAN, accountBalance, currency, userId))
            val transactionList = compareData(accountID)

            //GET all transactions from single "acounts"
            for (transactionItem in 0 until transactionArray.length()) {
                val transactionID = transactionArray.getJSONObject(transactionItem).getInt("id")
                val date = transactionArray.getJSONObject(transactionItem).getString("date")
                val description =
                    transactionArray.getJSONObject(transactionItem).getString("description")
                val amount = transactionArray.getJSONObject(transactionItem).getString("amount")

                //Remove string part from amount to parse in Double
                var amountFormatted = amount.replace(',', '.')
                amountFormatted = amountFormatted.replace(" ", "")
                amountFormatted = amountFormatted.dropLast(3)

                //Not every JSON response record has a type
                var type = ""
                if (transactionArray.getJSONObject(transactionItem).has("type")) {
                    type = transactionArray.getJSONObject(transactionItem).getString("type")
                }

                //Check if entry already exists
                var found = false
                for (item in transactionList) {
                    if (item.id == transactionID) {
                        found = true
                    }
                }
                if (!found) {
                    listOfTransactions.add(
                        Transaction(
                            null,
                            transactionID,
                            date,
                            description,
                            amountFormatted.toDouble(),
                            type,
                            accountID
                        )
                    )
                }
            }
        }
        saveDataToDB(listOfAccounts, listOfTransactions)
    }

    fun saveDataToDB(accountList: MutableList<Account>, transactionList: MutableList<Transaction>) {
        for (account in accountList) {
            dao.insertAccount(account)
        }
        for (transaction in transactionList) {
            dao.insertTransaction(transaction)
        }
    }

    fun remainLoggedIn(context: Context) {
        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("loggedIn", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("loggedIn", true)
        editor.commit()
        rememberUser(context)
    }
    fun checkIfLoggedIn(context: Context): Boolean {
        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("loggedIn", MODE_PRIVATE)
        loggedIn = sharedPreference.getBoolean("loggedIn", false)
        return loggedIn
    }

    private fun rememberUser(context: Context){
        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("existingUser", MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("existingUser", true)
        editor.commit()
    }

    fun checkIfRegistered(context: Context): Boolean {
        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("existingUser", MODE_PRIVATE)
        existingUser = sharedPreference.getBoolean("existingUser", false)
        return existingUser
    }



    fun registerUser(firstName: String, lastName: String, pin: String, context: Context): Boolean {
        val hashedPin = BCrypt.hashpw(pin, BCrypt.gensalt()).toString()
        try {
            dao.insertUser(User(1, firstName, lastName, hashedPin))
            Toast.makeText(context, "Welcome $firstName $lastName", Toast.LENGTH_SHORT).show()
            //Keep the user logged in
            remainLoggedIn(context)
            return true
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(context, "User already exists!", Toast.LENGTH_SHORT).show()
            println(e)
            return false
        } catch (e: Exception) {
            Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show()
            println(e)
            return false
        }
    }

    //Additional feature to log in with PIN
    fun checkLoginData(userPin: String, context: Context): Boolean {
        val getUserList = dao.getUserPin("1")
        //Check if hashed password matches the one in DB
        if(BCrypt.checkpw(userPin, getUserList.pin)){
            remainLoggedIn(context)
            println("Correct PIN")
            return true
        } else{
            println("Incorrect PIN")
            Toast.makeText(context, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            return false
        }

    }

    fun getAccounts(): List<Account> {
        //Hardcoded userID because of API response
        return dao.getUserAccounts("1")
    }

    fun getAllTransactions(): List<Transaction> {
        return dao.getAllTransactions()
    }

    fun compareData(accountId: Int): List<Transaction> {
        val accTr = dao.getUsersTransactions(accountId)
        var transactionList = listOf<Transaction>()
        for (item in accTr) {
            transactionList = item.transactions
        }
        return transactionList
    }

    fun deleteAllEntries(context: Context) {
        dao.deleteAllAccounts()
        dao.deleteAllTransactions()
        dao.deleteAllUsers()

        val sharedPreference: SharedPreferences =
            context.getSharedPreferences("existingUser", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear().apply()
        Toast.makeText(context, "Data cleared!", Toast.LENGTH_SHORT).show()
    }

    fun getUserFirstName(): String{
        var firstName = ""
        try{
            val user = dao.getUserFirstName()
            firstName = user.firstName.toString()
            return firstName
        }catch(e: Exception){
            println("Empty User!")
            return firstName
        }

    }


}