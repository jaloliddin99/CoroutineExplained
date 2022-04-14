package com.jaloliddinabdullaev.coroutineexplained.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.NoteItem
import com.jaloliddinabdullaev.coroutineexplained.databinding.LayoutNoteItemBinding
import java.text.DateFormat
import java.util.*

class NoteAdapter(private val list: List<NoteItem>, private val listener: OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>(), Filterable {

    var noteFilterList = ArrayList<NoteItem>()
    init {
        noteFilterList = list as ArrayList<NoteItem>
    }

    inner class MyViewHolder(private val binding: LayoutNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {


        fun bind(noteItem: NoteItem) {
            binding.description.text = noteItem.description
            binding.title.text = noteItem.title
            binding.time.text = DateFormat.getDateInstance(DateFormat.FULL).format(noteItem.date)
        }

        init {
            noteFilterList = list as ArrayList<NoteItem>
            itemView.setOnLongClickListener(this)
        }


        override fun onLongClick(p0: View?): Boolean {
            listener.onItemClicked(noteFilterList[adapterPosition])
            return true
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.MyViewHolder {
        val binding =
            LayoutNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.MyViewHolder, position: Int) {
        holder.bind(noteFilterList[position])
    }

    override fun getItemCount() = noteFilterList.size

    interface OnClickListener {
        fun onItemClicked(item: NoteItem)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                noteFilterList = if (charSearch.isEmpty()) {
                    list as ArrayList<NoteItem>
                } else {
                    val resultList = ArrayList<NoteItem>()
                    for (row in list) {
                        if (row.description.lowercase(Locale.ROOT)
                            .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = noteFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                noteFilterList = results?.values as ArrayList<NoteItem>
                notifyDataSetChanged()
            }

        }
    }
}