package example.parallel

import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    val scope = CoroutineScope(Dispatchers.IO)
    val job = scope.launch {
        val items = (1..5).parallelMap { index ->
            getUserName(index)
        }
        println(items)
    }
    runBlocking { job.join() }
}

suspend fun getUserName(index: Int): String {
    delay(Random.nextInt(10, 50).toLong())
    println("Got user name index=$index")
    return "Name index=$index"
}

/**
 * Note that we are adding a coroutineScope because we need to wrap async in a suspend function.
 */
suspend fun <A, B> Iterable<A>.parallelMap(action: suspend (A) -> B): List<B> = coroutineScope {
    map {
        async {
            action(it)
        }
    }.awaitAll()
    /**
     * Or we could go through the list and run await() for each.
     */
}