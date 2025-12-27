/*
 * TextEditorActivity - màn hình soạn thảo văn bản
 * đọc và ghi file .txt từ bộ nhớ ngoài
 */
package com.example.file_management

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class TextEditorActivity : AppCompatActivity() {

    private lateinit var etTextContent: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private var filePath: String? = null
    private var fileName: String? = null
    private var isNewFile: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_editor)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etTextContent = findViewById(R.id.etTextContent)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        filePath = intent.getStringExtra("FILE_PATH")
        fileName = intent.getStringExtra("FILE_NAME")
        isNewFile = intent.getBooleanExtra("IS_NEW_FILE", false)

        if (isNewFile) {
            title = "Tao file moi: $fileName"
        } else if (filePath != null) {
            val file = File(filePath!!)
            title = "Chinh sua: ${file.name}"
            try {
                etTextContent.setText(file.readText())
            } catch (e: Exception) {
                Toast.makeText(this, "khong the doc file: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnSave.setOnClickListener {
            saveFile()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveFile() {
        val content = etTextContent.text.toString()
        
        try {
            val file = if (isNewFile && filePath != null) {
                File(filePath!!, fileName!!)
            } else if (filePath != null) {
                File(filePath!!)
            } else {
                Toast.makeText(this, "loi: khong xac dinh duoc file", Toast.LENGTH_SHORT).show()
                return
            }

            file.writeText(content)
            Toast.makeText(this, "da luu file thanh cong", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "luu file that bai: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

