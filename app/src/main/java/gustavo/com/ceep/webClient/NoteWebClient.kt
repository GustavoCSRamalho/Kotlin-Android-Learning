package gustavo.com.ceep.webClient

import android.hardware.camera2.CaptureFailure
import callback
import gustavo.com.ceep.model.Note
import gustavo.com.ceep.retrofit.RetrofitInitializer

class NoteWebClient {

    fun list(success: (notes: List<Note>) -> Unit, failure: (throwable: Throwable) -> Unit) {
        val call = RetrofitInitializer().noteService().list()
        call.enqueue(callback { response, thorwable ->
            response?.body()?.let {
                success(it)
            }
            thorwable?.let {
                failure(it)
            }
        })
    }

    fun insert(note: Note, success: (note: Note) -> Unit, failure: (throwable: Throwable) -> Unit) {
        val call = RetrofitInitializer().noteService().insert(note)
        call.enqueue(callback { response, thorwable ->
            response?.body()?.let {
                success(it)
            }
            thorwable?.let {
                failure(it)
            }
        })
    }

    fun alter(note: Note, success: (note: Note) -> Unit,
              failure: (throwable: Throwable) -> Unit) {
        val call = RetrofitInitializer().noteService().alter(note,note.id)
        call.enqueue(callback { response, thorwable ->
            response?.body()?.let {
                success(it)
            }
            thorwable?.let {
                failure(it)
            }
        })
    }

}



