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


##Job:
Each coroutine has its own Job. Job is also a coroutine context. Job allows you to know the status of the coroutine or cancel it.<br>
When we cancel a Job, we cancel all of its child coroutines.<br>

SupervisorJob does not cancel all of its child coroutines if an exception is thrown in one of them.<br>

Coroutine implementation interface Job. In fact, in the builder we return our coroutine, but only part of the available API. It's like the SOLID interface segregation principle.<br><br>

##Modifier suspend:
The suspend modifier tells the Kotlin compiler that the function can be suspended. To do this, a new Continuation parameter is added to it.<br>

Continuation is an interface to return the result of the execution.<br>

The compiler generates in the Finite state machine for the suspend function, this allows us to suspend some part of our code.<br><br>


## Continuation:
Continuation is a callback for the suspend function. If there are several suspend functions in the coroutine, then Continuation will be a callback for all of them.<br><br>

## Coroutine builders:
We have the following builders: runBlocking, launch, async,<br>

Builder launch creates a new coroutine and launches it. Returns Job.<br>

async - returns a Deferred child of Job, so it has all the same methods.<br>
But in addition, it has methods for obtaining the result of the work of the coroutine. One of them is the await method.<br><br>

## Connection parent with child coroutines:
The Child Job takes the parent Job from parentContext and calls its attachChild method and passes itself there.<br>

The parent creates a ChildHandleNode object, and not only keeps it for itself, but also sends it back to the child Job as a result of calling the attachChild method.<br><br>

## Cancellation Coroutine:
When we call the cancel method, the coroutine goes into the cancelling state, isActive = false.<br>
It notifies the parent and child coroutines of its cancellation.<br>

Under the hood, the same notification mechanism is used as for exceptions. Instead of a real error, a CancellationException is thrown.<br>
The canceled coroutine throws CancellationException parent and childs coroutines.<br>

The parent coroutine checks for the exception and if it's a CancellationException then the parent coroutine simply ignores the error.<br>

The child coroutine does not check for the exception because its parent coroutine has been canceled and the child coroutine needs to be canceled as well. The child coroutine cancels itself and sends a cancel message to its children.<br><br>


## Exceptions:

The first thing we can use to handle errors is to use try/catch inside suspend functions.<br>

But then we should not forget that we can catch the CancellationException error due to the way coroutines are cancelled.<br>

In general, the try / catch approach is very dangerous because we have to take care of the entire chain of calls. Therefore, it is better to use try / catch in some local places.<br>


The second way is to use CoroutineExceptionHandler:<br>
This handler catches all errors, saves the first one and suppresses the rest.<br>

For CoroutineExceptionHandler to work, you need to add it either in CoroutineScope or in top-level coroutines.<br>

An exception from a child coroutine propagates up to the Job object of the top level coroutine and then further up to the Job object in topLevelScope.<br>
If async is a top-level coroutine, then async does not throw an exception, but returns a Deferred which will return an error when the await method is called.<br>

The exception is wrapped in a Deferred, only for top-level async coroutines. Otherwise, the exception is immediately propagated up the Jobs hierarchy and caught in the CoroutineExceptionHandler or passed to an unhandled error handler on the thread, even without calling the await method.<br><br>


### Exception with coroutineScope:
When Coroutine falls, the exception is thrown up the hierarchy.<br>
But we have a different behavior if we wrapped the coroutine in the scope function coroutineScope<br>

coroutineScope function transform Coroutine into a call to suspend function. And it doesn't throw the exception up the Job hierarchy.
We can handle this function with try-catch<br>


### Exception with supervisorScope:
When we use the supervisorScope function, we add a new scope with type SupervisorJob to the hierarchy.<br>

It is important to understand that supervisorScope is a new, separate nested scope that should handle exceptions itself.<br>

If the supervisorScope cannot handle the exception, then it will propagate the exception up the hierarchy.<br>

All coroutines in supervisorScope is top-level coroutines and can have CoroutineExceptionHandler.<br><br>

## suspendCancellableCoroutine:
Works the same as suspendCoroutine but the function returns CancellableContinuation, we can check the status of our coroutine and we can add a callback to cancel the coroutine.<br><br>

## Dispatchers:
Dispatchers are just thread pools.<br><br>

## Lost coroutine:
We can lose the coroutine if we don't call the continuation.resume method.<br><br>

## GlobalScope:
GlobalScope.launch, async -  creates global coroutines. It is now the responsibility of the developer to keep track of their coroutine life cycle.<br><br>

## Kotlin Flow:
The basic operators for Flow are of two types: Intermediate and Terminal.<br>

Intermediate operators add various data transformations to the Flow, but do not run it.<br>
Terminal operators start Flow and work with the result of its work. collect, single, reduce, count, first, toList, toSet, fold<br>

intermediate operators:
map, filter, take, zip, combine, withIndex, scan, debounce, distinctUntilChanged, drop, sample.<br><br>




