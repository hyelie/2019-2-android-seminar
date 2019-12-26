package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post.*

class Post : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        PostTitle.text = intent.getStringExtra("content_title")
        PostWriter.text = intent.getStringExtra("content_writer")
        PostTime.text = intent.getStringExtra("content_time")
        PostContent.text = intent.getStringExtra("content_content")
    }
}
