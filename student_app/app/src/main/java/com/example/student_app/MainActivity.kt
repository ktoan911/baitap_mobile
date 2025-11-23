package com.example.student_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Student(val name: String, val id: String)

class MainActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtId: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // anh xa cac thanh phan
        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        listView = findViewById(R.id.listView)

        // them du lieu mau
        students.add(Student("Nguyễn Văn A", "20200001"))
        students.add(Student("Trần Thị B", "20200002"))
        students.add(Student("Lê Văn C", "20200003"))
        students.add(Student("Phạm Thị D", "20200004"))
        students.add(Student("Hoàng Văn E", "20200005"))
        students.add(Student("Vũ Thị F", "20200006"))
        students.add(Student("Đặng Văn G", "20200007"))
        students.add(Student("Bùi Thị H", "20200008"))
        students.add(Student("Hồ Văn I", "20200009"))
        
        adapter = StudentAdapter(this, students) { position ->
            students.removeAt(position)
            adapter.notifyDataSetChanged()
            if (selectedPosition == position) {
                edtName.setText("")
                edtId.setText("")
                selectedPosition = -1
            }
        }
        listView.adapter = adapter

        // xu ly nut add
        btnAdd.setOnClickListener {
            val name = edtName.text.toString().trim()
            val id = edtId.text.toString().trim()
            
            if (name.isNotEmpty() && id.isNotEmpty()) {
                students.add(Student(name, id))
                adapter.notifyDataSetChanged()
                edtName.setText("")
                edtId.setText("")
            }
        }

        // xu ly nut update
        btnUpdate.setOnClickListener {
            if (selectedPosition != -1) {
                val name = edtName.text.toString().trim()
                val id = edtId.text.toString().trim()
                
                if (name.isNotEmpty() && id.isNotEmpty()) {
                    students[selectedPosition] = Student(name, id)
                    adapter.notifyDataSetChanged()
                    edtName.setText("")
                    edtId.setText("")
                    selectedPosition = -1
                }
            }
        }
        
        // xu ly khi nhan vao sinh vien de cap nhat
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            edtName.setText(students[position].name)
            edtId.setText(students[position].id)
        }
    }
}
