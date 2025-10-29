package com.eco.musicplayer.audioplayer.music.paywall

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.ProductDetails
import com.eco.musicplayer.R
import com.eco.musicplayer.databinding.ActivityPaywallSale50WeeklyBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaywallSale50WeeklyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaywallSale50WeeklyBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var billingManager: BillingManager
    private val productId = "free_123"
    private val offerId = "intro-price"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallSale50WeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        billingManager =
            BillingManager(this) { productDetails -> handleProductDetails(productDetails) }
    }

    private fun handleProductDetails(productDetails: ProductDetails) {
        lifecycleScope.launch(Dispatchers.Main) {
            val introOffer =
                productDetails.subscriptionOfferDetails?.find { it.offerId == offerId }

            if (introOffer != null) {
                val pricingPhases = introOffer.pricingPhases.pricingPhaseList

                val introPhase = pricingPhases.firstOrNull()
                val basePhase = pricingPhases.getOrNull(1)

                val introPrice = introPhase?.formattedPrice
                val basePrice = basePhase?.formattedPrice
                val introCycleCount = introPhase?.billingCycleCount

                val introMicros = introPhase?.priceAmountMicros ?: 0
                val baseMicros = basePhase?.priceAmountMicros ?: 1
                val discountPercent = if (baseMicros > 0) {
                    100 - ((introMicros * 100) / baseMicros)
                } else 0

                displayAfterQuery(basePrice, introPrice, discountPercent, introCycleCount)
                showSuccessState()
            } else {
                // làm gì đó ...
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAfterQuery(
        basePrice: String?,
        introPrice: String?,
        discountPercent: Long?,
        introCycleCount: Int?
    ) {
        binding.txtDiscount.apply {
            text = "$basePrice/week"
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.txtOffBadge.text = getString(R.string.off, discountPercent)
        binding.txtPrice.text = getString(R.string.price_week, introPrice)
        binding.txtPriceDescription.text = getString(
            R.string.price_description,
            introPrice,
            introCycleCount,
            basePrice
        )
    }

    private fun setupUI() {
        setupShimmer()
        setupBottomSheet()
        setupListeners()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        val screenHeight = resources.displayMetrics.heightPixels
        bottomSheetBehavior.peekHeight = (screenHeight * 0.38).toInt()
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

        showLoadingStateFirst()
        simulateLoadingFirst()

        btnTryAgain.setOnClickListener {
            showLoadingStateSecondary()
            simulateLoadingSecondary()
        }
    }

    private fun showLoadingStateFirst() = with(binding) {
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

    private fun showLoadingStateSecondary() = with(binding) {
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
        btnClaimOffer.text = getString(R.string.claim_offer)
        shimmerBlock.stopShimmer()
        shimmerBlock.visibility = View.GONE
        pbClaimOffer.visibility = View.GONE
        txtOffBadge.visibility = View.VISIBLE
        blockContent.visibility = View.VISIBLE
        txtPriceDescription.visibility = View.VISIBLE
    }

    private fun simulateLoadingFirst() = lifecycleScope.launch {
        delay(2500)
        binding.shimmerBlock.stopShimmer()
        showErrorState()
    }

    private fun simulateLoadingSecondary() {
        billingManager.startConnection {
            // log tất cả sản phẩm
            billingManager.logAllProducts()

            billingManager.queryProduct(productId)
        }
    }

    private fun View.showAndStart() {
        visibility = View.VISIBLE
        if (this is ShimmerFrameLayout) startShimmer()
    }
}