package com.example.androidtutorial.features.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * PermissionHelper:
 * - Tách riêng logic xin quyền và xử lý kết quả
 * - Dễ dùng lại trong nhiều Activity hoặc Fragment
 */
class PermissionHelper(private val activity: AppCompatActivity) {

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Chỉ thông báo nếu người dùng đã phản hồi thật (không phải lần đầu)
            if (isGranted) {
                Toast.makeText(activity, "Quyền đã được cấp!", Toast.LENGTH_SHORT).show()
            } else if (shouldShowRationale()) {
                Toast.makeText(activity, "Quyền bị từ chối!", Toast.LENGTH_SHORT).show()
            }
        }

    fun checkAndRequestPermission() {
        val permission = getPermissionType()

        when {
            isPermissionGranted(permission) -> {
                Toast.makeText(activity, "Quyền đã được cấp sẵn!", Toast.LENGTH_SHORT).show()
            }

            else -> {
                // Chưa có quyền → xin
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun getPermissionType(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRationale(): Boolean {
        val permission = getPermissionType()
        return activity.shouldShowRequestPermissionRationale(permission)
    }
}