package com.eco.musicplayer.audioplayer.music.paywall

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eco.musicplayer.R
import com.eco.musicplayer.databinding.ActivityPaywallSale50WeeklyBinding
import com.facebook.shimmer.Shimmer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale50WeeklyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallSale50WeeklyBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>

    private lateinit var billingManager: BillingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallSale50WeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()

        billingManager = BillingManager(this) { purchase ->
            Toast.makeText(this, "Mua thành công: ${purchase.products}", Toast.LENGTH_SHORT).show()
        }

        billingManager.startConnection {
            // Nhận dữ liệu sau khi query xong
            billingManager.setOnProductLoadedListener { productDetails ->
                // Lấy offer intro-price
                val introOffer = productDetails.subscriptionOfferDetails?.find { it.offerId == "intro-price" }

                if (introOffer != null) {
                    val phase = introOffer.pricingPhases.pricingPhaseList.first()
                    val price = phase.formattedPrice

                    // ✅ Hiển thị giá ưu đãi lên UI
                    binding.txtDiscount.text = getString(R.string.discount_price, price)
                    Log.d("Checkloi",price)
                } else {
                    binding.txtDiscount.text = "Không tìm thấy ưu đãi intro-price"
                }
            }

            // Nhấn nút sẽ query
            binding.btnClaimOffer.setOnClickListener {
                billingManager.queryProductAndLaunchBilling(this, "free_123")
            }
        }
    }

    private fun setupUI() {
        setupShimmer()
        setupBottomSheet()
        setupListeners()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        val screenHeight = resources.displayMetrics.heightPixels
        bottomSheetBehavior.peekHeight = (screenHeight * 0.391).toInt()
        bottomSheetBehavior.isHideable = false
    }

    private fun setupShimmer() = with(binding.shimmerBlock) {
        setShimmer(
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
        blockContent.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        btnTryAgain.visibility = View.GONE
        shimmerBlock.showAndStart()
    }

    private fun showLoadingState2() = with(binding) {
        btnClaimOffer.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.VISIBLE
        blockContent.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
        shimmerBlock.showAndStart()
    }

    private fun showErrorState() = with(binding) {
        shimmerBlock.visibility = View.GONE
        btnClaimOffer.visibility = View.INVISIBLE
        txtPriceDescription.visibility = View.INVISIBLE
        txtTileError.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
        btnTryAgain.visibility = View.VISIBLE
    }

    private fun showSuccessState() = with(binding) {
        shimmerBlock.visibility = View.GONE
        blockContent.visibility = View.VISIBLE
        btnClaimOffer.text = "CLAIM OFFER"
        btnClaimOffer.isEnabled = true
        txtPriceDescription.visibility = View.VISIBLE
        pbClaimOffer.visibility = View.GONE
        // chỗ này hiển thị
        // txtDiscount.text = getString(R.string.discount_price, chuyền vào giá 1 tuần thì hiển thị thế nào)
    }

    private fun simulateLoading1() = lifecycleScope.launch {
        delay(2500)
        binding.shimmerBlock.stopShimmer()
        showErrorState()
    }

    private fun simulateLoading2() = lifecycleScope.launch {
        delay(2500)
        binding.shimmerBlock.stopShimmer()
        showSuccessState()
    }

    private fun View.showAndStart() {
        visibility = View.VISIBLE
        if (this is com.facebook.shimmer.ShimmerFrameLayout) startShimmer()
    }
}
