package com.example.androidtutorial.features.navigation.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityFirstBinding

/**
 * FIRST ACTIVITY — DEMO EXPLICIT INTENT + EXTRAS
 *
 * Mục tiêu:
 *  - Điều hướng sang SecondaryActivity bằng Explicit Intent.
 *  - Truyền nhiều kiểu dữ liệu qua Intent extras:
 *
 * Truyền Object qua Intent/Bundle
 * - @Parcelize tự sinh code Parcelable, giúp truyền object qua Intent/Bundle.
 * - Chỉ nên chứa dữ liệu nhẹ
 *
 */
class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSecondary.setOnClickListener {
            // Truyền những kiểu dữ liệu cơ bản
            val user = User(id = 11, name = "Vu Van Chinh")
            val count = 42
            val message = "Hello from First!"
            val tags = arrayListOf("android", "kotlin")

            val intent = Intent(this, SecondaryActivity::class.java).apply {
                putExtra("extra_user", user)
                putExtra("extra_count", count)
                putExtra("extra_message", message)
                putStringArrayListExtra("extra_tags", tags)
            }
            startActivity(intent)
        }
    }
}