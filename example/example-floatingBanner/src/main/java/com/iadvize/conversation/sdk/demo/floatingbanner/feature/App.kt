package com.iadvize.conversation.sdk.demo.floatingbanner.feature

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.iadvize.conversation.sdk.demo.utility.CloseableCoroutineScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        // Setup the iAdvize SDK
        IAdvizeSDKConfig.setup(this)
    }
}

internal fun uiScope(block: suspend CoroutineScope.() -> Unit) = CloseableCoroutineScope(
    SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
        Log.e("TEST", "Error inside UI coroutine scope", throwable)
    }).launch(Dispatchers.Main.immediate, block = block)