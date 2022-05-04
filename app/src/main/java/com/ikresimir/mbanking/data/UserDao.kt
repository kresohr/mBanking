package com.ikresimir.mbanking.data

import androidx.room.*
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.AccountTransactions
import com.ikresimir.mbanking.model.Transaction
import com.ikresimir.mbanking.model.User
import com.ikresimir.mbanking.model.UserAccounts


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAccount(account: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTransaction(transaction: com.ikresimir.mbanking.model.Transaction)

    @Query("DELETE FROM user")
    abstract fun deleteAllUsers()

    @Query("DELETE FROM `transaction`")
    abstract fun deleteAllTransactions()

    @Query("DELETE FROM account")
    abstract fun deleteAllAccounts()

    @Query("SELECT * FROM user WHERE user.id = :userId")
    abstract fun getUserPin(userId: String): User

    @Query("SELECT * FROM user")
    abstract fun getUserFirstName(): User

    @Query("SELECT * FROM account WHERE account.userId = :userId")
    abstract fun getUserAccounts(userId: String): List<Account>

    @Query("SELECT * FROM `transaction`")
    abstract fun getAllTransactions(): List<Transaction>

    @androidx.room.Transaction
    @Query("SELECT * FROM account WHERE account.id = :accountId")
    abstract fun getUsersTransactions(accountId: Int): List<AccountTransactions>
}
