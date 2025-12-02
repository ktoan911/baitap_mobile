package com.example.student_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class StudentAdapter(
    private val context: Context,
    private val students: List<Student>,
    private val onDelete: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val student = students[position]
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvId = view.findViewById<TextView>(R.id.tvId)
        val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)

        tvName.text = student.name
        tvId.text = student.id
        btnDelete.setOnClickListener { onDelete(position) }

        return view
    }
}
