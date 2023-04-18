package ext

import kotlinx.coroutines.Job
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

fun CoroutineContext.contextToString(): String = "Job = ${this[Job]}, Dispatcher = ${this[ContinuationInterceptor]}"
