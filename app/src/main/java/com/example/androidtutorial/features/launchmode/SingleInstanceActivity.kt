package com.example.androidtutorial.features.launchmode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivitySingleInstanceBinding

class SingleInstanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleInstanceBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleInstanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerClickEvent()
        binding.textView.text =
            "SingleInstanceActivity Task Id: ${this.taskId}\nInstance Id: ${
                System.identityHashCode(
                    this
                )
            }"
    }

    fun registerClickEvent() {
        binding.btnStartStandardActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, StandardActivity::class.java))
        }
        binding.btnStartSingleTopActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleTopActivity::class.java))
        }
        binding.btnStartSingleTaskActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleTaskActivity::class.java))
        }
        binding.btnStartSingleInstanceActivity.setOnClickListener {
            startActivity(Intent(this@SingleInstanceActivity, SingleInstanceActivity::class.java))
        }
    }
}