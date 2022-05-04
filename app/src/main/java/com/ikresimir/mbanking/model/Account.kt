package com.ikresimir.mbanking.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "account")
data class Account (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val iban: String,
    val amount: String,
    val currency: String,
    val userId: Int
    )

