package com.example.openweatherapp.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConnectivityObserverImpl(
    context: Context
) : ConnectivityObserver {

    private val manager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {

        return callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    trySend(ConnectivityObserver.Status.Available)
                }

                override fun onLost(network: Network) {
                    trySend(ConnectivityObserver.Status.Unavailable)
                }
            }

            manager.registerDefaultNetworkCallback(callback)

            awaitClose {
                manager.unregisterNetworkCallback(callback)
            }
        }
    }
}