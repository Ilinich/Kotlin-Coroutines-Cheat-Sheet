package example.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random


fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        val items = withContext(Dispatchers.IO) {
            (1..15).map(concurrency = 5) { index ->
                delay(Random.nextInt(10, 50).toLong())
                println("got user with index=$index")
                "User id=$index"
            }
        }
        println(items.toString())
    }

    runBlocking { job.join() }
}


suspend fun <T, R> Iterable<T>.map(
    concurrency: Int,
    transform: suspend (T) -> R
): Unit = coroutineScope {
    // Create semaphore with permit specified as `concurrency`
    val semaphore = Semaphore(concurrency)

    map { item ->
        // Before processing each item, acquire the semaphore permit
        // This will be suspended until permit is available.
        semaphore.acquire()

        async {
            try {
                transform(item)
            } finally {
                // After processing (or failure), release a semaphore permit
                semaphore.release()
            }
        }
    }.awaitAll()
}