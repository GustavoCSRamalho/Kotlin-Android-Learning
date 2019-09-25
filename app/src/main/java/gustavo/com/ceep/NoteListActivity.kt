package gustavo.com.ceep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import gustavo.com.ceep.webClient.NoteWebClient
import gustavo.com.ceep.adapter.NoteListAdapter
import gustavo.com.ceep.dialog.NoteDialog
import gustavo.com.ceep.model.Note
import kotlinx.android.synthetic.main.activity_note_list.*
import java.util.logging.Logger

class NoteListActivity : AppCompatActivity() {

    private val notes: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        NoteWebClient().list({
            notes.addAll(it)
            configureList()
        },{
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            Log.d("TAG",it.message)
        })

        fab_add_note.setOnClickListener {
            NoteDialog(window.decorView as ViewGroup,
                this)
                .show {
                    notes.add(it)
                    configureList()
                }
        }
    }

    private fun notes(): List<Note>{
        return listOf(Note(title = "Leitura",
            description = "Livro de Kotlin com Android"),
            Note(title = "Pesquisa",
                description = "Como posso melhorar o código dos meus projetos"),
            Note(title = "Estudo",
                description = "Como sincronizar minha App com um Web Service"))
    }

    private fun configureList() {
        val recyclerView = note_list_recyclerview
        recyclerView.adapter = NoteListAdapter(notes, this) {note, position ->

            NoteDialog(window.decorView as ViewGroup, this).alter(note) {
                notes[position] = it
                configureList()
            }
        }
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}
