package example.dispatchers

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * We can create our own dispatcher based on Executor
 */
fun main() {
    val customDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val customFixedThreadPoolDispatcher = Executors.newFixedThreadPool(5).asCoroutineDispatcher()

    val scope = CoroutineScope(Job() + customDispatcher)
    val job = scope.launch {
        println("launch 1 thread=: ${Thread.currentThread()}")
        withContext(customDispatcher) {
            println("withContext thread=: ${Thread.currentThread()}")

            withContext(customFixedThreadPoolDispatcher) {
                println("withContext2 thread=: ${Thread.currentThread()}")
            }
        }
    }
    runBlocking { job.join() }
}

//launch 1 thread=: Thread[pool-1-thread-1,5,main]
//withContext thread=: Thread[pool-1-thread-1,5,main]
//withContext2 thread=: Thread[pool-2-thread-1,5,main]