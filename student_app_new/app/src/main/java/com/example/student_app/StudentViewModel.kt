package com.example.student_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: LiveData<MutableList<Student>> = _students

    init {
        _students.value = mutableListOf(
            Student("Nguyen Van A", "20200001", "0123456789", "Hanoi"),
            Student("Tran Thi B", "20200002", "0987654321", "HCM"),
            Student("Le Van C", "20200003", "0912345678", "Danang")
        )
    }

    fun addStudent(student: Student) {
        val currentList = _students.value ?: mutableListOf()
        currentList.add(student)
        _students.value = currentList
    }

    fun updateStudent(oldStudent: Student, newName: String, newMssv: String) {
        val currentList = _students.value ?: return
        val index = currentList.indexOf(oldStudent)
        if (index != -1) {
            currentList[index] = Student(newName, newMssv, oldStudent.phone, oldStudent.address)
            _students.value = currentList
        }
    }

    fun deleteStudent(student: Student) {
        val currentList = _students.value ?: return
        currentList.remove(student)
        _students.value = currentList
    }
}
