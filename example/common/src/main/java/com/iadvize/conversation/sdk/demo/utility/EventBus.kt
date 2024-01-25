package com.iadvize.conversation.sdk.demo.utility

import android.util.Log
import com.iadvize.conversation.sdk.demo.utility.EventBus.eventBusScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

object EventBus {
    fun eventBusScope(block: suspend CoroutineScope.() -> Unit) =
        scope.launch(Dispatchers.IO, block = block)

    private val scope = CloseableCoroutineScope(
        SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Log.e("IDZ", "Error in event bus coroutine scope", throwable)
        })

    private val publisherFlow = MutableSharedFlow<Any>()
    val observerFlow = publisherFlow
    val callerJobsMap: MutableMap<String, MutableList<Job>> = mutableMapOf()

    suspend fun emit(event: Any) = publisherFlow.emit(event)
}

fun Any.send(event: Any) {
    eventBusScope { EventBus.emit(event) }
}

inline fun <reified T : Any> Any.on(crossinline block: (T) -> Unit) {
    val caller = this::class.java.name
    val job = eventBusScope { EventBus.observerFlow.filterIsInstance<T>().collect { block(it) } }

    if (EventBus.callerJobsMap[caller] == null)
        EventBus.callerJobsMap[caller] = mutableListOf(job)
    else
        EventBus.callerJobsMap[caller]!!.add(job)
}

fun Any.releaseObservers() {
    val caller = this::class.java.name
    val jobs = EventBus.callerJobsMap[caller]

    with(jobs) {
        this?.iterator()?.forEach { it.cancel() }
        this?.clear()
    }
    EventBus.callerJobsMap.remove(caller)
}
