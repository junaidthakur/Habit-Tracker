package com.example.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdMobManager {

    const val APP_ID = "ca-app-pub-2355495489970239~5640654110"
    const val REWARDED_AD_UNIT_ID = "ca-app-pub-2355495489970239/6762164095"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2355495489970239/5884196696"

    const val TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val TEST_REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    private var rewardedAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd? = null
    private var isInitialized = false

    fun init(context: Context) {
        if (!isInitialized) {
            isInitialized = true
            try {
                MobileAds.initialize(context) { initializationStatus ->
                    Log.d("AdMobManager", "MobileAds initialized: $initializationStatus")
                }
            } catch (e: Exception) {
                Log.w("AdMobManager", "Failed to initialize MobileAds: ${e.message}")
            }
        }
    }

    fun loadAndShowInterstitialAd(
        activity: Activity,
        onAdClosed: () -> Unit = {}
    ) {
        loadInterstitialInternal(activity, INTERSTITIAL_AD_UNIT_ID, isRetry = false, onAdClosed = onAdClosed)
    }

    private fun loadInterstitialInternal(
        activity: Activity,
        adUnitId: String,
        isRetry: Boolean,
        onAdClosed: () -> Unit
    ) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d("AdMobManager", "Interstitial ad loaded successfully ($adUnitId).")

                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            interstitialAd = null
                            onAdClosed()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            interstitialAd = null
                            Log.w("AdMobManager", "Interstitial ad failed to show: ${adError.message}")
                            onAdClosed()
                        }
                    }

                    ad.show(activity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    Log.d("AdMobManager", "Failed to load interstitial ad ($adUnitId): ${loadAdError.message}")
                    if (!isRetry) {
                        // Fallback to test ad unit
                        loadInterstitialInternal(activity, TEST_INTERSTITIAL_AD_UNIT_ID, isRetry = true, onAdClosed = onAdClosed)
                    } else {
                        onAdClosed()
                    }
                }
            }
        )
    }

    fun loadAndShowRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdFailedToLoadOrShow: () -> Unit
    ) {
        loadRewardedInternal(activity, REWARDED_AD_UNIT_ID, isRetry = false, onRewardEarned, onAdFailedToLoadOrShow)
    }

    private fun loadRewardedInternal(
        activity: Activity,
        adUnitId: String,
        isRetry: Boolean,
        onRewardEarned: () -> Unit,
        onAdFailedToLoadOrShow: () -> Unit
    ) {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            activity,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Log.d("AdMobManager", "Rewarded ad loaded successfully ($adUnitId).")

                    var rewardEarned = false

                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            rewardedAd = null
                            if (!rewardEarned) {
                                Log.d("AdMobManager", "Ad dismissed without reward.")
                            }
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            rewardedAd = null
                            Log.w("AdMobManager", "Ad failed to show: ${adError.message}")
                            onAdFailedToLoadOrShow()
                        }
                    }

                    ad.show(activity, OnUserEarnedRewardListener { rewardItem ->
                        Log.d("AdMobManager", "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
                        rewardEarned = true
                        onRewardEarned()
                    })
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedAd = null
                    Log.d("AdMobManager", "Failed to load rewarded ad ($adUnitId): ${loadAdError.message}")
                    if (!isRetry) {
                        // Fallback to test ad unit
                        loadRewardedInternal(activity, TEST_REWARDED_AD_UNIT_ID, isRetry = true, onRewardEarned, onAdFailedToLoadOrShow)
                    } else {
                        onAdFailedToLoadOrShow()
                    }
                }
            }
        )
    }
}
