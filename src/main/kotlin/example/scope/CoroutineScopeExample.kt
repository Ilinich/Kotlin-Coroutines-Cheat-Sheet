package example.scope

import kotlinx.coroutines.*

/**
 * CoroutineScope is an important part that is used to start using coroutines.
 * CoroutineScope is the parent of all coroutines. When we cancel a scope, we cancel all of its child coroutines.
 *
 *
 * We always use Scope.
 * Even the runBlocking method uses a GlobalScope to create a context for the coroutine and run the code.
 */
fun main() {
    /**
     * if we create [CoroutineScope] without Job
     * ContextScope(if (context[Job] != null) context else context + Job())
     */
    val scope = CoroutineScope(Dispatchers.Default)

    val job1 = scope.launch {
        delay(50)
        println("top-level coroutine 1 finished work")
    }

    val job2 = scope.launch {
        delay(50)
        println("top-level coroutine 2 finished work")
    }

    println("job1 isActive= ${job1.isActive}")
    println("job2 isActive= ${job2.isActive}")

    runBlocking {
        scope.cancel()
    }

    println("job1 isCancelled= ${job1.isCancelled}")
    println("job2 isCancelled= ${job2.isCancelled}")
}

//job1 isActive= true
//job2 isActive= true

//job1 isCancelled= true
//job2 isCancelled= true