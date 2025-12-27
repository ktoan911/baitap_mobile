package com.example.file_management

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class FileAdapter(
    private var fileList: List<FileItem>,
    private val onItemClick: (FileItem) -> Unit,
    private val onItemLongClick: (FileItem, View) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
        var currentItem: FileItem? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let { onItemClick(it) }
            }

            itemView.setOnLongClickListener {
                currentItem?.let { item ->
                    onItemLongClick(item, itemView)
                }
                true
            }

            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            currentItem?.let { item ->
                if (item.isDirectory) {
                    (itemView.context as? MainActivity)?.menuInflater?.inflate(
                        R.menu.menu_context_folder,
                        menu
                    )
                } else {
                    (itemView.context as? MainActivity)?.menuInflater?.inflate(
                        R.menu.menu_context_file,
                        menu
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = fileList[position]
        holder.currentItem = item
        holder.tvName.text = item.name

        if (item.isDirectory) {
            holder.ivIcon.setImageResource(android.R.drawable.ic_menu_upload)
            holder.tvDetails.text = "Thu muc | ${dateFormat.format(Date(item.lastModified))}"
        } else {
            holder.ivIcon.setImageResource(android.R.drawable.ic_menu_report_image)
            holder.tvDetails.text = "${formatFileSize(item.size)} | ${dateFormat.format(Date(item.lastModified))}"
        }
    }

    override fun getItemCount(): Int = fileList.size

    fun updateList(newList: List<FileItem>) {
        fileList = newList
        notifyDataSetChanged()
    }

    private fun formatFileSize(size: Long): String {
        if (size < 1024) return "$size B"
        if (size < 1024 * 1024) return "${size / 1024} KB"
        if (size < 1024 * 1024 * 1024) return "${size / (1024 * 1024)} MB"
        return "${size / (1024 * 1024 * 1024)} GB"
    }
}

