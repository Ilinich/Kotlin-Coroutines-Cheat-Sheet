package example.job

import kotlinx.coroutines.*
import java.lang.Exception


fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }

    val scope = CoroutineScope(Job() + Dispatchers.Default + handler)

    val job = scope.launch {
        launch {
            delay(50)
            println("child1 coroutine finished work")
        }
        launch {
            /**
             * with exception Job in scope cancelled all child coroutines
             */
            throw Exception("child2 coroutine finished with Exception")
        }
        delay(50)
        println("top-level coroutine 1 finished work")
    }

    val job2 = scope.launch {
        delay(50)
        println("top-level coroutine 2 finished work")
    }

    println("job isActive= ${job.isActive}")
    println("job2 isActive= ${job.isActive}")

    runBlocking {
        job.cancel()
        job.join()
        job2.join()
    }

    println("job isCancelled= ${job.isCancelled}")
    println("job2 isCancelled= ${job.isCancelled}")
}

/**
 * we don't have response for "child1" coroutine because in "child2" was exception and Job cancelled all child coroutines
 */

//job isActive= true
//job2 isActive= true
//java.lang.Exception: child2 coroutine finished with Exception
//job isCancelled= true
//job2 isCancelled= true