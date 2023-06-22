package example.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

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
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        doAction()
                    }
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