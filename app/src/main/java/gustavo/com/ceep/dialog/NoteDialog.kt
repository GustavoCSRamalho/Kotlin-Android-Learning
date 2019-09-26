package gustavo.com.ceep.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import gustavo.com.ceep.R
import gustavo.com.ceep.model.Note
import gustavo.com.ceep.webClient.NoteWebClient

import kotlinx.android.synthetic.main.form_note.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteDialog(private val viewGroup: ViewGroup,
                 private val context: Context): CoroutineScope {

    private val createdView = createView()
    private val titleField = createdView.form_note_title
    private val descriptionField = createdView.form_note_description

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun show(created: (createdNote: Note) -> Unit){
        AlertDialog.Builder(context)
            .setTitle("Add note")
            .setView(createdView)
            .setPositiveButton("Save") { _, _ ->
                val title = titleField.text.toString()
                val description = descriptionField.text.toString()
                val note = Note(title = title, description = description)

                launch(Dispatchers.Main){
                    NoteWebClient().insert(note, {
                        created(it)
                    },{
                        Toast.makeText(context, "Falha ao salvar nota", Toast.LENGTH_LONG).show()
                    })
                    job.cancel()
                }
            }
            .show()
    }

    private fun createView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_note,
                viewGroup,
                false)
    }

    fun alter(note: Note,altered: (altered: Note)  -> Unit, configuration: () -> Unit){
        titleField.setText(note.title)
        descriptionField.setText(note.description)
        AlertDialog.Builder(context)
            .setTitle("Alter note")
            .setView(createdView)
            .setPositiveButton("Save") { _, _ ->
                val title = titleField.text.toString()
                val description = descriptionField.text.toString()
                val alteredNote = note.copy(title = title, description = description)
                launch(Dispatchers.Main) {
                    NoteWebClient().alter(alteredNote, {
                        altered(it)
                    }, {
                        Toast.makeText(context, "Falha ao alterar nota", Toast.LENGTH_LONG).show()
                    })
                    configuration()
                    job.cancel()
                }
            }
            .show()
    }
}