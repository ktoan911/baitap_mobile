package com.example.internetapp

import java.io.Serializable

data class Student(
    val id: Int,
    val mssv: String,
    val hoten: String,
    val ngaysinh: String,
    val email: String,
    val thumbnail: String
) : Serializable
