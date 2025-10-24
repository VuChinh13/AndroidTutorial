package com.eco.musicplayer.audioplayer.music.navigation.activityresultlauncher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.databinding.ActivitySecondaryResultBinding

class SecondaryResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondaryResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondaryResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFirst.setOnClickListener {
            val intent = Intent(this, FirstResultActivity::class.java)
            intent.putExtra("result", binding.etContent.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}