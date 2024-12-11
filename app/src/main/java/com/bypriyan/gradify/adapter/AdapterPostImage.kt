package com.bypriyan.gradify.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.bumptech.glide.Glide
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.gradify.R
import com.bypriyan.sharemarketcourseinhindi.adapter.AdapterOnBordingScreen
import com.bypriyan.sharemarketcourseinhindi.model.ModelOnBordingScreen

class AdapterPostImage (val elementList: List<String>) :
    RecyclerView.Adapter<AdapterPostImage.HolderPostImage>() {
        lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPostImage {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_post_image, parent, false)
        context = parent.context
        return HolderPostImage(view)
    }


    override fun onBindViewHolder(holder: HolderPostImage, position: Int) {
        var url = elementList[position]
        Log.d("pImg", "onBindViewHolder: ${Constants.KEY_BASE_URL+url}")
//        holder.postImg.load(Constants.KEY_BASE_URL+url) {
//            crossfade(true)
//        }
        Glide.with(context)
            .load(Constants.KEY_BASE_URL+url)
            .placeholder(R.drawable.app_logo) // Replace with your placeholder image
            .into(holder.postImg)
    }


    override fun getItemCount(): Int {
        return elementList.size
    }

    inner class HolderPostImage(itemView: View) : RecyclerView.ViewHolder(itemView){
        val postImg: ImageView = itemView.findViewById(R.id.postImg)
    }

}