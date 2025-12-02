package com.example.student_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val edtId = findViewById<EditText>(R.id.edtId)
        val edtName = findViewById<EditText>(R.id.edtName)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val edtAddress = findViewById<EditText>(R.id.edtAddress)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        val student = intent.getSerializableExtra("STUDENT") as? Student

        if (student != null) {
            edtId.setText(student.id)
            edtName.setText(student.name)
            edtPhone.setText(student.phone)
            edtAddress.setText(student.address)
        }

        btnUpdate.setOnClickListener {
            val id = edtId.text.toString().trim()
            val name = edtName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val address = edtAddress.text.toString().trim()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                val updatedStudent = Student(name, id, phone, address)
                val intent = Intent()
                intent.putExtra("UPDATED_STUDENT", updatedStudent)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
