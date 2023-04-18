package example.flow

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

/**
 * Terminal operators are such operators that start Flow
 *
 * collect, single, reduce, count, first, toList, toSet, fold
 */
fun main() {

    /**
     * Terminal statements have to call collect, they are all suspend functions and can only be called in a coroutine
     * or another suspend function.
     */
    runBlocking {
        /**
         * It will count and return the number of Flow elements.
         *
         * And for this, he, obviously, will have to start Flow and wait until it will finish his work.
         */
        val count = flowOf("a", "b", "c").count()
        println("count=$count")
    }
}