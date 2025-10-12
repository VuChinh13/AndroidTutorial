package com.example.androidtutorial.features.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    // Tạo launcher xin quyền kiểu mới
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            handlePermissionResult(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAndRequestPermission()
    }

    /**
     * Hàm kiểm tra quyền:
     * - Nếu đã có quyền → thông báo “đã được cấp”
     * - Nếu chưa có → xin quyền qua launcher
     */
    private fun checkAndRequestPermission() {
        val permissionToRequest = getPermissionType()

        when {
            isPermissionGranted(permissionToRequest) -> {
                showToast("Quyền đã được cấp sẵn!")
            }

            shouldShowRequestPermissionRationale(permissionToRequest) -> {
                showToast("Ứng dụng cần quyền để truy cập vào bộ nhớ!")
                requestPermissionLauncher.launch(permissionToRequest)
            }

            else -> {
                requestPermissionLauncher.launch(permissionToRequest)
            }
        }
    }

    /**
     * Hàm xác định loại quyền cần xin (tùy phiên bản Android)
     */
    private fun getPermissionType(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    /**
     * Hàm kiểm tra xem quyền đã được cấp chưa
     */
    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Hàm xử lý kết quả khi người dùng chọn Allow hoặc Deny
     */
    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            showToast("Quyền đã được cấp!")
        } else {
            showToast("Quyền bị từ chối!")
        }
    }

    /**
     * Hàm hiển thị thông báo
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
