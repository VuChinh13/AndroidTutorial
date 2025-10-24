package com.eco.musicplayer.audioplayer.music.paywall

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eco.musicplayer.R
import com.eco.musicplayer.databinding.ActivityPaywallUnlockFeatureBinding
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