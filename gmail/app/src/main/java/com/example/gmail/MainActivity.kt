package com.example.gmail

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmailAdapter(getEmails())
    }

    // sample data giong nhu gmail
    private fun getEmails(): List<Email> {
        return listOf(
            Email("E", "Edurila.com", "12:34 PM", 
                "$19 Only (First 10 spots) - Bestselling...", 
                "Are you looking to Learn Web Designin...", 
                0xFF4285F4.toInt(), true),
            Email("C", "Chris Abad", "11:22 AM", 
                "Help make Campaign Monitor better", 
                "Let us know your thoughts! No Images...", 
                0xFFFF6F00.toInt(), false),
            Email("T", "Tuto.com", "11:04 AM", 
                "8h de formation gratuite et les nouvea...", 
                "Photoshop, SEO, Blender, CSS, WordPre...", 
                0xFFA8DAB5.toInt(), false),
            Email("S", "support", "10:26 AM", 
                "Société Ovh : suivi de vos services - hp...", 
                "SAS OVH - http://www.ovh.com 2 rue K...", 
                0xFF9AA0A6.toInt(), false),
            Email("M", "Matt from Ionic", "9:30 AM", 
                "The New Ionic Creator Is Here!", 
                "Announcing the all-new Creator, build...", 
                0xFFA8DAB5.toInt(), false),
            Email("G", "Google Play", "9:15 AM",
                "Your order receipt from Google Play",
                "Thank you for your purchase. This ema...",
                0xFF4285F4.toInt(), false),
            Email("A", "Amazon", "8:45 AM",
                "Your package has been shipped",
                "Your order #123-4567890-1234567 has b...",
                0xFFFF6F00.toInt(), true),
            Email("F", "Facebook", "8:20 AM",
                "You have 5 new notifications",
                "John Doe and 4 others commented on yo...",
                0xFF4285F4.toInt(), false),
            Email("N", "Netflix", "7:55 AM",
                "New releases this week",
                "Check out the latest movies and series...",
                0xFFE50914.toInt(), false),
            Email("L", "LinkedIn", "7:30 AM",
                "You appeared in 12 searches this week",
                "People are looking at your profile. K...",
                0xFF0077B5.toInt(), false),
            Email("D", "Dribbble", "Yesterday",
                "Weekly inspiration digest",
                "The best shots from designers around...",
                0xFFEA4C89.toInt(), true),
            Email("S", "Spotify", "Yesterday",
                "Your Discover Weekly is ready",
                "New music just for you. Listen now to...",
                0xFF1DB954.toInt(), false),
            Email("T", "Twitter", "Yesterday",
                "Your Twitter Highlights",
                "Here are the top tweets you might hav...",
                0xFF1DA1F2.toInt(), false),
            Email("Y", "YouTube", "2 days ago",
                "Recommended videos for you",
                "Based on your watch history, we think...",
                0xFFFF0000.toInt(), false),
            Email("M", "Medium", "2 days ago",
                "Top stories for you",
                "Here are today's top stories and arti...",
                0xFF00AB6C.toInt(), false)
        )
    }
}

// email data class
data class Email(
    val initial: String,
    val sender: String,
    val time: String,
    val subject: String,
    val preview: String,
    val avatarColor: Int,
    val hasAttachment: Boolean
)

// adapter cho recyclerview
class EmailAdapter(private val emails: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
    }

    override fun getItemCount() = emails.size

    // viewholder cho email item
    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarText: TextView = itemView.findViewById(R.id.avatarText)
        private val senderText: TextView = itemView.findViewById(R.id.senderText)
        private val timeText: TextView = itemView.findViewById(R.id.timeText)
        private val subjectText: TextView = itemView.findViewById(R.id.subjectText)
        private val previewText: TextView = itemView.findViewById(R.id.previewText)
        private val attachIcon: ImageView = itemView.findViewById(R.id.attachIcon)

        fun bind(email: Email) {
            // set avatar circle voi mau
            avatarText.text = email.initial
            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.OVAL
            drawable.setColor(email.avatarColor)
            avatarText.background = drawable

            senderText.text = email.sender
            timeText.text = email.time
            subjectText.text = email.subject
            previewText.text = email.preview
            
            // hien thi icon attach neu co
            attachIcon.visibility = if (email.hasAttachment) View.VISIBLE else View.GONE
        }
    }
}
