package com.umar.tajlifee.categoridetailadpter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.dbCategori.DatabaseManager
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment() : Fragment(R.layout.fragment_categori_detal), CategoriDetailAdapter.Listener {
    private lateinit var categoryDao: CategoriDao
    private val adapter = CategoriDetailAdapter(this)
    private val ARG_CATEGORY_ID = "categoryId"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewdetal)
        recyclerView.adapter = adapter

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearchdetal)
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
                            val categoryId = arguments?.getInt(ARG_CATEGORY_ID, -1) ?: -1
                            categoryDao.getCategoriesByParentId(categoryId)
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
        val categoryId = arguments?.getInt(ARG_CATEGORY_ID, -1) ?: -1
        if (categoryId != -1) {
            val dataFromDatabase = withContext(Dispatchers.IO) {
                categoryDao.getCategoriesByParentId(categoryId)
            }
            adapter.updateItems(dataFromDatabase)
        }

    }

    override fun onClick(item: EntityCategoriModel) {

    }
}
