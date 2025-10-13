package com.example.androidtutorial.features.permissions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityPermissionBinding

/**
 * PermissionActivity
 * Mục đích:
 *  - Xin quyền truy cập vào bộ nhớ (đọc file âm thanh)
 *  - Sử dụng API xin quyền kiểu mới (ActivityResultContracts.RequestPermission)
 *  - Hiển thị thông báo cho người dùng sau khi cấp hoặc từ chối quyền
 *
 * Cách hoạt động:
 *  - Khi mở Activity, app kiểm tra xem quyền đã được cấp chưa
 *  - Nếu chưa có, hệ thống sẽ hiển thị hộp thoại xin quyền
 *  - Kết quả (được cấp / bị từ chối) sẽ hiển thị bằng Toast
 */
class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding
    private lateinit var permissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionHelper = PermissionHelper(this)
        permissionHelper.checkAndRequestPermission()
    }
}

