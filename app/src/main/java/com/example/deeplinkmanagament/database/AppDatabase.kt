package com.example.deeplinkmanagament.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deeplinkmanagament.Constants

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object{
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java,
                Constants.USER_DB)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }

}