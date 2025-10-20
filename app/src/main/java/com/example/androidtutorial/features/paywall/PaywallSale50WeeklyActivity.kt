package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.databinding.ActivityPaywallSale50WeeklyBinding
import com.facebook.shimmer.Shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale50WeeklyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallSale50WeeklyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPaywallSale50WeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupShimmer()
        setupListeners()
    }

    private fun setupShimmer() {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1200)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(1f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setTilt(0f)
            .build()

        binding.shimmerBlock.setShimmer(shimmer)
    }

    private fun setupListeners() {
        binding.btnClose.setOnClickListener { finish() }

        binding.btnClaimOffer.setOnClickListener {
            showLoadingState()
            simulateLoading()
        }
    }

    private fun showLoadingState() {
        binding.blockContent.visibility = View.INVISIBLE
        binding.shimmerBlock.visibility = View.VISIBLE
        binding.shimmerBlock.startShimmer()
    }

    private fun simulateLoading() {
        lifecycleScope.launch {
            delay(2500)
            binding.shimmerBlock.stopShimmer()
            showErrorState()
        }
    }

    private fun showErrorState() {
        binding.shimmerBlock.visibility = View.GONE
        binding.btnClaimOffer.visibility = View.INVISIBLE
        binding.txtPriceDescription.visibility = View.INVISIBLE
        binding.txtTileError.visibility = View.VISIBLE
        binding.txtTryAgain.visibility = View.VISIBLE
    }
}
