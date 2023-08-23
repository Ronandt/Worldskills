package com.example.worldskills

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object NetworkObserver {
    fun observe(context: Context): Flow<Boolean> = callbackFlow {
        var networkService = context.getSystemService(ConnectivityManager::class.java)
        var networkCallback = object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()

                trySend(false)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {

                trySend(true)
            }

            override fun onLost(network: Network) {

                trySend(false)
            }
        }
        networkService.registerDefaultNetworkCallback(networkCallback)
        awaitClose {
            networkService.unregisterNetworkCallback(networkCallback)
        }
    }

}