package core

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class Repository {

    suspend fun todo(){
        coroutineScope {
            async {  }
        }
    }
}