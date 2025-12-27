/*
 * MainActivity - màn hình chính quản lý file
 * chức năng: duyệt, tạo, xóa, đổi tên, sao chép file/thư mục
 * xử lý quyền truy cập bộ nhớ ngoài (Android 11+ và phiên bản cũ)
 */
package com.example.file_management

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvCurrentPath: TextView
    private lateinit var adapter: FileAdapter
    private var currentDirectory: File = Environment.getExternalStorageDirectory()
    private var selectedItem: FileItem? = null
    private var selectedItemPosition: Int = -1
    
    private val textEditorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            loadDirectory(currentDirectory)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            loadDirectory(currentDirectory)
        } else {
            Toast.makeText(this, "can quyen truy cap bo nho", Toast.LENGTH_SHORT).show()
        }
    }

    private val manageStorageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                loadDirectory(currentDirectory)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCurrentPath = findViewById(R.id.tvCurrentPath)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FileAdapter(
            emptyList(),
            onItemClick = { item -> handleItemClick(item) },
            onItemLongClick = { item, view -> handleItemLongClick(item, view) }
        )
        recyclerView.adapter = adapter

        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                manageStorageLauncher.launch(intent)
            } else {
                loadDirectory(currentDirectory)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            } else {
                loadDirectory(currentDirectory)
            }
        }
    }

    private fun loadDirectory(directory: File) {
        currentDirectory = directory
        tvCurrentPath.text = directory.absolutePath

        val files = directory.listFiles()
        val fileItems = mutableListOf<FileItem>()

        if (directory.parentFile != null) {
            fileItems.add(
                FileItem(
                    directory.parentFile!!,
                    "..",
                    true,
                    0,
                    directory.parentFile!!.lastModified()
                )
            )
        }

        files?.sortedWith(compareBy<File> { !it.isDirectory }.thenBy { it.name })?.forEach { file ->
            fileItems.add(
                FileItem(
                    file,
                    file.name,
                    file.isDirectory,
                    file.length(),
                    file.lastModified()
                )
            )
        }

        adapter.updateList(fileItems)
    }

    private fun handleItemClick(item: FileItem) {
        if (item.isDirectory) {
            loadDirectory(item.file)
        } else {
            val extension = item.file.extension.lowercase()
            when (extension) {
                "txt" -> openTextFile(item.file)
                "bmp", "jpg", "jpeg", "png" -> openImageFile(item.file)
                else -> Toast.makeText(this, "khong ho tro dinh dang nay", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleItemLongClick(item: FileItem, view: View) {
        selectedItem = item
        selectedItemPosition = adapter.itemCount
        view.showContextMenu()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        selectedItem?.let { fileItem ->
            when (item.itemId) {
                R.id.action_rename -> showRenameDialog(fileItem)
                R.id.action_delete -> showDeleteDialog(fileItem)
                R.id.action_copy -> showCopyDialog(fileItem)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun showRenameDialog(item: FileItem) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val etInput = dialogView.findViewById<EditText>(R.id.etInput)
        etInput.setText(item.name)

        AlertDialog.Builder(this)
            .setTitle("Doi ten")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val newName = etInput.text.toString().trim()
                if (newName.isNotEmpty()) {
                    renameFile(item, newName)
                }
            }
            .setNegativeButton("Huy", null)
            .show()
    }

    private fun showDeleteDialog(item: FileItem) {
        AlertDialog.Builder(this)
            .setTitle("Xac nhan xoa")
            .setMessage("Ban co chac chan muon xoa ${item.name}?")
            .setPositiveButton("Xoa") { _, _ ->
                deleteFile(item)
            }
            .setNegativeButton("Huy", null)
            .show()
    }

    private fun showCopyDialog(item: FileItem) {
        val folders = currentDirectory.listFiles()?.filter { it.isDirectory } ?: emptyList()
        
        if (folders.isEmpty()) {
            Toast.makeText(this, "khong co thu muc nao de sao chep", Toast.LENGTH_SHORT).show()
            return
        }

        val folderNames = folders.map { it.name }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Chon thu muc dich")
            .setItems(folderNames) { _, which ->
                val destinationFolder = folders[which]
                copyFile(item, destinationFolder)
            }
            .setNegativeButton("Huy", null)
            .show()
    }

    private fun renameFile(item: FileItem, newName: String) {
        val newFile = File(item.file.parent, newName)
        if (item.file.renameTo(newFile)) {
            Toast.makeText(this, "da doi ten thanh cong", Toast.LENGTH_SHORT).show()
            loadDirectory(currentDirectory)
        } else {
            Toast.makeText(this, "doi ten that bai", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFile(item: FileItem) {
        val deleted = if (item.isDirectory) {
            item.file.deleteRecursively()
        } else {
            item.file.delete()
        }

        if (deleted) {
            Toast.makeText(this, "da xoa thanh cong", Toast.LENGTH_SHORT).show()
            loadDirectory(currentDirectory)
        } else {
            Toast.makeText(this, "xoa that bai", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyFile(item: FileItem, destinationFolder: File) {
        try {
            val destinationFile = File(destinationFolder, item.name)
            FileInputStream(item.file).use { input ->
                FileOutputStream(destinationFile).use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(this, "sao chep thanh cong", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "sao chep that bai: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openTextFile(file: File) {
        val intent = Intent(this, TextEditorActivity::class.java)
        intent.putExtra("FILE_PATH", file.absolutePath)
        intent.putExtra("IS_NEW_FILE", false)
        textEditorLauncher.launch(intent)
    }

    private fun openImageFile(file: File) {
        val intent = Intent(this, ImageViewerActivity::class.java)
        intent.putExtra("FILE_PATH", file.absolutePath)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_folder -> {
                showCreateFolderDialog()
                true
            }
            R.id.action_create_file -> {
                showCreateFileDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateFolderDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val etInput = dialogView.findViewById<EditText>(R.id.etInput)
        etInput.hint = "Ten thu muc moi"

        AlertDialog.Builder(this)
            .setTitle("Tao thu muc moi")
            .setView(dialogView)
            .setPositiveButton("Tao") { _, _ ->
                val folderName = etInput.text.toString().trim()
                if (folderName.isNotEmpty()) {
                    createFolder(folderName)
                }
            }
            .setNegativeButton("Huy", null)
            .show()
    }

    private fun showCreateFileDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val etInput = dialogView.findViewById<EditText>(R.id.etInput)
        etInput.hint = "Ten file van ban moi"

        AlertDialog.Builder(this)
            .setTitle("Tao file van ban moi")
            .setView(dialogView)
            .setPositiveButton("Tao") { _, _ ->
                val fileName = etInput.text.toString().trim()
                if (fileName.isNotEmpty()) {
                    createTextFile(fileName)
                }
            }
            .setNegativeButton("Huy", null)
            .show()
    }

    private fun createFolder(folderName: String) {
        val newFolder = File(currentDirectory, folderName)
        if (newFolder.mkdir()) {
            Toast.makeText(this, "da tao thu muc thanh cong", Toast.LENGTH_SHORT).show()
            loadDirectory(currentDirectory)
        } else {
            Toast.makeText(this, "tao thu muc that bai", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createTextFile(fileName: String) {
        val finalFileName = if (fileName.endsWith(".txt")) fileName else "$fileName.txt"
        val intent = Intent(this, TextEditorActivity::class.java)
        intent.putExtra("FILE_PATH", currentDirectory.absolutePath)
        intent.putExtra("FILE_NAME", finalFileName)
        intent.putExtra("IS_NEW_FILE", true)
        textEditorLauncher.launch(intent)
    }

    override fun onBackPressed() {
        if (currentDirectory.parentFile != null && currentDirectory != Environment.getExternalStorageDirectory()) {
            loadDirectory(currentDirectory.parentFile!!)
        } else {
            super.onBackPressed()
        }
    }
}

