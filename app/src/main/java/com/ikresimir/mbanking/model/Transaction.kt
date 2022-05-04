package com.ikresimir.mbanking.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int?,
    val id: Int,
    val date: String,
    val description: String,
    val amount: Double,
    val type: String?,
    val accountId: Int
)

//Amount moze biti i Double/Float, samo bi trebalo napraviti custom deserializer za ovaj entity

