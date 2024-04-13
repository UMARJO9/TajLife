package com.umar.tajlifee.categori

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.umar.tajlifee.R
import com.umar.tajlifee.categori.adapter.ChatsAdapter
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.umar.tajlifee.categori.dbCategori.AppDatabase
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao

class ChatFragment : Fragment(R.layout.fragment_categori), ChatsAdapter.Listener {
    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoriDao
    private val adapter = ChatsAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
        recyclerView.adapter = adapter

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearch)
        searchEditText.setBackgroundResource(0)


        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database").build()
        categoryDao = db.categoryDao()


        lifecycleScope.launch {
            addDataToDatabase()
            val data = withContext(Dispatchers.IO) {
                categoryDao.getAllCategories()
            }
            adapter.updateItems(data)
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase()
                lifecycleScope.launch {
                    val filteredData = withContext(Dispatchers.IO) {
                        categoryDao.searchCategories(searchText)
                    }
                    adapter.updateItems(filteredData)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private suspend fun addDataToDatabase() {

        val existingDataCount = categoryDao.getCategoriesCount()

        if (existingDataCount == 0) {

            val data = listOf(
                EntityCategoriModel(1, "История", R.drawable.history),
                EntityCategoriModel(2, "Города", R.drawable.town),
                EntityCategoriModel(3, "Туристически места", R.drawable.turist)
            )

            data.forEach { categoryModel ->
                val categoryEntity = EntityCategoriModel(
                    name = categoryModel.name,
                    imageResId = categoryModel.imageResId
                )
                categoryDao.insertCategory(categoryEntity)
            }
        }
    }


    override fun onClick(item: EntityCategoriModel) {

    }
}
