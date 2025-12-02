package com.example.student_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()
    private var selectedPosition = -1

    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newStudent = result.data?.getSerializableExtra("NEW_STUDENT") as? Student
            if (newStudent != null) {
                students.add(newStudent)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private val updateStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
            if (updatedStudent != null && selectedPosition != -1) {
                students[selectedPosition] = updatedStudent
                adapter.notifyDataSetChanged()
                selectedPosition = -1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        students.add(Student("Nguyễn Văn A", "20200001", "0901234567", "Hà Nội"))
        students.add(Student("Trần Thị B", "20200002", "0901234568", "Hải Phòng"))
        students.add(Student("Lê Văn C", "20200003", "0901234569", "Đà Nẵng"))

        adapter = StudentAdapter(this, students) { position ->
            students.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("STUDENT", students[position])
            updateStudentLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
