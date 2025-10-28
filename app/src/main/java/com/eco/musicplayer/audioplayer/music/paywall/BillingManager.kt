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
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                if (productDetailsList.isEmpty()) {
                    Log.w("BillingManager", "No products found for $productType")
                    return@queryProductDetailsAsync
                }

                for (product in productDetailsList) {
                    logProductDetails(product)
                }
            } else {
                Log.e(
                    "BillingManager",
                    "Query log failed: ${result.debugMessage}, type=$productType"
                )
            }
        }
    }

    private fun logProductDetails(product: ProductDetails) {
        Log.d("BillingManager", "------ PRODUCT DETAIL ------")
        Log.d("BillingManager", "ID: ${product.productId}")
        Log.d("BillingManager", "Title: ${product.title}")
        Log.d("BillingManager", "Description: ${product.description}")
        Log.d("BillingManager", "Type: ${product.productType}")

        if (product.productType == BillingClient.ProductType.INAPP) {
            Log.d(
                "BillingManager",
                "One-time purchase: ${product.oneTimePurchaseOfferDetails?.formattedPrice}"
            )
        } else {
            product.subscriptionOfferDetails?.forEach { offer ->
                Log.d("BillingManager", "Offer ID: ${offer.offerId}")
                offer.pricingPhases.pricingPhaseList.forEach { phase ->
                    Log.d(
                        "BillingManager",
                        " - Price=${phase.formattedPrice}, Period=${phase.billingPeriod}, Cycles=${phase.billingCycleCount}, Recurrence=${phase.recurrenceMode}"
                    )
                }
            }
        }

        Log.d("BillingManager", "-----------------------------")
    }
}
