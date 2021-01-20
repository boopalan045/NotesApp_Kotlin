package com.example.roomkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.roomkotlin.db.User
import com.example.roomkotlin.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val note:List<User>): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {



    class NoteViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.NoteViewHolder {
        return NoteViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        holder.view.txt_title.setText(note[position].title)
        holder.view.txt_notes.setText(note[position].notes)
        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNotesFragment()
            action.user = note[position]
            Navigation.findNavController(it).navigate(action)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return note.size
    }


}