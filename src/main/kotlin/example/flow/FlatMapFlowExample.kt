package example.flow

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
            .collect {
                println("$it")
            }
    }
}