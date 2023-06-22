package example.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main() {
    runBlocking {
        flowOf("Hey")
            .onEach { doAction() }
            .map { it.length }
            .onStart {
                doAction()
            }
            .flowOn(Dispatchers.Default)
            .flatMapMerge {
                doAction()
                flowOf(1)
                    .onEach {
                        doAction()
                    }
                    .flowOn(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
            }
            .flowOn(Dispatchers.IO)
            .collect {
                doAction()
            }
    }
}

private fun doAction() {
    println(Thread.currentThread().name)
}