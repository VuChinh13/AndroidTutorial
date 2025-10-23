package com.example.androidtutorial.features.paywall

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtutorial.databinding.ActivityPayWallMainBinding

class PayWallMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayWallMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayWallMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPaywallSale50Weekly.setOnClickListener {
            val intent = Intent(this, PaywallSale50WeeklyActivity::class.java)
            startActivity(intent)
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
}