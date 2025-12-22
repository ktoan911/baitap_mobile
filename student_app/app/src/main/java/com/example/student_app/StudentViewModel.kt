package com.example.student_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: LiveData<MutableList<Student>> = _students

    init {
        _students.value = mutableListOf(
            Student("20200001", "Nguyễn Văn A", "0912345671", "Hà Nội"),
            Student("20200002", "Trần Thị B", "0912345672", "Hồ Chí Minh"),
            Student("20200003", "Lê Văn C", "0912345673", "Đà Nẵng"),
            Student("20200004", "Phạm Thị D", "0912345674", "Hải Phòng"),
            Student("20200005", "Hoàng Văn E", "0912345675", "Cần Thơ"),
            Student("20200006", "Vũ Thị F", "0912345676", "Huế"),
            Student("20200007", "Đặng Văn G", "0912345677", "Nha Trang"),
            Student("20200008", "Bùi Thị H", "0912345678", "Quy Nhơn"),
            Student("20200009", "Hồ Văn I", "0912345679", "Vũng Tàu")
        )
    }

    fun addStudent(student: Student) {
        _students.value?.add(student)
        _students.value = _students.value
    }

    fun updateStudent(position: Int, student: Student) {
        _students.value?.set(position, student)
        _students.value = _students.value
    }

    fun deleteStudent(position: Int) {
        _students.value?.removeAt(position)
        _students.value = _students.value
    }
}

