/*
 * ImageViewerActivity - màn hình hiển thị ảnh
 * đọc và hiển thị file ảnh từ bộ nhớ ngoài
 */
package com.example.file_management

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ivImage = findViewById<ImageView>(R.id.ivImage)
        val filePath = intent.getStringExtra("FILE_PATH")

        if (filePath != null) {
            try {
                val file = File(filePath)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                if (bitmap != null) {
                    ivImage.setImageBitmap(bitmap)
                    title = file.name
                } else {
                    Toast.makeText(this, "khong the hien thi anh", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "khong the mo anh: ${e.message}", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "khong tim thay file", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

