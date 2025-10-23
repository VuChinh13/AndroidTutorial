package com.example.androidtutorial.features.paywall

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidtutorial.R
import com.example.androidtutorial.databinding.ActivityPaywallUnlockFeatureBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallUnlockFeatureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallUnlockFeatureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallUnlockFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        simulateLoading()
        setupListeners()
    }

    fun setupUI() {
        with(binding) {
            textDescription.visibility = View.INVISIBLE
            btnTryFree.text = ""
            btnTryFree.isEnabled = false
            pbTryToFree.visibility = View.VISIBLE
        }
    }

    private fun simulateLoading() = lifecycleScope.launch {
        delay(2500)
        with(binding) {
            btnTryFree.setBackgroundResource(R.drawable.bg_button_try_to_free_unlock_feature)
            btnTryFree.text = "Try to free"
            btnTryFree.isEnabled = true
            pbTryToFree.visibility = View.GONE
            textDescription.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        with(binding) {
            btnTryFree.setOnClickListener {
                if (btnTryFree.text == "Try to free") {
                    btnTryFree.text = "Continue"
                    textDescription.text = "$14.99/year\nAuto renew, cancel anytime"
                }
            }
        }
    }

}