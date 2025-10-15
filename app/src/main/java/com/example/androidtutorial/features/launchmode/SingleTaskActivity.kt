package com.example.androidtutorial.features.launchmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivitySingleTaskBinding

class SingleTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleTaskBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.txtTitle.text =
            "SingleTaskActivity Task Id: ${this.taskId}\nInstance Id: ${System.identityHashCode(this)}"
    }

    fun registerClickEvent() {
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleTaskActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@SingleTaskActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleTaskActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@SingleTaskActivity, SingleInstanceActivity::class.java))
        }
    }
}