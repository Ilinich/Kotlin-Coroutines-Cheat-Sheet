package example.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val mutex = Mutex()
private var counterWithMutex = 0
private var counterNoMutex = 0

fun main() {
    val job1NoMutex = CoroutineScope(Dispatchers.IO)
        .launch {
            for (i in 1..500) {
                incrementCounterByTenNoMutex()
            }
        }

    val job2NoMutex = CoroutineScope(Dispatchers.IO)
        .launch {
            for (i in 1..500) {
                incrementCounterByTenNoMutex()
            }
        }

    val job3WithMutex = CoroutineScope(Dispatchers.IO)
        .launch {
            for (i in 1..500) {
                incrementCounterByTenWithMutex()
            }
        }

    val job4WithMutex = CoroutineScope(Dispatchers.IO).launch {
        for (i in 1..500) {
            incrementCounterByTenWithMutex()
        }
    }

    runBlocking { joinAll(job1NoMutex, job2NoMutex, job3WithMutex, job4WithMutex) }

    println("No Mutex Tally: $counterNoMutex")
    println("With Mutex Tally: $counterWithMutex")
}

private suspend fun incrementCounterByTenWithMutex() {
    mutex.withLock {
        for (i in 0 until 10) {
            counterWithMutex++
        }
    }
}

private fun incrementCounterByTenNoMutex() {
    for (i in 0 until 10) {
        counterNoMutex++
    }
}

//No Mutex Tally: 7579
//With Mutex Tally: 10000