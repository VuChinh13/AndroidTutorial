package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.databinding.ActivityPaywallSale30WeeklyBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale30WeeklyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallSale30WeeklyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPaywallSale30WeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
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
        txtPriceDescription.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE

    }

    private fun simulateLoading() {
        lifecycleScope.launch {
            delay(2500)
            showErrorState()
        }
    }

    private fun showErrorState() = with(binding) {
        btnClaimOffer.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
        txtTryAgain.visibility = View.VISIBLE
    }
}