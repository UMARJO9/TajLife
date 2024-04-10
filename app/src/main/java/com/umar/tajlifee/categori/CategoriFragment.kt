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
import com.umar.tajlifee.categori.model.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.umar.tajlifee.AppDatabase
import com.umar.tajlifee.CategoryDao

class ChatFragment : Fragment(R.layout.fragment_categori), ChatsAdapter.Listener {
    private lateinit var db: AppDatabase
    private lateinit var categoryDao: CategoryDao
    private val adapter = ChatsAdapter(this)
    private var isDataLoaded = false // флаг, чтобы убедиться, что данные были загружены только один раз

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
        recyclerView.adapter = adapter

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearch)
        searchEditText.setBackgroundResource(0)

        // Инициализация базы данных и DAO
        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database").build()
        categoryDao = db.categoryDao()

        // Добавление данных в базу данных и обновление RecyclerView
        lifecycleScope.launch {
            addDataToDatabase()
            if (!isDataLoaded) { // Проверяем, загружали ли мы данные ранее
                removeDuplicateData()
                isDataLoaded = true // Устанавливаем флаг в true, чтобы не вызывать метод снова
            }

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
        // Проверяем, есть ли уже данные в таблице
        val existingDataCount = categoryDao.getCategoriesCount()

        if (existingDataCount == 0) {
            // Если таблица пуста, добавляем данные
            val data = listOf(
                CategoryEntity(1, "История", R.drawable.history),
                CategoryEntity(2, "Города", R.drawable.town),
                CategoryEntity(3, "Туристически места", R.drawable.turist)
            )

            data.forEach { categoryModel ->
                val categoryEntity = CategoryEntity(
                    name = categoryModel.name,
                    imageResId = categoryModel.imageResId
                )
                categoryDao.insertCategory(categoryEntity)
            }
        }
    }
    private suspend fun removeDuplicateData() {
        // Получаем список всех категорий
        val allCategories = categoryDao.getAllCategories()

        // Удаляем дубликаты из списка allCategories по полю id
        val uniqueCategories = allCategories.distinctBy { it.id }

        // Удаляем все категории из базы данных
        categoryDao.deleteAllCategories()

        // Добавляем уникальные категории обратно в базу данных
        uniqueCategories.forEach { uniqueCategory ->
            categoryDao.insertCategory(uniqueCategory)
        }
    }


    override fun onClick(item: CategoryEntity) {
        // Обработка клика по элементу списка
    }
}
