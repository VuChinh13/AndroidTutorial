package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.R
import com.example.androidtutorial.databinding.ActivityPayWallFocusBottomSheetBinding
import com.example.androidtutorial.databinding.ActivityPayWallFocusFullScreenBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PayWallFocusFullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayWallFocusFullScreenBinding
    private var isFocusYearlyPlan = true
    private var isEnableButtonContinue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayWallFocusFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        simulateLoading()
        setupListeners()
    }

    fun setupUI() {
        with(binding) {
            btnTryToFree.isEnabled = false
        }
    }

    fun setupListeners() {
        with(binding) {
            btnClose.setOnClickListener {
                finish()
            }

            btnYearlyPlan.setOnClickListener {
                if (!isFocusYearlyPlan) {
                    isFocusYearlyPlan = true
                    btnYearlyPlan.setBackgroundResource(R.drawable.bg_button_yearly_plan_full_screen)
                    txtTitle92.setBackgroundResource(R.drawable.bg_title_save92)
                    if (isEnableButtonContinue) {
                        txtPriceDescription.setText(R.string.description_price_yearly_continue)
                    } else txtPriceDescription.setText(R.string.description_price_yearly_plan)
                    btnWeeklyPlan.setBackgroundResource(R.drawable.bg_button_yearly_plan2)
                }
            }

            btnWeeklyPlan.setOnClickListener {
                if (isFocusYearlyPlan) {
                    isFocusYearlyPlan = false
                    btnYearlyPlan.setBackgroundResource(R.drawable.bg_button_yearly_plan2)
                    txtTitle92.setBackgroundResource(R.drawable.bg_title_save92_2)
                    if (isEnableButtonContinue) {
                        txtPriceDescription.setText(R.string.description_price_weekly_continue)
                    } else txtPriceDescription.setText(R.string.description_price_weekly_plan)
                    btnWeeklyPlan.setBackgroundResource(R.drawable.bg_button_yearly_plan_full_screen)
                }
            }

            btnTryToFree.setOnClickListener {
                if (btnTryToFree.equals(R.string.try_for_free)) {
                    isEnableButtonContinue = true
                    btnTryToFree.setText(R.string.continue_1)
                    txt3Day.setText(R.string.cancel_anytime)
                    if (isFocusYearlyPlan) txtPriceDescription.setText(R.string.description_price_yearly_continue)
                    else txtPriceDescription.setText(R.string.description_price_weekly_continue)
                }
            }
        }
    }

    fun simulateLoading() {
        lifecycleScope.launch {
            delay(2500)
            with(binding) {
                pbWeeklyPlan.visibility = View.GONE
                pbYearlyPlan.visibility = View.GONE
                pbTryToFree.visibility = View.GONE
                btnTryToFree.setBackgroundResource(R.drawable.bg_button_try_to_free_bottom_sheet)
                btnTryToFree.isEnabled = true
                btnTryToFree.setText(R.string.try_for_free)
                txtDescriptionPriceWeekly.visibility = View.VISIBLE
                txtDescriptionPriceYearly.visibility = View.VISIBLE
                txtWeek.visibility = View.VISIBLE
                txtYear.visibility = View.VISIBLE
                txtPriceDescription.visibility = View.VISIBLE
            }
        }
    }
}