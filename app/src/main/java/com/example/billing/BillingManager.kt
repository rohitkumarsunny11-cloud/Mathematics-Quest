package com.example.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Google Play Billing Manager for Math Quest.
 * Manages the "Remove Ads" one-time non-consumable in-app purchase.
 *
 * Product ID placeholder: "remove_ads_99"
 * Must be created in Google Play Console under:
 * Monetize > Products > In-app products > Create product.
 */
class BillingManager(
    private val context: Context,
    private val onEntitlementGranted: (Boolean) -> Unit
) : PurchasesUpdatedListener {

    companion object {
        private const val TAG = "BillingManager"
        const val PRODUCT_REMOVE_ADS = "remove_ads_99"
        const val DEFAULT_PRICE_DISPLAY = "₹99"
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private var billingClient: BillingClient? = null

    private val _productDetails = MutableStateFlow<ProductDetails?>(null)
    val productDetails: StateFlow<ProductDetails?> = _productDetails.asStateFlow()

    private val _formattedPrice = MutableStateFlow(DEFAULT_PRICE_DISPLAY)
    val formattedPrice: StateFlow<String> = _formattedPrice.asStateFlow()

    private val _purchaseStatusMessage = MutableStateFlow<String?>(null)
    val purchaseStatusMessage: StateFlow<String?> = _purchaseStatusMessage.asStateFlow()

    init {
        initializeBillingClient()
    }

    private fun initializeBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(
                PendingPurchasesParams.newBuilder()
                    .enableOneTimeProducts()
                    .build()
            )
            .build()

        startConnection()
    }

    private fun startConnection(onConnected: (() -> Unit)? = null) {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Google Play Billing setup successful")
                    queryRemoveAdsProduct()
                    restorePurchases()
                    onConnected?.invoke()
                } else {
                    Log.w(TAG, "Billing setup failed with code: ${billingResult.responseCode}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "Billing service disconnected. Will reconnect on next request.")
            }
        })
    }

    fun queryRemoveAdsProduct() {
        val client = billingClient ?: return
        if (!client.isReady) {
            startConnection { queryRemoveAdsProduct() }
            return
        }

        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRODUCT_REMOVE_ADS)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        client.queryProductDetailsAsync(params) { billingResult, queryProductDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val details = queryProductDetailsList.firstOrNull { it.productId == PRODUCT_REMOVE_ADS }
                _productDetails.value = details
                details?.oneTimePurchaseOfferDetails?.formattedPrice?.let { priceStr ->
                    _formattedPrice.value = priceStr
                }
                Log.d(TAG, "Product query complete. Found: ${details?.productId}, Price: ${_formattedPrice.value}")
            } else {
                Log.d(TAG, "Product query returned code: ${billingResult.responseCode}")
            }
        }
    }

    fun launchPurchaseFlow(activity: Activity) {
        val client = billingClient
        if (client == null || !client.isReady) {
            _purchaseStatusMessage.value = "Billing service connecting... Please try again."
            startConnection { launchPurchaseFlow(activity) }
            return
        }

        val details = _productDetails.value
        if (details == null) {
            // Note for developer: In Play Console, product 'remove_ads_99' must be created and active.
            _purchaseStatusMessage.value = "Product 'remove_ads_99' not yet active in Play Console. Configure in Google Play Console to complete real purchases."
            return
        }

        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(details)
            .build()

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        val responseCode = client.launchBillingFlow(activity, billingFlowParams).responseCode
        if (responseCode != BillingClient.BillingResponseCode.OK) {
            _purchaseStatusMessage.value = "Failed to launch Google Play purchase: code $responseCode"
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (!purchases.isNullOrEmpty()) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                _purchaseStatusMessage.value = "Purchase cancelled."
            }
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                _purchaseStatusMessage.value = "You already own Remove Ads! Entitlement restored."
                onEntitlementGranted(true)
            }
            else -> {
                _purchaseStatusMessage.value = "Purchase error: ${billingResult.debugMessage}"
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.products.contains(PRODUCT_REMOVE_ADS)) {
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()

                    billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            Log.d(TAG, "Remove Ads purchase successfully acknowledged!")
                            _purchaseStatusMessage.value = "Ads successfully removed! Thank you for your support."
                            onEntitlementGranted(true)
                        } else {
                            Log.e(TAG, "Failed to acknowledge purchase: ${billingResult.debugMessage}")
                        }
                    }
                } else {
                    // Already acknowledged
                    onEntitlementGranted(true)
                }
            } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                _purchaseStatusMessage.value = "Purchase is pending completion by Google Play."
            }
        }
    }

    /**
     * Restores existing purchases (e.g. after reinstalling the app or switching devices).
     */
    fun restorePurchases(onFinished: ((Boolean) -> Unit)? = null) {
        val client = billingClient
        if (client == null || !client.isReady) {
            startConnection { restorePurchases(onFinished) }
            return
        }

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        client.queryPurchasesAsync(params) { billingResult, purchasesList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val hasRemoveAds = purchasesList.any { purchase ->
                    purchase.products.contains(PRODUCT_REMOVE_ADS) &&
                            purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }
                if (hasRemoveAds) {
                    _purchaseStatusMessage.value = "Purchases restored! Ads removed."
                    onEntitlementGranted(true)
                } else {
                    _purchaseStatusMessage.value = "No previous purchases found on this Google account."
                }
                onFinished?.invoke(hasRemoveAds)
            } else {
                _purchaseStatusMessage.value = "Failed to query purchases: ${billingResult.debugMessage}"
                onFinished?.invoke(false)
            }
        }
    }

    fun setTemporaryMessage(message: String) {
        _purchaseStatusMessage.value = message
    }

    fun clearStatusMessage() {
        _purchaseStatusMessage.value = null
    }

    fun destroy() {
        billingClient?.endConnection()
        billingClient = null
    }
}
