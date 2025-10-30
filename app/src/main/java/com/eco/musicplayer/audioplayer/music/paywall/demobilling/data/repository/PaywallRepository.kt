package com.eco.musicplayer.audioplayer.music.paywall.demobilling.data.repository

import com.eco.musicplayer.audioplayer.music.paywall.demobilling.data.model.PaywallConfig
import com.eco.musicplayer.audioplayer.music.paywall.demobilling.data.remote.RemoteConfig
import com.eco.musicplayer.audioplayer.music.paywall.demobilling.data.remote.RemoteConfigHelper
import com.google.gson.Gson

class PaywallRepository() {
    private val remoteConfig: RemoteConfig = RemoteConfig()
    private val gson: Gson = Gson()
    private val parameterName = "demo_config"
    fun getPaywallConfig(onLoaded: (PaywallConfig) -> Unit) {
        remoteConfig.fetchAndActivate {
            val jsonStr = RemoteConfigHelper.getString(parameterName, "")
            val config = if (jsonStr.isEmpty()) PaywallConfig()
            else gson.fromJson(jsonStr, PaywallConfig::class.java)

            onLoaded(config)
        }
    }
}
