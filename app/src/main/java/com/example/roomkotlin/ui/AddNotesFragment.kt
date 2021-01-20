@file:Suppress("DEPRECATION")

package com.example.roomkotlin.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.roomkotlin.R
import com.example.roomkotlin.db.NoteDataBase
import com.example.roomkotlin.db.User
import kotlinx.android.synthetic.main.fragment_add_notes.*
import kotlinx.coroutines.launch

class AddNotesFragment : BaseFragment() {

    private var notes : User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments.let {
            notes = AddNotesFragmentArgs.fromBundle(it!!).user
            edit_text_title.setText(notes?.title)
            edit_text_note.setText(notes?.notes)
        }

        button_save.setOnClickListener {view->
            var note_title = edit_text_title.text.toString().trim()
            var note_desc = edit_text_note.text.toString().trim()

            if(note_title.isEmpty() || note_desc.isEmpty()){
                Toast.makeText(activity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            launch {
                //for null safety
                context.let {
                    val m_note = User(note_title,note_desc)
                    if(notes == null){
                        NoteDataBase(it!!).getNoteDao().addNote(m_note)
                        Toast.makeText(activity, "notes added successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        m_note.id = notes!!.id
                        NoteDataBase(it!!).getNoteDao().updateNotes(m_note)
                        Toast.makeText(activity, "notes updated successfully", Toast.LENGTH_SHORT).show()
                    }

                    val action = AddNotesFragmentDirections.actionAddNotesFragmentToHomeFragment()
                    Navigation.findNavController(view ).navigate(action)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete -> if (notes != null) deleteNote()
            else
                Toast.makeText(activity, "Cant delete empty note", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                    activity?.onBackPressed()
                    return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure to delete?")
            setMessage("Once deleted, Undo not possible")
            setPositiveButton("yes") { _,_->
                launch {
                    context.let {
                        NoteDataBase(it).getNoteDao().deleteNote(notes!!)
                        val action = AddNotesFragmentDirections.actionAddNotesFragmentToHomeFragment()
                        Navigation.findNavController(view!!).navigate(action)
                        Toast.makeText(activity, "notes deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            setNegativeButton("No"){_,_->
            }
        }
            .create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }



    /*fun saveNotes(note:User){
        class SaveNoteDetails() : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                NoteDataBase(activity!!).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                Toast.makeText(activity, "notes added", Toast.LENGTH_SHORT).show()
            }
        }
        SaveNoteDetails().execute();
    }

    fun saveNotesviaObserver(note:User){
        var observable: @NonNull io.reactivex.rxjava3.disposables.Disposable? = Observable.
                interval(1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    NoteDataBase(activity!!).getNoteDao().addNote(note)
                })
        Toast.makeText(activity, "notes added successfully", Toast.LENGTH_SHORT).show()
    }*/





}

