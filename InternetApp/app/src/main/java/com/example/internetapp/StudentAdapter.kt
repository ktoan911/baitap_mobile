package com.example.internetapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StudentAdapter(
    private var students: List<Student>,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
        val tvHoten: TextView = itemView.findViewById(R.id.tvHoten)
        val tvMssv: TextView = itemView.findViewById(R.id.tvMssv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvHoten.text = student.hoten
        holder.tvMssv.text = student.mssv

        val imageUrl = if (student.thumbnail.isNotEmpty()) {
            "https://lebavui.io.vn${student.thumbnail}"
        } else {
            ""
        }

        if (imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .circleCrop()
                .into(holder.imgThumbnail)
        } else {
            holder.imgThumbnail.setImageResource(R.drawable.ic_placeholder)
        }

        holder.itemView.setOnClickListener {
            onItemClick(student)
        }
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
}
