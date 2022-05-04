package com.ikresimir.mbanking.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserAccounts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val accounts: List<Account>
)
