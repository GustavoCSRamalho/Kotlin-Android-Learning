package gustavo.com.ceep.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import gustavo.com.ceep.R
import gustavo.com.ceep.model.Note
import kotlinx.android.synthetic.main.note_item.view.*


class NoteListAdapter(private val notes: List<Note>,
                      private val context: Context,
                      private val onItemClickListener: (note: Note, position: Int) -> Unit): Adapter<NoteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.let{
            it.bindView(note)
            it.itemView.setOnClickListener {
                onItemClickListener(note, position)
            }
        }


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.note_item_title
        val description = itemView.note_item_description

        fun bindView(note: Note) {
            val title = itemView.note_item_title
            val description = itemView.note_item_description

            title.text = note.title
            description.text = note.description
        }
        fun onClick(note: Note, execute:(note: Note) -> Unit) {
            itemView.setOnClickListener{
                execute(note)
            }
        }
    }
}

