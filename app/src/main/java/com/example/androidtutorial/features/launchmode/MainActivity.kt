package com.example.androidtutorial.features.launchmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
    }

    private fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, SingleInstanceActivity::class.java))
        }

    }
}