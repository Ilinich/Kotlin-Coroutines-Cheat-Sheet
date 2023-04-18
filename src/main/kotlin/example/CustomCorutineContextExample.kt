package example

import ext.contextToString
import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 *
 * Context is somewhat similar to Map. Context stores certain information for the coroutine to work, such as Dispatcher.
 *
 */
fun main() {
    val userData = UserDataCoroutineContextElement(id = 1, name = "Alex")

    val scope = CoroutineScope(Job() + Dispatchers.Default + userData)

    val job = scope.launch {
        println("launch 1 context=: ${coroutineContext.contextToString()}")
        launch {
            println("launch 2 context=: ${coroutineContext.contextToString()}")


            /**
             * overwritten Dispatcher value in Context
             */
            launch(Dispatchers.IO) {
                println("launch 3 context=: ${coroutineContext.contextToString()}")

                /**
                 * As you can see User is available in each coroutines
                 *
                 * Further, we can get our custom object from context in each coroutine,
                 * since for each new coroutine a newContext is created based on the parentContext,
                 * and new fields overwrite the current ones if they already exist.
                 */
                val value = coroutineContext[UserDataCoroutineContextElement]
                println("user name=: ${value?.name}")
            }
        }
    }

    runBlocking { job.join() }
}

//launch 1 context=: Job = StandaloneCoroutine{Active}@7ee8ead3, Dispatcher = Dispatchers.Default
//launch 2 context=: Job = StandaloneCoroutine{Active}@4c67ae84, Dispatcher = Dispatchers.Default
//launch 3 context=: Job = StandaloneCoroutine{Active}@1bccedbb, Dispatcher = Dispatchers.IO
//user name=: Alex

/**
 *  We can add our own object to the Context,
 *  we only need to inherit from the AbstractCoroutineContextElement class.
 */
data class UserDataCoroutineContextElement(
    val id: Long,
    val name: String,
) : AbstractCoroutineContextElement(UserDataCoroutineContextElement) {
    companion object Key : CoroutineContext.Key<UserDataCoroutineContextElement>
}
