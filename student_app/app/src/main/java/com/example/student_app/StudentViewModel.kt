package com.example.student_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.student_app.data.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StudentRepository(application)
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: LiveData<MutableList<Student>> = _students

    init {
        loadStudents()
    }

    private fun loadStudents() {
        viewModelScope.launch {
            val studentList = withContext(Dispatchers.IO) {
                repository.getAllStudents()
            }
            _students.value = studentList.toMutableList()
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertStudent(student)
            }
            _students.value?.add(student)
            _students.value = _students.value
        }
    }

    fun updateStudent(position: Int, student: Student) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateStudent(student)
            }
            _students.value?.set(position, student)
            _students.value = _students.value
        }
    }

    fun deleteStudent(position: Int) {
        viewModelScope.launch {
            val student = _students.value?.get(position)
            student?.let {
                withContext(Dispatchers.IO) {
                    repository.deleteStudent(it.id)
                }
                _students.value?.removeAt(position)
                _students.value = _students.value
            }
        }
    }
}

