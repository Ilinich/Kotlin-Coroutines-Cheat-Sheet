package example.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach

/**
 * [Actor] can also provide us with thread safety,
 * because it has a [Channel] under the hood, which was created to safely transfer data between coroutines.
 */
fun main() {
    val scope = CoroutineScope(Dispatchers.IO)
    val counterContext = newSingleThreadContext("CounterContext")

    var counter = 0
    val actor = scope.actor<Unit> {
        consumeEach {
            counter++
        }
    }

    val job = scope.launch {
        withContext(counterContext) {
            (1..5).onEach { index ->
                actor.send(Unit)
                println("send index=$index")
            }
        }

        println("coroutine finished")
    }

    runBlocking {
        job.join()
    }

    println("counter=$counter")

    actor.close()
}