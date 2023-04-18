package example.dispatchers

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * Unconfined Dispatcher:
 *
 * For an Unconfined dispatcher, the isDispatchNeeded method returns false.
 * This leads to the fact that when starting and resuming the execution
 * of the Continuation code, there is no thread change.
 *
 * Those. at the start of the coroutine, it is executed in the thread where the builder
 * was called, which created and launched this coroutine.
 * And when execution is resumed from the suspend function,
 * the coroutine is executed on the thread that was used in the suspend function to perform background work.
 * After all, it is in this thread that we call continuation.resume.
 */
fun main() {
    val scope = CoroutineScope(Job() + Dispatchers.Default)
    val job = scope.launch {
        println("launch 1 thread=: ${Thread.currentThread()}")
        launch(Dispatchers.Unconfined) {
            println("launch 2 thread=: ${Thread.currentThread()}")

            launch(Dispatchers.IO) {
                println("launch 3 thread=: ${Thread.currentThread()}")

                launch(Dispatchers.IO) {
                    println("launch 4.1 thread=: ${Thread.currentThread()}")
                }

                launch(Dispatchers.Unconfined) {
                    println("launch 4.2 thread=: ${Thread.currentThread()}")
                }
            }
        }
    }

    runBlocking { job.join() }
}

//launch 1 thread=: Thread[DefaultDispatcher-worker-1,5,main]
//launch 2 thread=: Thread[DefaultDispatcher-worker-1,5,main]

//launch 3 thread=: Thread[DefaultDispatcher-worker-2,5,main]

//launch 4.1 thread=: Thread[DefaultDispatcher-worker-3,5,main]
//launch 4.2 thread=: Thread[DefaultDispatcher-worker-2,5,main]
