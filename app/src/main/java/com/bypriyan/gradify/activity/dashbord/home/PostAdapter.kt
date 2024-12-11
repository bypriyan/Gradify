package com.bypriyan.gradify.activity.dashbord.home

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.gradify.adapter.AdapterPostImage
import com.bypriyan.gradify.apiResponse.Data
import com.bypriyan.gradify.databinding.RowPostBinding
import com.bypriyan.sharemarketcourseinhindi.adapter.AdapterOnBordingScreen
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Data>()

    fun updatePosts(newPosts: List<Data>) {
        val start = posts.size
        posts.addAll(newPosts)
        notifyItemRangeInserted(start, newPosts.size)
    }

    inner class PostViewHolder(private val binding: RowPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Data) {
            binding.apply {
                userNameTv.text = post.teacher_name
                timeTv.text = getTimeAgoForAllApiLevels(post.created_at)
                emailTv.text = post.teacher_email
                contentTv.text = post.content
                likeCount.text = post.like_count.toString()

                profileImage.load(Constants.KEY_BASE_URL + "profileImg/" + post.teacher_profile_image) {
                    crossfade(true)
                }

                postImgLayout.visibility = if (post.images.isNotEmpty()) View.VISIBLE else View.GONE

                // Handle visibility and setup for ViewPager2
                postImgLayout.visibility = if (post.images.isNotEmpty()) View.VISIBLE else View.GONE
                wormDotsIndicator.visibility = if (post.images.size>1) View.VISIBLE else View.GONE

                if (post.images.isNotEmpty()) {
                    val imageAdapter = AdapterPostImage(post.images)
                    viewPager2.adapter = imageAdapter
                    // Set up dots indicator
                    wormDotsIndicator.attachTo(viewPager2)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RowPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    private fun getTimeAgoForAllApiLevels(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            val pastTime = dateFormat.parse(dateTime)?.time ?: return "Invalid time"
            val diff = System.currentTimeMillis() - pastTime
            when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
                diff < TimeUnit.HOURS.toMillis(1) -> ".${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"
                diff < TimeUnit.DAYS.toMillis(1) -> ".${TimeUnit.MILLISECONDS.toHours(diff)}h ago"
                diff < TimeUnit.DAYS.toMillis(7) -> ".${TimeUnit.MILLISECONDS.toDays(diff)}d ago"
                diff < TimeUnit.DAYS.toMillis(30) -> ".${TimeUnit.MILLISECONDS.toDays(diff) / 7}w ago"
                diff < TimeUnit.DAYS.toMillis(365) -> ".${TimeUnit.MILLISECONDS.toDays(diff) / 30}mo ago"
                else -> "${TimeUnit.MILLISECONDS.toDays(diff) / 365}y ago"
            }
        } catch (e: Exception) {
            "Unknown time"
        }
    }
}
