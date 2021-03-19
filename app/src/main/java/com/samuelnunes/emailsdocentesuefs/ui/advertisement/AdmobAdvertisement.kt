package com.samuelnunes.emailsdocentesuefs.ui.advertisement

import android.content.Context
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.samuelnunes.emailsdocentesuefs.BuildConfig

class AdmobAdvertisement(val context: Context) {
    private lateinit var adView: AdView

    init {
        MobileAds.initialize(context)
    }

    companion object {
        val BANNER = AdSize.BANNER
        val FULL_BANNER = AdSize.FULL_BANNER
        val LARGE_BANNER = AdSize.LARGE_BANNER
        val LEADERBOARD = AdSize.LEADERBOARD
        val MEDIUM_RECTANGLE = AdSize.MEDIUM_RECTANGLE
        val WIDE_SKYSCRAPER = AdSize.WIDE_SKYSCRAPER
        val SMART_BANNER = AdSize.SMART_BANNER
        val FLUID = AdSize.FLUID
    }

    fun createBanner(view: RelativeLayout, idAdMob: Int, size: AdSize = SMART_BANNER) {
        adView = AdView(context)
        adView.adSize = size
        adView.adUnitId = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/6300978111"
        } else {
            context.getString(idAdMob)
        }
        view.addView(adView)
    }

    fun loadBanner() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}