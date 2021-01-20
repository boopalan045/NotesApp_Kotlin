package com.example.roomkotlin.db

import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao
interface UserDao {

    @Insert()
    suspend fun addNote(user : User)

    @Query("SELECT * FROM user ORDER by id DESC")
    suspend fun getallNotes() : List<User>

    @Insert
    suspend fun addMultipleNotes(vararg note: User)

    @Update
    suspend fun updateNotes(user: User)

    @Delete
    suspend fun deleteNote(user: User)
}