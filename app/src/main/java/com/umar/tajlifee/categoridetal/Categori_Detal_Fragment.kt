package com.umar.tajlifee.categoridetal

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
import com.umar.tajlifee.categori.dbCategori.AppDatabase
import com.umar.tajlifee.categori.dbCategori.dao.Caterori_Detal_DAO
import com.umar.tajlifee.categori.dbCategori.entity.Entity_Categori_Detal
import com.umar.tajlifee.categori.dbCategori.migration_1_2
import com.umar.tajlifee.categoridetal.adapter.Categori_Detal_Adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Categori_Detal_Fragment : Fragment(R.layout.fragment_categori_detal),
    Categori_Detal_Adapter.Listener {
    private lateinit var db: AppDatabase
    private lateinit var categoriDetal: Caterori_Detal_DAO
    private val adapter = Categori_Detal_Adapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewdetal)
        recyclerView.adapter = adapter

        val searchEditText = view.findViewById<EditText>(R.id.TextViewsearchdetal)
        searchEditText.setBackgroundResource(0)


        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .addMigrations(migration_1_2)
            .build()
        categoriDetal = db.Caterori_Detal_DAO()


        lifecycleScope.launch {
            addDataToDatabase()
            val data = withContext(Dispatchers.IO) {
                categoriDetal.getAllCategories()
            }
            adapter.updateItems(data)
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase()
                lifecycleScope.launch {
                    val filteredData = withContext(Dispatchers.IO) {
                        categoriDetal.searchCategories(searchText)
                    }
                    adapter.updateItems(filteredData)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private suspend fun addDataToDatabase() {

        val existingDataCount = categoriDetal.getCategoriesCount()

        if (existingDataCount == 0) {

            val data = listOf(
                Entity_Categori_Detal(detal_name = "Душанбе", detal_imageResId = R.drawable.town),
                Entity_Categori_Detal(detal_name = "Худжанд", detal_imageResId = R.drawable.town),
                Entity_Categori_Detal(detal_name = "Кӯлоб", detal_imageResId = R.drawable.town),
                Entity_Categori_Detal(detal_name = "Исфара", detal_imageResId = R.drawable.town),
                Entity_Categori_Detal(detal_name = "Панҷакент", detal_imageResId = R.drawable.town)

            )

            data.forEach { categoryModel ->
                val categoryEntity = Entity_Categori_Detal(
                    detal_name = categoryModel.detal_name,
                    detal_imageResId = categoryModel.detal_imageResId
                )
                categoriDetal.insertCategory(categoryEntity)
            }
        }
    }


    override fun onClick(item: Entity_Categori_Detal) {
        TODO("Not yet implemented")
    }
}