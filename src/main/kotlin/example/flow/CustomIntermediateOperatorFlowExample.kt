package example.flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        flowOf("a", "b", "c")
            .toUpperCase()
            .collect {
                println(it)
            }
    }
}

fun Flow<String>.toUpperCase() = transform { value ->
    return@transform emit(value.uppercase())
}