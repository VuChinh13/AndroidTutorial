package com.eco.musicplayer.audioplayer.music.paywall

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*

class BillingManager(
    context: Context,
    private val onPurchase: (Purchase) -> Unit
) {

    private var onProductLoaded: ((ProductDetails) -> Unit)? = null

    fun setOnProductLoadedListener(listener: (ProductDetails) -> Unit) {
        onProductLoaded = listener
    }

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                purchases.forEach { purchase -> onPurchase(purchase) }
            }
        }

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    fun startConnection(onReady: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    onReady()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Có thể retry nếu muốn
            }
        })
    }

    fun queryProductAndLaunchBilling(activity: Activity, productId: String) {
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
            if (result.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                // ✅ Gọi callback trả kết quả về cho Activity
                onProductLoaded?.invoke(productDetailsList[0])
            }
        }
    }
}
