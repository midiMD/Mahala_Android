package com.neighborly.neighborlyandroid.data.network

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

interface RetryPolicy {
    suspend operator fun <R> invoke(block: suspend () -> R): R
}

object NoRetryPolicy : RetryPolicy {
    override suspend operator fun <R> invoke(block: suspend () -> R): R = block()
}

class SimpleRetryPolicy(
    val attempts: Int,
) : RetryPolicy {
    override suspend operator fun <R> invoke(block: suspend () -> R): R {
        var attempt = 1
        var lastException: Exception

        do try {
            return block()
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive() // makes sure that it's not a CancellationException
            lastException = e
        }
        while (++attempt <= attempts)

        throw lastException
    }
}