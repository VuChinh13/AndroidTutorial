package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
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
        enableEdgeToEdge()
        binding = ActivityPaywallSale30YearlyBinding.inflate(layoutInflater)
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
        imgBackgroundContent.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE

        //Thu hẹp khoảng cách giữa btnClaimOffer và imgBackground
        ConstraintSet().apply {
            clone(binding.main)
            connect(
                R.id.btnClaimOffer,
                ConstraintSet.TOP,
                R.id.imgBackground,
                ConstraintSet.BOTTOM
            )
            applyTo(binding.main)
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