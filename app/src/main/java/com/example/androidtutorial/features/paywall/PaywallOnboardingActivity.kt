package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.R
import com.example.androidtutorial.databinding.ActivityPaywallOnbroadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallOnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallOnbroadingBinding
    private var isYearlySelected = true      // Mặc định Yearly
    private var isFreeTrialEnabled = true    // Mặc định bật Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallOnbroadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() = with(binding) {
        updateUI()
        // Sự kiện loading
        loading()
        simulateLoading()

        // Xử lý khi đổi switch
        switchFreeTrial.setOnCheckedChangeListener { _, isChecked ->
            isFreeTrialEnabled = isChecked
            updateUI()
        }

        // Xử lý khi chọn Yearly
        btnYearly.setOnClickListener {
            isYearlySelected = true
            updateUI()
        }

        // Xử lý khi chọn Weekly
        btnWeekly.setOnClickListener {
            isYearlySelected = false
            updateUI()
        }

        // Đóng màn hình
        btnClose.setOnClickListener { finish() }

        with(binding) {
            btnTryFree.setOnClickListener {
                if (binding.btnTryFree.text == "CONTINUE") {

                }
            }
        }
    }

    private fun loading() = with(binding) {
        pbTryToFree.visibility = View.VISIBLE
        txtDescription.visibility = View.INVISIBLE
        btnTryFree.isEnabled = false
        btnTryFree.text = ""
    }

    private fun updateUI() = with(binding) {
        // Cập nhật trạng thái nút chọn gói
        if (isYearlySelected) {
            styleSelectedButton(btnYearly)
            styleUnselectedButton(btnWeekly)
        } else {
            styleSelectedButton(btnWeekly)
            styleUnselectedButton(btnYearly)
        }

        // Cập nhật nội dung mô tả
        txtDescription.text = getDescriptionText()
    }

    private fun getDescriptionText(): String {
        return if (!isFreeTrialEnabled) {
            binding.btnTryFree.text = "CONTINUE"
            if (isYearlySelected)
                getString(R.string.description_onboarding_yearly_trial)
            else
                getString(R.string.description_onboarding_weekly_trial)
        } else {
            binding.btnTryFree.text = "TRY FOR FREE"
            if (isYearlySelected)
                getString(R.string.description_onboarding_yearly)
            else
                getString(R.string.description_onboarding_weekly)
        }
    }

    private fun simulateLoading() = lifecycleScope.launch {
        delay(2500)
        with(binding) {
            btnTryFree.setBackgroundResource(R.drawable.bg_button_try_to_free)
            btnTryFree.text = "TRY TO FREE"
            pbTryToFree.visibility = View.GONE
            txtDescription.visibility = View.VISIBLE
        }
    }

    private fun styleSelectedButton(button: RadioButton) {
        button.setBackgroundResource(R.drawable.bg_radio_group2)
        button.setTextColor(ContextCompat.getColor(this, R.color.primary_dark_blue))
    }

    private fun styleUnselectedButton(button: RadioButton) {
        button.setBackgroundResource(R.drawable.bg_radio_group)
        button.setTextColor(ContextCompat.getColor(this, R.color.background_light_blue))
    }
}
