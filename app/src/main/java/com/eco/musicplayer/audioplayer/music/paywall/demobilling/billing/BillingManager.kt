package com.eco.musicplayer.audioplayer.music.paywall.demobilling.billing

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.QueryProductDetailsParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Suppress("DEPRECATION")
class BillingManager(
    private val context: Context,
    private val scope: CoroutineScope,
    private val onProductLoaded: (ProductDetails) -> Unit,
    private val onFailed: () -> Unit
) {
    private val billingClient = BillingClient.newBuilder(context)
        .enablePendingPurchases()
        .setListener { _, _ -> }
        .build()

    // Nếu thất bại, sẽ tự retry tối đa 3 lần
    fun startConnection(onReady: () -> Unit) {
        scope.launch {
            retryBillingConnection(maxRetries = 3, onReady)
        }
    }

    // Thử kết nối lại tối đa [maxRetries] lần, delay tăng dần 1s -> 2s -> 4s.
    private suspend fun retryBillingConnection(
        maxRetries: Int,
        onReady: () -> Unit
    ) {
        var delayTime = 1000L
        repeat(maxRetries) { attempt ->
            val result = startConnectionSuspend()
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("BillingManager", "Billing connected successfully on attempt #${attempt + 1}")
                onReady()
                return
            } else {
                Log.e(
                    "BillingManager",
                    "Billing setup failed (attempt ${attempt + 1}): ${result.debugMessage}"
                )
            }
            delay(delayTime)
            delayTime *= 2
        }
    }

    private suspend fun startConnectionSuspend(): BillingResult =
        suspendCancellableCoroutine { cont ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(result: BillingResult) {
                    cont.resume(result)
                }

                override fun onBillingServiceDisconnected() {
                    Log.w("BillingManager", "Billing service disconnected.")
                }
            })
        }

    // Query product details.
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
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(params) { result, productDetailsList ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK &&
                productDetailsList.isNotEmpty()
            ) {
                Log.d("CheckThreadchinh", Thread.currentThread().name.toString())
                onProductLoaded(productDetailsList[0])
            } else {
                onFailed()
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
        Log.d("BillingManager", "+) ProductDetails:")
        Log.d("BillingManager", " • productId = ${product.productId}")
        Log.d("BillingManager", " • productType = ${product.productType}")
        Log.d("BillingManager", " • title = ${product.title}")
        Log.d("BillingManager", " • name = ${product.name}")
        Log.d("BillingManager", " • description = ${product.description}")

        product.oneTimePurchaseOfferDetails?.let {
            logOneTimeOffer(it)
        }

        product.subscriptionOfferDetails?.let {
            logSubscriptionOffers(it)
        }
    }

    private fun logOneTimeOffer(offer: ProductDetails.OneTimePurchaseOfferDetails) {
        Log.d("BillingManager", " OneTimePurchaseOfferDetails:")
        Log.d("BillingManager", "   - priceCurrencyCode = ${offer.priceCurrencyCode}")
        Log.d("BillingManager", "   - formattedPrice = ${offer.formattedPrice}")
    }

    private fun logSubscriptionOffers(offers: List<ProductDetails.SubscriptionOfferDetails>) {
        Log.d("BillingManager", " SubscriptionOfferDetails (${offers.size}):")
        offers.forEach { offer ->
            Log.d("BillingManager", "   Offer ID: ${offer.offerId ?: "N/A"}")
            Log.d("BillingManager", "   - basePlanId = ${offer.basePlanId}")
            Log.d("BillingManager", "   - offerTags = ${offer.offerTags}")
            Log.d("BillingManager", "   - offerIdToken = ${offer.offerToken}")

            offer.pricingPhases.pricingPhaseList.forEachIndexed { index, phase ->
                logPricingPhase(index, phase)
            }
        }
    }

    private fun logPricingPhase(index: Int, phase: ProductDetails.PricingPhase) {
        Log.d("BillingManager", "     Phase ${index + 1}:")
        Log.d("BillingManager", "       • price = ${phase.formattedPrice}")
        Log.d("BillingManager", "       • currencyCode = ${phase.priceCurrencyCode}")
        Log.d("BillingManager", "       • billingPeriod = ${phase.billingPeriod}")
        Log.d("BillingManager", "       • cycles = ${phase.billingCycleCount}")
        Log.d("BillingManager", "       • recurrence = ${phase.recurrenceMode}")
    }

}
