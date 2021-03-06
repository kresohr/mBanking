package com.ikresimir.mbanking.model

import androidx.room.Embedded
import androidx.room.Relation


data class AccountTransactions(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val transactions: List<Transaction>

)
