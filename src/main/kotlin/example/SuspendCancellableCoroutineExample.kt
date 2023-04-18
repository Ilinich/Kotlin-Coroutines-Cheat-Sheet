package example

import core.CallbackFile
import core.NetworkService
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun main() {
    val networkService = NetworkService()
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        val file = downloadWithClearRes(networkService)
        println("file=$file")
    }
    runBlocking {
        job.join()
    }
}


suspend fun downloadWithClearRes(networkService: NetworkService): File {
    /**
     * The same logic as for [suspendCoroutine] but the function returns [CancellableContinuation],
     * we can check the state of our coroutine and we can hang a callback on the fact that the coroutine is canceled.
     */
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            // resource.close() Ensures the resource is closed on cancellation
        }
        networkService.getFile(object : CallbackFile {
            override fun onSuccess(result: File) {
                continuation.resume(result)
            }

            override fun onFailure(error: Exception) {
                continuation.resumeWithException(error)
            }
        })
    }
}