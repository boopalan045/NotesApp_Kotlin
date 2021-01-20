@file:Suppress("UNREACHABLE_CODE")

package com.example.roomkotlin.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomkotlin.NotesAdapter
import com.example.roomkotlin.R
import com.example.roomkotlin.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
        //for backpress
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_notes.setHasFixedSize(true)
        recycler_view_notes.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        launch {
            context.let {
               val notes =  NoteDataBase(it!!).getNoteDao().getallNotes()
                recycler_view_notes.adapter = NotesAdapter(notes)
            }

        }

        button_add.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNotesFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
                true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                showAreYouSureDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
                this,  // LifecycleOwner
                callback)
    }

    private fun showAreYouSureDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure to close?")
            setPositiveButton("yes") { _,_->
               activity!!.finish()
            }
            setNegativeButton("No"){_,_->
            }
        }.create().show()
    }




}