package com.jaloliddinabdullaev.coroutineexplained.ui.db

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.jaloliddinabdullaev.coroutineexplained.R
import com.jaloliddinabdullaev.coroutineexplained.`interface`.OnSubmitCLicked
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.NoteItem
import com.jaloliddinabdullaev.coroutineexplained.databinding.FragmentRoomFragmentBinding
import com.jaloliddinabdullaev.coroutineexplained.ui.adapter.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentRoom : Fragment(), NoteAdapter.OnClickListener {

    companion object {
        private const val TAG = "FragmentRoom"
    }

    private var _binding: FragmentRoomFragmentBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: FragmentRoomViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.apply {
            addFab.extend()
            noteViewModel.getAllNotes.observe(viewLifecycleOwner) {
                recyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    noteAdapter = NoteAdapter(it, this@FragmentRoom)
                    adapter = noteAdapter
                    ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
                    noteList = it
                    setHasFixedSize(true)
                }
            }
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        //scroll up
                        addFab.extend()
                    } else {
                        addFab.shrink()
                    }
                }
            })

            addFab.setOnClickListener {
                showAlertWithTextInputLayout(requireContext(), null)
            }


        }
    }


    override fun onItemClicked(item: NoteItem) {
        showAlertWithTextInputLayout(requireContext(), item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                noteAdapter.filter.filter(p0)
                return false
            }

        })
    }

    private fun showAlertWithTextInputLayout(context: Context, item: NoteItem?) {
        val linearLayout = LinearLayoutCompat(context)

        val lp: LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayoutCompat.VERTICAL
        lp.setMargins(200, 0, 200, 0)
        linearLayout.layoutParams = lp


        val title = EditText(context)
        title.hint = "title"

        val input = EditText(context)
        input.hint = "description"
        item?.let {
            title.setText(item.title)
            input.setText(item.description)
        }
        linearLayout.addView(title)
        linearLayout.addView(input)
        val alert = AlertDialog.Builder(context)
            .setTitle("Add Note")
            .setView(linearLayout)
            .setMessage("Please add note and its description")
            .setPositiveButton("Submit") { dialog, _ ->
                if (item != null) {
                    noteViewModel.updateNote(
                        title.text.toString(),
                        input.text.toString(),
                        item.date,
                        item.id
                    )
                } else {
                    val noteItem = NoteItem()
                    noteItem.title = title.text.toString()
                    noteItem.description = input.text.toString()
                    noteViewModel.insertNote(noteItem)
                }
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()
    }

    private val itemTouchHelper: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote(noteList[viewHolder.adapterPosition].id)
            }

        }

    private lateinit var noteList :List<NoteItem>


}