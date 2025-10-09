package com.example.androidtutorial.features.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityLifecycleBinding

/**
 * ========================= VÒNG ĐỜI ACTIVITY (Android) =========================
 *
 * Tạo mới bình thường:
 *   onCreate() -> onStart() -> onResume() -> [Activity chạy foreground]
 *
 * Rời app (Home / Recent) rồi quay lại:
 *   Trạng thái:   onPause() -> onStop()
 *   Quay lại: onRestart() -> onStart() -> onResume()
 *
 * Xoay màn hình / thay đổi cấu hình (recreate):
 *   onPause() -> onSaveInstanceState() -> onStop() -> onDestroy()
 *   -> onCreate(savedInstanceState) -> onStart() -> onRestoreInstanceState() -> onResume()
 *
 * Người dùng thoát hẳn (Back) hoặc finish():
 *   onPause() -> onStop() -> onDestroy()
 *   (Không khôi phục lại, savedInstanceState = null ở lần mở sau)
 *
 * Hệ thống thiếu RAM, kill process khi Activity đang ở background:
 *   Có thể gọi onSaveInstanceState() trước rồi kill process (KHÔNG gọi onDestroy()).
 *   Khi người dùng quay lại: Activity được tạo mới, nhận savedInstanceState trong onCreate().
 *
 * Ghi chú:
 * - onCreate(): khởi tạo UI; có Bundle savedInstanceState để khôi phục trạng thái.
 * - onSaveInstanceState(): chỉ lưu dữ liệu NHẸ/TẠM để khôi phục UI; không dùng cho dữ liệu lớn/lâu dài.
 *
 */
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
