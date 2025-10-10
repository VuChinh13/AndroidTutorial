package com.example.androidtutorial.features.launchmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivitySingleTopBinding

class SingleTopActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleTopBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleTopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.textView.text =
            "SingleTopActivity Task Id: ${this.taskId}\nInstance Id: ${System.identityHashCode(this)}"
    }

    fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@SingleTopActivity, SingleInstanceActivity::class.java))
        }
    }
}