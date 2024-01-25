package com.iadvize.conversation.sdk.demo.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context
    override fun close() = coroutineContext.cancelChildren()
}
