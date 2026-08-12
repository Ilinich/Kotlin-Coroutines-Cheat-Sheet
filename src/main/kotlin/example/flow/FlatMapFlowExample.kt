package example.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
    runBlocking {
        flowOf("a", "b", "c")
            .onStart {
                println("onStart")
            }
            .flatMapMerge {
                flow {
                    delay(Random.nextLong(10, 500))
                    emit(it)
                }
            }
            .onCompletion {
                println("onCompletion")
            }
            .collectLatest {
                delay(500)
                println("$it")
            }
    }
}