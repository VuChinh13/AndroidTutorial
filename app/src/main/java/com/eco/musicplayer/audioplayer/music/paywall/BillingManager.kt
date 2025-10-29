package com.eco.musicplayer.audioplayer.music.paywall

import android.content.Context
import android.util.Log
import com.android.billingclient.api.*

class BillingManager(
    context: Context,
    private val onProductLoaded: (ProductDetails) -> Unit
) {
    private val billingClient = BillingClient.newBuilder(context)
        .enablePendingPurchases()
        .setListener { _, _ -> }
        .build()

    fun startConnection(onReady: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    onReady()
                } else {
                    Log.e("BillingManager", "Billing setup failed: ${result.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w("BillingManager", "Billing service disconnected.")
            }
        })
    }

    fun queryProduct(productId: String) {
        if (!billingClient.isReady) {
            Log.w("BillingManager", "BillingClient is not ready yet.")
            return
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.SUBS) // Giả định là sub
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(params) { result, productDetailsList ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK &&
                productDetailsList.isNotEmpty()
            ) {
                onProductLoaded(productDetailsList[0]) // Gửi về UI
            } else {
                Log.e(
                    "BillingManager",
                    "Query product failed: ${result.debugMessage}, responseCode=${result.responseCode}"
                )
            }
        }
    }

    fun logAllProducts() {
        val subsProducts = listOf("free_123", "test1", "test2")
        val inappProducts = listOf("test3")

        queryAndLog(subsProducts, BillingClient.ProductType.SUBS)
        queryAndLog(inappProducts, BillingClient.ProductType.INAPP)
    }

    private fun queryAndLog(productIds: List<String>, productType: String) {
        if (!billingClient.isReady) {
            Log.w("BillingManager", "BillingClient is not ready yet for logging.")
            return
        }

        val productList = productIds.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it)
                .setProductType(productType)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { result, productDetailsList ->
            if (result.responseCode != BillingClient.BillingResponseCode.OK) {
                Log.e(
                    "BillingManager",
                    "Query log failed: ${result.debugMessage}, type=$productType"
                )
                return@queryProductDetailsAsync
            }

            if (productDetailsList.isEmpty()) {
                Log.w("BillingManager", "No products found for $productType")
                return@queryProductDetailsAsync
            }

            productDetailsList.forEach { logProductDetails(it) }
        }
    }

    private fun logProductDetails(product: ProductDetails) {
        val sb = StringBuilder()
        sb.appendLine("+) ProductDetails:")
        sb.appendLine(" • productId = ${product.productId}")
        sb.appendLine(" • productType = ${product.productType}")
        sb.appendLine(" • title = ${product.title}")
        sb.appendLine(" • name = ${product.name}")
        sb.appendLine(" • description = ${product.description}")

        product.oneTimePurchaseOfferDetails?.let {
            sb.appendLine(formatOneTimeOffer(it))
        }

        product.subscriptionOfferDetails?.let {
            sb.appendLine(formatSubscriptionOffers(it))
        }

        Log.d("BillingManager", sb.toString())
    }

    private fun formatOneTimeOffer(offer: ProductDetails.OneTimePurchaseOfferDetails): String =
        buildString {
            appendLine(" OneTimePurchaseOfferDetails:")
            appendLine("   - priceCurrencyCode = ${offer.priceCurrencyCode}")
            appendLine("   - formattedPrice = ${offer.formattedPrice}")
        }

    private fun formatSubscriptionOffers(offers: List<ProductDetails.SubscriptionOfferDetails>): String =
        buildString {
            appendLine(" SubscriptionOfferDetails (${offers.size}):")
            offers.forEach { offer ->
                appendLine("   Offer ID: ${offer.offerId ?: "N/A"}")
                appendLine("   - basePlanId = ${offer.basePlanId}")
                appendLine("   - offerTags = ${offer.offerTags}")
                appendLine("   - offerIdToken = ${offer.offerToken}")

                offer.pricingPhases.pricingPhaseList.forEachIndexed { index, phase ->
                    appendLine(formatPricingPhase(index, phase))
                }
            }
        }

    private fun formatPricingPhase(index: Int, phase: ProductDetails.PricingPhase): String =
        buildString {
            appendLine("     Phase ${index + 1}:")
            appendLine("       • price = ${phase.formattedPrice}")
            appendLine("       • currencyCode = ${phase.priceCurrencyCode}")
            appendLine("       • billingPeriod = ${phase.billingPeriod}")
            appendLine("       • cycles = ${phase.billingCycleCount}")
            appendLine("       • recurrence = ${phase.recurrenceMode}")
        }

}
