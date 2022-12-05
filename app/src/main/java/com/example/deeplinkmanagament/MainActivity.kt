package com.example.deeplinkmanagament

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.deeplinkmanagament.provider.UserContentProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var showBtn: Button
    private lateinit var insertBtn: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBtn = findViewById(R.id.show_btn)
        tvName = findViewById(R.id.tv_name)
        insertBtn = findViewById(R.id.insert_btn)

        showBtn.setOnClickListener {
            CoroutineScope(IO).launch {

                val cursor = contentResolver.query(
                    Uri.parse("content://com.example.deeplinkmanagement.provider/" + Constants.USER_TABLE + "/1"),
                    null, null, null, null
                )

                if(cursor!=null) {
                    cursor.moveToFirst()
                    var strBuild = ""
                    while (!cursor.isLast) {
                        strBuild += (cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME))) + "\n"
                        cursor.moveToNext()
                    }
                    strBuild += (cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME))) + "\n"

                    withContext(Main){
                        tvName.text = strBuild
                    }
                }
                cursor?.close()
            }
        }

        insertBtn.setOnClickListener {
            CoroutineScope(IO).launch {
                val values = ContentValues()
                values.put(Constants.COLUMN_NAME, System.currentTimeMillis().toString())
                contentResolver.insert(UserContentProvider.URI_USERS, values)
            }
        }

    }
}