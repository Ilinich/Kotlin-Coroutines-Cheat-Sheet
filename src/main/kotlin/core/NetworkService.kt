package core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

interface CallbackFile {

    fun onSuccess(result: File)

    fun onFailure(error: Exception)
}

class NetworkService {

    fun getFile(callback: CallbackFile) {
        GlobalScope.launch {
            delay(200)
            callback.onSuccess(File("my file path"))
        }
    }
}