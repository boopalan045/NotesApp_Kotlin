package com.example.roomkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [User::class],
        version = 1
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun getNoteDao() : UserDao

    //create instance - base rule
    companion object{
        @Volatile
        private var instance : NoteDataBase ?=null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                NoteDataBase::class.java,
                "noteDatabase"
        ).build()
    }
}

//companion and invoke - need to learn