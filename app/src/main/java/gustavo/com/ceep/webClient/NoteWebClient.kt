package gustavo.com.ceep.webClient

import android.hardware.camera2.CaptureFailure
import androidx.lifecycle.ViewModel
import callback
import gustavo.com.ceep.model.Note
import gustavo.com.ceep.retrofit.RetrofitInitializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class NoteWebClient: ViewModel() {

//    fun list(success: (notes: List<Note>) -> Unit, failure: (throwable: Throwable) -> Unit) {
//        val call = RetrofitInitializer().noteService().list()
//        call.enqueue(callback { response, thorwable ->
//            response?.body()?.let {
//                success(it)
//            }
//            thorwable?.let {
//                failure(it)
//            }
//        })
//    }

    suspend fun list(success: (notes: List<Note>) -> Unit, failure: () -> Unit) {
        var call: List<Note> = listOf()
        try{
            withContext(Dispatchers.IO) {
                call = RetrofitInitializer().noteService().list()
            }
            success(call)
        }catch (e: Exception){
            failure()
        }

    }

    suspend fun insert(note: Note, success: (note: Note) -> Unit, failure: () -> Unit) {
        var call = Note(title = "",description = "")
        try {
            withContext(Dispatchers.IO){
                call = RetrofitInitializer().noteService().insert(note)
            }
            success(call)
        }catch (e: Exception){
            failure()
        }


    }

    suspend fun alter(note: Note, success: (note: Note) -> Unit,
              failure: () -> Unit) {
        var call = Note(title = "",description = "")
        try {
            withContext(Dispatchers.IO){
                call = RetrofitInitializer().noteService().alter(note,note.id)
                success(call)
            }
        }catch (e: Exception){
            failure()
            println(e.printStackTrace())
        }
    }
}



