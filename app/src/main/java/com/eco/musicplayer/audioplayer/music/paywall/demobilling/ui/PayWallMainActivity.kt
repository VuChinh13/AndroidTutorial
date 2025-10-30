package com.eco.musicplayer.audioplayer.music.paywall.demobilling.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eco.musicplayer.audioplayer.music.paywall.PayWallFocusBottomSheetActivity
import com.eco.musicplayer.audioplayer.music.paywall.PayWallFocusFullScreenActivity
import com.eco.musicplayer.audioplayer.music.paywall.PaywallOnboardingActivity
import com.eco.musicplayer.audioplayer.music.paywall.PaywallSale30WeeklyActivity
import com.eco.musicplayer.audioplayer.music.paywall.PaywallSale30YearlyActivity
import com.eco.musicplayer.audioplayer.music.paywall.PaywallUnlockFeatureActivity
import com.eco.musicplayer.audioplayer.music.paywall.demobilling.data.repository.PaywallRepository
import com.eco.musicplayer.databinding.ActivityPayWallMainBinding

class PayWallMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayWallMainBinding
    private val repository = PaywallRepository()
    private var productId = ""
    private var offerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayWallMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()

        // Gọi repo -> nhận dữ liệu qua callback
        repository.getPaywallConfig { config ->
            val uiType = config.uiType?.toIntOrNull() ?: 1
            val selected = config.products?.firstOrNull()

            productId = selected?.productId ?: ""
            offerId = selected?.offerId ?: ""

            navigateByUiType(uiType, productId, offerId)
        }
    }

    private fun setListener() {
        binding.btnPaywallSale50Weekly.setOnClickListener {
            openPaywallSale50Weekly(productId, offerId)
        }

        binding.btnPaywallSale30Weekly.setOnClickListener {
            val intent = Intent(this, PaywallSale30WeeklyActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaywallSale30Yearly.setOnClickListener {
            val intent = Intent(this, PaywallSale30YearlyActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaywallOnboarding.setOnClickListener {
            val intent = Intent(this, PaywallOnboardingActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaywallUnlockFeature.setOnClickListener {
            val intent = Intent(this, PaywallUnlockFeatureActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaywallFocusBottomSheet.setOnClickListener {
            val intent = Intent(this, PayWallFocusBottomSheetActivity::class.java)
            startActivity(intent)
        }

        binding.btnPaywallFocusFullScreen.setOnClickListener {
            val intent = Intent(this, PayWallFocusFullScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateByUiType(uiType: Int, productId: String, offerId: String) {
        when (uiType) {
            1 -> {
                openPaywallSale50Weekly(productId, offerId)
            }

            else -> {
                android.util.Log.w("PayWallMainActivity", " Unknown uiType=$uiType")
            }
        }
    }

    private fun openPaywallSale50Weekly(productId: String, offerId: String) {
        val intent = Intent(this, PaywallSale50WeeklyActivity::class.java).apply {
            putExtra("productId", productId)
            putExtra("offerId", offerId)
        }
        startActivity(intent)
    }
}
