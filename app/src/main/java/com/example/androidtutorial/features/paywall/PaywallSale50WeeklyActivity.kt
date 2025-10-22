package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.databinding.ActivityPaywallSale50WeeklyBinding
import com.facebook.shimmer.Shimmer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale50WeeklyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallSale50WeeklyBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPaywallSale50WeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupShimmer()
        setupBottomSheet()
        setupListeners()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        val screenHeight = resources.displayMetrics.heightPixels
        bottomSheetBehavior.peekHeight = (screenHeight * 0.35).toInt()
        bottomSheetBehavior.isHideable = false
    }

    private fun setupShimmer() = with(binding) {
        shimmerBlock.setShimmer(
            Shimmer.AlphaHighlightBuilder()
                .setDuration(1200)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(1f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setTilt(0f)
                .build()
        )
    }

    private fun setupListeners() = with(binding) {
        btnClose.setOnClickListener { finish() }

        btnClaimOffer.setOnClickListener {
            showLoadingState()
            simulateLoading()
        }
    }

    private fun showLoadingState() = with(binding) {
        btnClaimOffer.apply {
            text = ""
            isEnabled = false
        }
        pbClaimOffer.visibility = View.VISIBLE
        blockContent.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        shimmerBlock.apply {
            visibility = View.VISIBLE
            startShimmer()
        }
    }

    private fun simulateLoading() {
        lifecycleScope.launch {
            delay(2500)
            with(binding.shimmerBlock) { stopShimmer() }
            showErrorState()
        }
    }

    private fun showErrorState() = with(binding) {
        shimmerBlock.visibility = View.GONE
        btnClaimOffer.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
        txtTryAgain.visibility = View.VISIBLE
    }
}