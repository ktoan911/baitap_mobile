package com.example.student_app.data

import android.content.ContentValues
import android.content.Context
import com.example.student_app.Student

class StudentRepository(context: Context) {
    private val dbHelper = StudentDbHelper(context)

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = dbHelper.readableDatabase
        
        val cursor = db.query(
            StudentContract.StudentEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_NAME))
                val phone = getString(getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_PHONE))
                val address = getString(getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_ADDRESS))
                students.add(Student(id, name, phone, address))
            }
        }
        cursor.close()
        
        return students
    }

    fun insertStudent(student: Student): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentContract.StudentEntry.COLUMN_ID, student.id)
            put(StudentContract.StudentEntry.COLUMN_NAME, student.name)
            put(StudentContract.StudentEntry.COLUMN_PHONE, student.phone)
            put(StudentContract.StudentEntry.COLUMN_ADDRESS, student.address)
        }
        return db.insert(StudentContract.StudentEntry.TABLE_NAME, null, values)
    }

    fun updateStudent(student: Student): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentContract.StudentEntry.COLUMN_NAME, student.name)
            put(StudentContract.StudentEntry.COLUMN_PHONE, student.phone)
            put(StudentContract.StudentEntry.COLUMN_ADDRESS, student.address)
        }
        val selection = "${StudentContract.StudentEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(student.id)
        return db.update(StudentContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun deleteStudent(id: String): Int {
        val db = dbHelper.writableDatabase
        val selection = "${StudentContract.StudentEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(id)
        return db.delete(StudentContract.StudentEntry.TABLE_NAME, selection, selectionArgs)
    }
}
