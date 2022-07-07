package com.example.submission3.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission3.data.source.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM githubUser")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("DELETE FROM githubUser WHERE username= :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT * FROM githubUser WHERE username = :username")
    suspend fun getUserByUsername(username: String) : UserEntity

}