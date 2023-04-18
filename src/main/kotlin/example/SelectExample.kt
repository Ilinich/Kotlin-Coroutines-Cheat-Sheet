package example

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {

        val job1 = async {
            delay(100)
            "hello"
        }
        val job2 = async {
            delay(50)
            "Hello World"
        }

        /**
         * get only one result
         */
        val result = select {
            job1.onAwait {
                it.uppercase()
            }
            job2.onAwait {
                it.uppercase()
            }

            onTimeout(500) {
                "world"
            }
        }

        println(result)
    }

    runBlocking {
        job.join()
    }
}