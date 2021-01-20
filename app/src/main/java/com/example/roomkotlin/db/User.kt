package com.example.roomkotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
        val title: String,
        val notes:String
):Serializable{
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0
}