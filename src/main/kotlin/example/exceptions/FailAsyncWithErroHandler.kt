package example.exceptions

import kotlinx.coroutines.*

fun main() {
    val handler = CoroutineExceptionHandler { _, _ -> println("1") }
    val handler2 = CoroutineExceptionHandler { _, _ -> println("2") }
    val handler3 = CoroutineExceptionHandler { _, _ -> println("3") }
    val scope = CoroutineScope(handler)

    runBlocking {
        scope.launch(handler2) {
            launch(handler3) {
                launch {
                    try {
                        async { throw Exception() }.await()
                    } catch (e: Exception) {
                        println("Exception caught in inner launch")
                    }
                }
            }
        }.join()
    }
}