package com.example.ads

/**
 * AdMob Configuration for Math Quest: Brain Challenge.
 *
 * CONNECTED ADMOB CREDENTIALS:
 * App ID: ca-app-pub-1765363605368624~2041980950
 * Banner Ad Unit ID: ca-app-pub-1765363605368624/9345755907
 * Rewarded Ad Unit ID: ca-app-pub-1765363605368624/2536804974
 * Interstitial Ad Unit ID: ca-app-pub-1765363605368624/4285000913
 *
 * NOTE ON TESTING:
 * - When USE_TEST_ADS = false, the app requests live ads from your AdMob units.
 * - When USE_TEST_ADS = true, the app uses Google's official test ad units
 *   (recommended while clicking around in emulators/development to avoid invalid clicks policy flags).
 */
object AdConfig {

    // Set to false to serve your live connected AdMob ads; set to true if testing on emulators
    var USE_TEST_ADS: Boolean = false

    // Interstitial frequency: show at level completion after every N completed levels.
    const val INTERSTITIAL_LEVEL_FREQUENCY: Int = 3

    // =========================================================================
    // PRODUCTION IDs (Connected directly from your AdMob Console)
    // =========================================================================
    const val PROD_APP_ID = "ca-app-pub-1765363605368624~2041980950"
    const val PROD_BANNER_AD_UNIT_ID = "ca-app-pub-1765363605368624/9345755907"
    const val PROD_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-1765363605368624/4285000913"
    const val PROD_REWARDED_AD_UNIT_ID = "ca-app-pub-1765363605368624/2536804974"
    const val PROD_REWARDED_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-1765363605368624/2536804974"

    // =========================================================================
    // GOOGLE OFFICIAL TEST AD UNIT IDs (Safe for development on Android)
    // =========================================================================
    const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"
    const val TEST_BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val TEST_REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    const val TEST_REWARDED_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/5354046379"

    fun getBannerAdUnitId(): String {
        return if (USE_TEST_ADS) {
            TEST_BANNER_AD_UNIT_ID
        } else {
            PROD_BANNER_AD_UNIT_ID
        }
    }

    fun getInterstitialAdUnitId(): String {
        return if (USE_TEST_ADS) {
            TEST_INTERSTITIAL_AD_UNIT_ID
        } else {
            PROD_INTERSTITIAL_AD_UNIT_ID
        }
    }

    fun getRewardedAdUnitId(): String {
        return if (USE_TEST_ADS) {
            TEST_REWARDED_AD_UNIT_ID
        } else {
            PROD_REWARDED_AD_UNIT_ID
        }
    }

    fun getRewardedInterstitialAdUnitId(): String {
        return if (USE_TEST_ADS) {
            TEST_REWARDED_INTERSTITIAL_AD_UNIT_ID
        } else {
            PROD_REWARDED_INTERSTITIAL_AD_UNIT_ID
        }
    }
}
