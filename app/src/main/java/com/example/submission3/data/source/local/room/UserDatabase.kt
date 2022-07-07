package com.example.submission3.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission3.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object{

        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            if (instance == null ) {
                synchronized(UserDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "UserGithub.db").build()
                }
            }

            return instance as UserDatabase
        }

    }

}