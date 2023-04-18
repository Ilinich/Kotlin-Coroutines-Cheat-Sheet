package example.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        val join = flowOf("a", "b", "c").join()
        println("join string=$join")
    }
}


suspend fun Flow<String>.join(): String {
    val sb = StringBuilder()
    collect {
        sb.append(it).append(",")
    }
    return sb.toString()
}