package example

import core.CallbackFile
import core.NetworkService
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() {
    val networkService = NetworkService()
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        val file = download(networkService)
        println("file=$file")
    }
    runBlocking {
        job.join()
    }
}

/**
 * We can lose the coroutine if we don't call the [continuation.resume] method.
 */
suspend fun download(networkService: NetworkService): File {
    return suspendCoroutine { continuation ->
        networkService.getFile(object : CallbackFile {
            override fun onSuccess(result: File) {
                continuation.resume(result)
            }

            override fun onFailure(error: Exception) {
                // todo should be: continuation.resumeWithException(error)
            }
        })
    }
}