package com.example.androidtutorial.features.navigation.intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivitySecondaryBinding

/**
 * Chú ý:
 * - Từ API 33, getParcelableExtra(String) bị deprecate.
 */
@Suppress("DEPRECATION")
class SecondaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cần lưu ý đoạn này tùy vào API
        with(intent) {
            val user: User? =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    getParcelableExtra("extra_user", User::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    getParcelableExtra("extra_user")
                }
            val count = getIntExtra("extra_count", -1)
            val message = getStringExtra("extra_message") ?: ""
            val tags = getStringArrayListExtra("extra_tags") ?: arrayListOf()

            binding.tv.text = buildString {
                appendLine("SecondaryActivity")
                appendLine("User: $user")
                appendLine("Count: $count")
                appendLine("Message: $message")
                appendLine("Tags: ${tags.joinToString(", ")}")
            }
        }
    }
}

