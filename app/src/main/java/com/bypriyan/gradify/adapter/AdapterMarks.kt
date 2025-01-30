package com.bypriyan.gradify.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bypriyan.gradify.model.Mark
import com.bypriyan.gradify.R
import com.google.android.material.card.MaterialCardView
import kotlin.jvm.java

class AdapterMarks (private val context: Context, private val markList: List<Mark>):
    RecyclerView.Adapter<AdapterMarks.HolderMarks>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMarks {
        val view = LayoutInflater.from(context).inflate(R.layout.row_marks, parent, false)
        return HolderMarks(view)    }

    override fun onBindViewHolder(holder: HolderMarks, position: Int) {
        val modelMark = markList[position]

        var marks = modelMark.marks
        var semester = modelMark.semester
        var subject_id = modelMark.subject_id
        var test_type = modelMark.test_type

        holder.content.text = "$marks in $semester sem"

        when (subject_id) {
            "1" -> {
                holder.subjectName.text = "Java"+" | "+test_type
                holder.subjectIv.setImageResource(R.drawable.java_icon)
            }
            "2" -> {
                holder.subjectName.text = "DS"+" | "+test_type
                holder.subjectIv.setImageResource(R.drawable.ds_icon)
            }
            "3" -> {
                holder.subjectName.text = "AI & ES"+" | "+test_type
                holder.subjectIv.setImageResource(R.drawable.ai_icon)
            }
            "4" -> {
                holder.subjectName.text = "OS"+" | "+test_type
                holder.subjectIv.setImageResource(R.drawable.os_icon)
            }
            "5" -> {
                holder.subjectName.text = "BPM"+" | "+test_type
                holder.subjectIv.setImageResource(R.drawable.bpm_icon)
            }
            else -> "Unknown Subject"
        }
    }

    override fun getItemCount(): Int {
        return markList.size
    }

    inner class HolderMarks(itemView: View): RecyclerView.ViewHolder(itemView) {
        val subjectIv: ImageView = itemView.findViewById(R.id.subjectIv)
        val subjectName: TextView = itemView.findViewById(R.id.subjectName)
        val content: TextView = itemView.findViewById(R.id.content)
    }
}