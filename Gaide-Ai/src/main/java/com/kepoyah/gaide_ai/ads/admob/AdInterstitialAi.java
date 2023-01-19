package com.kepoyah.gaide_ai.ads.admob;


import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

public class AdInterstitialAi {
    private static InterstitialAd mInterstitialAd;
    private static boolean loadingIklan=true;
    private static Integer hitung=0;

    public static void SHOW(Context activity,Integer position, OnListener onListener) {
        hitung++;
        if (loadingIklan) {
            InterstitialAd.load(activity, SettingsAi.ad_interstitial_ai,
                    new AdRequest.Builder().build(),
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            interstitialAd.setFullScreenContentCallback(
                                    new FullScreenContentCallback() {
                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            mInterstitialAd = null;
                                            onListener.succeed();
                                        }


                                        @Override
                                        public void onAdShowedFullScreenContent() {

                                        }
                                    });

                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mInterstitialAd = null;

                        }
                    });
            loadingIklan = false;
        }
        if (hitung % SettingsAi.interval_inter_ai == 0) {
            if (mInterstitialAd != null) {
                SettingsAi.position_detail_ai = position;
                mInterstitialAd.show((Activity) activity);
            }else {
              onListener.failed();
            }
            loadingIklan = true;
        }else {
            onListener.load();
        }
    }
}
