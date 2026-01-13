package com.example.internetapp

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: StudentAdapter
    private var studentList: List<Student> = emptyList()
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        adapter = StudentAdapter(emptyList()) { student ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("student", student)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterStudents(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterStudents(newText)
                return true
            }
        })

        fetchStudents()
    }

    private fun fetchStudents() {
        val request = Request.Builder()
            .url("https://lebavui.io.vn/students")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Lỗi kết nối: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()
                    val type = object : TypeToken<List<Student>>() {}.type
                    studentList = Gson().fromJson(json, type)
                    runOnUiThread {
                        adapter.updateData(studentList)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Lỗi: ${response.code}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun filterStudents(query: String?) {
        if (query.isNullOrEmpty()) {
            adapter.updateData(studentList)
        } else {
            val filtered = studentList.filter {
                it.hoten.contains(query, ignoreCase = true) ||
                it.mssv.contains(query, ignoreCase = true)
            }
            adapter.updateData(filtered)
        }
    }
}
