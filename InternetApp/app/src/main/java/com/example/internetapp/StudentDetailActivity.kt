package com.example.internetapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class StudentDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chi tiết sinh viên"

        val student = intent.getSerializableExtra("student") as? Student

        val imgThumbnail: ImageView = findViewById(R.id.imgDetailThumbnail)
        val tvHoten: TextView = findViewById(R.id.tvDetailHoten)
        val tvMssv: TextView = findViewById(R.id.tvDetailMssv)
        val tvNgaysinh: TextView = findViewById(R.id.tvDetailNgaysinh)
        val tvEmail: TextView = findViewById(R.id.tvDetailEmail)

        student?.let {
            tvHoten.text = it.hoten
            tvMssv.text = it.mssv
            tvNgaysinh.text = it.ngaysinh
            tvEmail.text = it.email

            val imageUrl = if (it.thumbnail.isNotEmpty()) {
                "https://lebavui.io.vn${it.thumbnail}"
            } else {
                ""
            }

            if (imageUrl.isNotEmpty()) {
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .circleCrop()
                    .into(imgThumbnail)
            } else {
                imgThumbnail.setImageResource(R.drawable.ic_placeholder)
            }
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
