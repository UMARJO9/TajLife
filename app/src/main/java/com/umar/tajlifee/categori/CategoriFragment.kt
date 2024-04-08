package com.umar.tajlifee.categori

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.categori.adapter.ChatsAdapter
import com.umar.tajlifee.categori.model.CategoriModelt
import com.umar.tajlifee.R

class ChatFragment : Fragment(R.layout.fragment_categori), ChatsAdapter.Listener {

    private val recyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.recyclerViewChat)
    }
    private val adapter = ChatsAdapter(this)

    private val data = mutableListOf(
        CategoriModelt("История", R.drawable.history),
        CategoriModelt("Города", R.drawable.town),
        CategoriModelt("Туристически места", R.drawable.turist),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        adapter.updateItems(data)

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearch)
        searchEditText?.setBackgroundResource(0)

        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase()
                val filteredData = data.filter {
                    it.chatNames.toLowerCase().contains(searchText)
                }
                adapter.updateItems(filteredData)
            }

            override fun afterTextChanged(s: Editable?) {
                // Не нужно ничего делать
            }
        })
    }

    override fun onClick(item: CategoriModelt) {
        TODO("Not yet implemented")
    }
}
