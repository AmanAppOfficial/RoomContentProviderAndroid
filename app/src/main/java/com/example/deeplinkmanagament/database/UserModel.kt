package com.example.deeplinkmanagament.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.deeplinkmanagament.Constants

@Entity(tableName = Constants.USER_TABLE)
data class UserModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_ID)
    var id: Long,

    @ColumnInfo(name = Constants.COLUMN_NAME)
    var name: String

    )