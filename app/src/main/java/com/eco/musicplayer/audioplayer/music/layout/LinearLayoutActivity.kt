package com.eco.musicplayer.audioplayer.music.layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.R

/**
 * Demo sử dụng LinearLayout - tương thích với nhiều loại màn hình khác nhau
 */
class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)
    }
}