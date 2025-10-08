package com.example.androidtutorial.features.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityLifecycleBinding

class LifecycleActivity : AppCompatActivity() {

    private val tag = "LifecycleActivity"
    private lateinit var binding: ActivityLifecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(tag, "onCreate")

        val savedName = savedInstanceState?.getString("name_key")
        if (savedName != null) {
            binding.editName.setText(savedName)
            Log.d(tag, "Khôi phục dữ liệu từ savedInstanceState: $savedName")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }

    // Lưu dữ liệu trước khi Activity bị huỷ (ví dụ: xoay màn hình)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val name = binding.editName.text.toString()
        outState.putString("name_key", name)
        Log.d(tag, "onSaveInstanceState - Lưu dữ liệu: $name")
    }
}
