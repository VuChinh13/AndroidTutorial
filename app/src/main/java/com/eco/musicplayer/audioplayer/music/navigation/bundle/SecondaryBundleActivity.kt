package com.eco.musicplayer.audioplayer.music.navigation.bundle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.databinding.ActivitySecondaryBundleBinding

/**
 * SECONDARY ACTIVITY — NHẬN BUNDLE
 *
 * - Lấy Bundle từ intent.extras.
 * - Dùng BundleCompat.getParcelable(...) khi đọc Parcelable
 * - Nên null-check và có giá trị mặc định khi thiếu key.
 */
class SecondaryBundleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondaryBundleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondaryBundleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy bundle từ Intent (nếu null thì tạo bundle rỗng)
        val receivedBundle = intent.extras ?: Bundle()

        receivedBundle.run {
            val receivedCount = getInt("extra_count", -1)
            val receivedMessage = getString("extra_message").orEmpty()
            val receivedTags = getStringArrayList("extra_tags") ?: arrayListOf()

            val optionsBundle = getBundle("extra_options")
            val shouldShowDialog = optionsBundle?.getBoolean("showDialog") ?: false
            val accentColor = optionsBundle?.getString("accent").orEmpty()

            binding.tvContent.text = buildString {
                appendLine("SecondaryBundleActivity")
                appendLine("Count: $receivedCount")
                appendLine("Message: $receivedMessage")
                appendLine("Tags: ${receivedTags.joinToString()}")
                appendLine("Options.showDialog: $shouldShowDialog")
                appendLine("Options.accent: $accentColor")
            }
        }
    }
}