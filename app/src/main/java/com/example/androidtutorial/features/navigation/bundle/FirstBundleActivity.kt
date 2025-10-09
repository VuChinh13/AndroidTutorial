package com.example.androidtutorial.features.navigation.bundle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.androidtutorial.databinding.ActivityFirstBundleBinding

/**
 * FIRST ACTIVITY — GỬI BUNDLE SANG SECONDARY ACTIVITY
 *
 * - Gom dữ liệu vào 1 Bundle (int, string, list, bundle lồng).
 * - Truyền qua Intent bằng putExtras().
 */
class FirstBundleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBundleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBundleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnSecondary.setOnClickListener {
                val itemCount = 42
                val welcomeMessage = "Hello from First!"
                val topicTags = arrayListOf("android", "kotlin")

                val optionsBundle = bundleOf(
                    "showDialog" to true,
                    "accent" to "#6200EE"
                )

                val extrasBundle = bundleOf(
                    "extra_count" to itemCount,
                    "extra_message" to welcomeMessage,
                    "extra_tags" to topicTags,
                    "extra_options" to optionsBundle
                )

                val secondaryIntent = Intent(this@FirstBundleActivity, SecondaryBundleActivity::class.java).apply {
                    putExtras(extrasBundle)
                }
                startActivity(secondaryIntent)
            }
        }
    }
}
