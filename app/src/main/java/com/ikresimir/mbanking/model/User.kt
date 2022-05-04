package com.ikresimir.mbanking.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val pin: String?,
)
