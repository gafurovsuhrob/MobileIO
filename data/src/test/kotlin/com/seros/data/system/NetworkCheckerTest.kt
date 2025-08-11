package com.seros.data.system

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class NetworkCheckerTest {

    private val context: Context = mock(Context::class.java)
    private val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
    private val networkCapabilities: NetworkCapabilities = mock(NetworkCapabilities::class.java)

    @Test
    fun `isConnected returns true when network is available`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        whenever(connectivityManager.activeNetwork).thenReturn(mock())
        whenever(connectivityManager.getNetworkCapabilities(any())).thenReturn(networkCapabilities)
        whenever(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        val checker = NetworkChecker(context)

        assertTrue(checker.isNetworkAvailable())
    }

    @Test
    fun `isConnected returns false when no internet`() {
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        whenever(connectivityManager.activeNetwork).thenReturn(null)

        val checker = NetworkChecker(context)

        assertFalse(checker.isNetworkAvailable())
    }
}
