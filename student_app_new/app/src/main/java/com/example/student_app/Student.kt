package com.example.student_app

import java.io.Serializable

data class Student(
    var name: String,
    var id: String,
    var phone: String,
    var address: String
) : Serializable
