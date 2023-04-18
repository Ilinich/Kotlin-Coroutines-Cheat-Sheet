package example.job

import kotlinx.coroutines.*


/**
 * Each coroutine has its own Job. Job is also a coroutine context. Job allows you to know the status of the coroutine or cancel it.
 *
 * When we cancel a Job, we cancel all of its child coroutines.
 */
fun main() {
    /**
     * if we create [CoroutineScope] without Job
     * ContextScope(if (context[Job] != null) context else context + Job())
     */
    val scope = CoroutineScope(Dispatchers.Default)

    /**
     * Coroutine implementation interface Job.
     * In fact, in the builder we return our coroutine, but only part of the available API.
     * It's like the SOLID interface segregation principle.
     */
    val job = scope.launch {
        launch {
            delay(50)
            println("child1 coroutine finished work")
        }
        launch {
            delay(50)
            println("child2 coroutine finished work")
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

//job isActive= true
//job2 isActive= true
//top-level coroutine 2 finished work
//job isCancelled= true
//job2 isCancelled= true