package com.example.deeplinkmanagament.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deeplinkmanagament.Constants

@Dao
interface UserDao {

    @Insert
    fun insert(userModel: UserModel): Long

    @Query("Select * FROM " + Constants.USER_TABLE)
    fun selectAll(): Cursor

}