package com.eco.musicplayer.audioplayer.music.navigation.activityresultlauncher

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.databinding.ActivityFirstResultBinding

/**
 *
 * Mục đích:
 *  - Demo cách sử dụng ActivityResultLauncher và registerForActivityResult()
 *    để nhận dữ liệu trả về từ một Activity khác (thay thế cho hàm `startActivityForResult()` đã bị deprecated).
 *
 * Luồng hoạt động:
 *  - Bấm nút ở màn FirstResultActivity và chuyển sang màn SecondaryResultActivity
 *  - Tại màn SecondaryResultActivity nhập nội dung và bấm nút trở về màn FirstResultActivity
 *  - Kết quả được nhận trong callback của `registerForActivityResult()` và hiển thị trên `tvResult`.
 *
 */
class FirstResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstResultBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    binding.tvResult.text = data?.getStringExtra("result")
                }
            }

        binding.btnSecondary.setOnClickListener {
            val intent = Intent(this, SecondaryResultActivity::class.java)
            launcher.launch(intent)
        }
    }
}