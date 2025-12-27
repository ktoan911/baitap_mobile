package com.example.file_management

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class TextViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_viewer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvContent = findViewById<TextView>(R.id.tvContent)
        val filePath = intent.getStringExtra("FILE_PATH")

        if (filePath != null) {
            try {
                val file = File(filePath)
                val content = file.readText()
                tvContent.text = content
                title = file.name
            } catch (e: Exception) {
                Toast.makeText(this, "khong the doc file: ${e.message}", Toast.LENGTH_SHORT).show()
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

