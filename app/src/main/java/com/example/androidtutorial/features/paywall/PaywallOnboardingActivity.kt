package com.example.androidtutorial.features.paywall

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.R
import com.example.androidtutorial.databinding.ActivityPaywallOnbroadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallOnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallOnbroadingBinding
    private var isYearlySelected = true
    private var isFreeTrialEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallOnbroadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() = with(binding) {
        updateUI()
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

        btnClose.setOnClickListener { finish() }

        with(binding) {
            btnTryFree.setOnClickListener {
                if (btnTryFree.text == "CONTINUE") {
                    groupSwitch.visibility = View.INVISIBLE
                    ConstraintSet().apply {
                        clone(main)
                        connect(
                            R.id.btnTryFree,
                            ConstraintSet.TOP,
                            R.id.txtDescription,
                            ConstraintSet.BOTTOM
                        )
                        applyTo(binding.main)
                    }
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

        txtDescription.text = getDescriptionText()
    }

    @SuppressLint("SetTextI18n")
    private fun getDescriptionText(): String {
        return if (!isFreeTrialEnabled) {
            binding.btnTryFree.text = "CONTINUE"
            if (isYearlySelected)
                getString(R.string.description_onboarding_yearly_trial)
            else
                getString(R.string.description_onboarding_weekly_trial)
        } else {
            binding.btnTryFree.setText(R.string.try_for_free)
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
            btnTryFree.setText(R.string.try_for_free)
            btnTryFree.isEnabled = true
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
