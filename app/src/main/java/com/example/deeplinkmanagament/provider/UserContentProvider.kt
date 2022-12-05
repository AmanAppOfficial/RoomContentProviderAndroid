package com.example.deeplinkmanagament.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.deeplinkmanagament.Constants
import com.example.deeplinkmanagament.database.AppDatabase
import com.example.deeplinkmanagament.database.UserModel

class UserContentProvider: ContentProvider() {

    companion object{
        // authority of this content provider
        const val AUTHORITY = "com.example.deeplinkmanagement.provider"

        //uri of user table
        val URI_USERS = Uri.parse("content://" + AUTHORITY + "/" + Constants.USER_TABLE)!!

        // search codes
        const val ALL_USERS_CODE = 1
        const val ONE_USER_CODE = 2

        private val MATCHER: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(AUTHORITY, Constants.USER_TABLE, ALL_USERS_CODE)
            MATCHER.addURI(AUTHORITY, Constants.USER_TABLE + "/*", ONE_USER_CODE)
        }

    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        val code = MATCHER.match(uri)
        if(code == ALL_USERS_CODE || code == ONE_USER_CODE){
            val context = context
            context?.let {
                val userDao = AppDatabase.getInstance(context).userDao()
                val cursor = userDao.selectAll()
                cursor.setNotificationUri(context.contentResolver, uri)
                return cursor
            }
        }
        else{
            throw IllegalArgumentException("Unknown URI: $uri")
        }
        return null

    }

    override fun getType(uri: Uri): String? {
        return when(MATCHER.match(uri)){
            ONE_USER_CODE -> "vnd.android.cursor.dir/" + AUTHORITY + "." + Constants.USER_TABLE
            ALL_USERS_CODE -> "vnd.android.cursor.item/" + AUTHORITY + "." + Constants.USER_TABLE
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val context = context
        context?.let {
            val id = AppDatabase.getInstance(context).userDao().insert(fromContentValues(values!!))
            context.contentResolver.notifyChange(uri, null)
            return ContentUris.withAppendedId(uri, id)
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
         return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    private fun fromContentValues(values: ContentValues): UserModel {
        val name = values.getAsString(Constants.COLUMN_NAME)
        return UserModel(0L , name)
    }
}