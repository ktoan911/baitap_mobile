/*
 * FileItem - data class lưu thông tin file/thư mục
 * chứa đường dẫn, tên, loại, kích thước và thời gian sửa cuối
 */
package com.example.file_management

import java.io.File

data class FileItem(
    val file: File,
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    val lastModified: Long
)

