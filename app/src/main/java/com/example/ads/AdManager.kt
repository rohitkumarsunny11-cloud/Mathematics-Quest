package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import java.util.concurrent.atomic.AtomicBoolean

object AdManager {

    private const val TAG = "AdManager"
    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) { initializationStatus ->
                Log.d(TAG, "AdMob MobileAds initialized: $initializationStatus")
                isInitialized = true
            }
        } catch (e: Exception) {
            Log.e(TAG, "AdMob initialization error", e)
        }
    }

    /**
     * Feature 1: Rewarded Ad for 1 Free Hint.
     * Loads and shows a real AdMob RewardedAd.
     * Strictly awards the hint ONLY when the official SDK confirms the reward.
     * Double-reward protection ensures exactly one reward callback invocation.
     */
    fun loadAndShowRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdFailed: (String) -> Unit
    ) {
        val adRequest = AdRequest.Builder().build()
        val adUnitId = AdConfig.getRewardedAdUnitId()
        val rewardGranted = AtomicBoolean(false)

        RewardedAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            Log.d(TAG, "Rewarded ad dismissed. Reward granted status: ${rewardGranted.get()}")
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e(TAG, "Rewarded ad failed to show: ${adError.message}")
                            onAdFailed("Ad display failed: ${adError.message}")
                        }
                    }

                    rewardedAd.show(activity) { rewardItem ->
                        // Atomic check prevents duplicate callbacks from awarding extra hints
                        if (rewardGranted.compareAndSet(false, true)) {
                            Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
                            activity.runOnUiThread {
                                onRewardEarned()
                            }
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(TAG, "Rewarded ad failed to load: ${loadAdError.message}")
                    onAdFailed("Ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Feature 3: Interstitial Ads at level-completion transitions.
     * Frequency limit: shows only after every [AdConfig.INTERSTITIAL_LEVEL_FREQUENCY] completed levels.
     * If [hasRemovedAds] is true, immediately continues without showing any ad.
     */
    fun showInterstitialIfEligible(
        activity: Activity,
        sessionCompletedLevels: Int,
        hasRemovedAds: Boolean,
        onComplete: () -> Unit
    ) {
        if (hasRemovedAds) {
            onComplete()
            return
        }

        if (sessionCompletedLevels <= 0 || sessionCompletedLevels % AdConfig.INTERSTITIAL_LEVEL_FREQUENCY != 0) {
            onComplete()
            return
        }

        val adRequest = AdRequest.Builder().build()
        val adUnitId = AdConfig.getInterstitialAdUnitId()

        InterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            onComplete()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e(TAG, "Interstitial failed to show: ${adError.message}")
                            onComplete()
                        }
                    }
                    interstitialAd.show(activity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(TAG, "Interstitial failed to load: ${loadAdError.message}")
                    onComplete()
                }
            }
        )
    }

    /**
     * Feature 4: Rewarded Interstitial Ad (e.g. for optional bonus daily coins).
     * Distinct from normal rewarded hint flow and standard interstitials.
     */
    fun loadAndShowRewardedInterstitial(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onDismissed: (earnedReward: Boolean) -> Unit
    ) {
        val adRequest = AdRequest.Builder().build()
        val adUnitId = AdConfig.getRewardedInterstitialAdUnitId()
        val rewardGranted = AtomicBoolean(false)

        RewardedInterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            onDismissed(rewardGranted.get())
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e(TAG, "Rewarded interstitial failed to show: ${adError.message}")
                            onDismissed(false)
                        }
                    }

                    ad.show(activity) { rewardItem ->
                        if (rewardGranted.compareAndSet(false, true)) {
                            Log.d(TAG, "Rewarded interstitial reward earned: ${rewardItem.amount}")
                            activity.runOnUiThread {
                                onRewardEarned()
                            }
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(TAG, "Rewarded interstitial failed to load: ${loadAdError.message}")
                    onDismissed(false)
                }
            }
        )
    }
}

/**
 * Feature 2: Responsive Banner Ad Component.
 * Hides gracefully when [hasRemovedAds] is true or when ad fails to load.
 */
@Composable
fun BannerAdView(
    hasRemovedAds: Boolean,
    modifier: Modifier = Modifier
) {
    if (hasRemovedAds) {
        return
    }

    var isAdVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(if (isAdVisible) Modifier.wrapContentHeight() else Modifier.height(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = AdConfig.getBannerAdUnitId()
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            isAdVisible = true
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            super.onAdFailedToLoad(error)
                            isAdVisible = false
                            Log.d("BannerAdView", "Banner ad failed to load: ${error.message}")
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
