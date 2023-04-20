# Kotlin-Coroutines-Cheat-Sheet


## The general structure of the coroutine:<br>
Coroutines is an object that consists of several Job, Context, Dispatcher and CoroutineScope objects that interact with each other.<br><br>

## Structured Concurrency:<br>

Structured Concurrency guarantees that code distributed between threads will always be merged at some point. Those we control the code that runs in threads<br>

In the unstructured concurrency paradigm, threads start anywhere in the code, with no specifics as to where they start and end.<br><br>

## CoroutineScope:
In Kotlin coroutines, we always have a Scope which is the entry point.<br>
We always use Scope. Even the runBlocking method uses a GlobalScope to create a context for the coroutine and run the code.<br>

CoroutineScope only keeps the context that was passed to it and must have a Job.<br>
If we have not passed a Job to CoroutineScope, then we create it automatically when creating a Scope.<br>
Job is needed for communication between coroutines<br><br>

## Coroutine Context:
Context is somewhat similar to Map. Context keeps certain information for the coroutine to work, such as Dispatcher.<br>
Coroutine context has operator plus redefined.<br>

Each coroutine has its own Context. We take the Context from the parent and overwrite the value by the keys that we passed to the builder of our Coroutine. At the output, we get a new Coroutine Context<br>

We can create custom Context, only need extend our class from AbstractCoroutineContextElement.<br><br>

