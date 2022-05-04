package com.ikresimir.mbanking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ikresimir.mbanking.data.UserDao
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.Transaction
import com.ikresimir.mbanking.model.User

@Database(
    entities = [
        User::class,
        Account::class,
        Transaction::class
    ],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao

    companion object{
        @Volatile
        private var INSTANCE: com.ikresimir.mbanking.database.Database? = null

        fun getInstance(context: Context): com.ikresimir.mbanking.database.Database{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    com.ikresimir.mbanking.database.Database::class.java,
                    "mbanking"
                ).allowMainThreadQueries().build().also{
                    INSTANCE = it
                }
            }
        }
    }
}