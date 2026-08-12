package example.scope

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        coroutineScope {
            launch {
                delay(200)
                println("A")
            }
            delay(100)
            println("B")
        }

        println("C")

        coroutineScope {
            launch {
                println("D")
            }
            launch {
                println("E")
            }
            delay(100)
            println("F")
        }

        launch {
            println("G")
        }

        println("H")
    }

    runBlocking {
        job.join()
    }
}

//B
//A
//C
//D
//E
//F
//H
//G