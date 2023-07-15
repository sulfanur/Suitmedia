package com.example.suitmediaapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suitmedia.data.entity.RemoteKeys
import com.example.suitmedia.data.entity.RemoteKeysDao
import com.example.suitmedia.data.entity.UserDao
import com.example.suitmediaapp.data.UserResponseItem

@Database(
    entities = [UserResponseItem::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}