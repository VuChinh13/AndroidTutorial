package com.eco.musicplayer.audioplayer.music.launchmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.databinding.ActivityMainBinding

/**
 * Demo về 4 loại Launch Mode Android:
 *
 * 1. standard (mặc định):
 *    - Mỗi lần gọi startActivity() sẽ tạo một instance mới của Activity, dù nó đã tồn tại.
 *    - Instance mới luôn được đặt lên đầu stack.
 *    - Phù hợp với các màn hình có thể được mở nhiều lần độc lập.
 *
 * 2. singleTop:
 *    - Nếu Activity đã ở trên cùng của back stack, Android sẽ tái sử dụng instance hiện tại (không tạo mới).
 *    - Nếu không ở trên cùng, sẽ tạo mới như `standard`.
 *    - onNewIntent() sẽ được gọi nếu Activity được tái sử dụng.
 *
 * 3. singleTask:
 *    - Nếu Activity đã tồn tại trong task, Android sẽ đưa instance đó lên top và hủy các activity phía trên nó.
 *    - Nếu chưa tồn tại, sẽ tạo mới.
 *    - onNewIntent() sẽ được gọi khi tái sử dụng.
 *    - Dùng trong các màn hình mang tính "gốc" hoặc trung tâm, ví dụ: màn hình Home.
 *
 * 4. **singleInstance**:
 *    - Giống `singleTask`, nhưng activity sẽ chạy trong một task riêng biệt.
 *    - Không chia sẻ task với các activity khác.
 *    - Dùng trong trường hợp đặc biệt như màn hình cuộc gọi, voice assistant,...
 *
 * Note:
 * - Các launch mode được cấu hình trong AndroidManifest.xml
 *
 * File MainActivity – điểm bắt đầu của ứng dụng, dùng để test các loại launch mode khác nhau.
 */
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