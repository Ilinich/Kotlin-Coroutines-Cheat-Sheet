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

        println()

        val flow = flowOf("valid123", "invalid@#", "anotherValid456", "123")

        flow.validateStrings()
            .collect { value ->
                println(value)
            }
    }
}

fun Flow<String>.toUpperCase() = transform { value ->
    return@transform emit(value.uppercase())
}

fun Flow<String>.validateStrings(): Flow<String> = transform { value ->
    if (value.all { it.isLetterOrDigit() }) {
        emit(value) // Передаем только валидные строки
    }
}