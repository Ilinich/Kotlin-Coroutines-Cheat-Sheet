package example.job

import kotlinx.coroutines.*
import java.lang.Exception


fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }

    /**
     * [SupervisorJob] does not cancel all of its child coroutines if an exception is thrown in one of them.
     */
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + handler)

    val job = scope.launch {
        launch {
            delay(50)
            println("child1 coroutine finished work")
        }
        launch {
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

//job isActive= true
//job2 isActive= true
//java.lang.Exception: child2 coroutine finished with Exception
//top-level coroutine 2 finished work
//job isCancelled= true
//job2 isCancelled= true