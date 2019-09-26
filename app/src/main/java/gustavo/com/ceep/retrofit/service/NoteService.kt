package gustavo.com.ceep.retrofit.service

import gustavo.com.ceep.model.Note
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NoteService {

    @GET("notes")
    suspend fun list() : List<Note>

    @POST("notes")
    suspend fun insert(@Body note: Note): Note

    @PUT("notes/{id}")
    suspend fun alter(@Body note: Note,@Path("id") id: Int): Note
}