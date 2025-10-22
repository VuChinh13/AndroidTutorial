package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.R
import com.example.androidtutorial.databinding.ActivityPaywallSale30YearlyBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale30YearlyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallSale30YearlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallSale30YearlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        btnClose.setOnClickListener { finish() }

        showLoadingState1()
        simulateLoading1()

        btnTryAgain.setOnClickListener {
            showLoadingState2()
            simulateLoading2()
        }
    }

    private fun showLoadingState1() = with(binding) {
        btnClaimOffer.apply {
            text = ""
            isEnabled = false
        }
        pbClaimOffer.visibility = View.VISIBLE
        btnTryAgain.visibility = View.GONE
    }

    private fun showLoadingState2() = with(binding) {
        btnClaimOffer.visibility = View.VISIBLE
        btnClaimOffer.isEnabled = false
        pbClaimOffer.visibility = View.VISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
    }

    private fun simulateLoading1() {
        lifecycleScope.launch {
            delay(2500)
            showErrorState()
        }
    }

    private fun simulateLoading2() {
        lifecycleScope.launch {
            delay(2500)
            showSucessState()
        }
    }

    private fun showErrorState() = with(binding) {
        btnClaimOffer.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
        btnTryAgain.visibility = View.VISIBLE
    }

    private fun showSucessState() = with(binding) {
        imgBackgroundContent.visibility = View.VISIBLE
        txtPriceDescription.visibility = View.VISIBLE
        ConstraintSet().apply {
            clone(binding.main)
            connect(
                R.id.btnClaimOffer,
                ConstraintSet.TOP,
                R.id.imgBackgroundContent,
                ConstraintSet.BOTTOM
            )
            applyTo(binding.main)
        }
        txtPriceDescription.visibility = View.VISIBLE
        btnClaimOffer.text = "CLAIM OFFER"
        txtPriceDescription.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
    }
}