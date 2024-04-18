package com.umar.tajlifee.categori

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.Categori_Detal_Activity
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.adapter.ChatsAdapter
import com.umar.tajlifee.categori.dbCategori.DatabaseManager
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryFragment : Fragment(R.layout.fragment_categori), ChatsAdapter.Listener {
    private lateinit var categoryDao: CategoriDao
    private val adapter = ChatsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
        recyclerView.adapter = adapter

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearch)
        searchEditText.setBackgroundResource(0)

        val db = DatabaseManager.getDatabase(requireContext())
        categoryDao = db.categoryDao()

        lifecycleScope.launch {
            addDataToDatabase()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase()
                lifecycleScope.launch {
                    val filteredData = withContext(Dispatchers.IO) {
                        if (searchText.isNullOrEmpty()) {
                            categoryDao.getCategoriesWithIsStart(1)
                        } else {
                            categoryDao.searchCategories(searchText)
                        }
                    }
                    adapter.updateItems(filteredData)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private suspend fun addDataToDatabase() {
        val dataFromDatabase = withContext(Dispatchers.IO) {
            categoryDao.getCategoriesWithIsStart(1)
        }

        adapter.updateItems(dataFromDatabase)
    }


    override fun onItemClick(item: EntityCategoriModel) {
        val intent = Intent(context, Categori_Detal_Activity::class.java)
        intent.putExtra("categoryId", item.id)
        startActivity(intent)
    }

}