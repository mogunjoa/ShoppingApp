package com.mogun.shoppingapp.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import com.mogun.shoppingapp.R

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, applicationContext.getString(R.string.KAKAO_APP_KEY))
    }
}