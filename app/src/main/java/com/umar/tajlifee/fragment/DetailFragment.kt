package com.umar.tajlifee.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.Information_Activity
import com.umar.tajlifee.R
import com.umar.tajlifee.adapter.CategoriDetailAdapter
import com.umar.tajlifee.categori.dbCategory.DatabaseManager
import com.umar.tajlifee.categori.dbCategory.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategory.entity.EntityCategoriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class DetailFragment : Fragment(R.layout.fragment_categori_detal), CategoriDetailAdapter.Listener,
    MenuProvider {
    private lateinit var categoryDao: CategoriDao
    private val adapter = CategoriDetailAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)
        val categoryName = requireActivity().intent.getStringExtra("categoryName")
        val appCompatActivity = requireActivity() as AppCompatActivity
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setTitle(categoryName)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewdetal)
        recyclerView.adapter = adapter

        val db = DatabaseManager.getDatabase(requireContext())
        categoryDao = db.categoryDao()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoryId = requireActivity().intent.getIntExtra("categoryId", 1)
        lifecycleScope.launch {
            addDataToDatabase(categoryId)
        }
    }

    private suspend fun addDataToDatabase(categoryId: Int) {
        val dataFromDatabase = withContext(Dispatchers.IO) {
            categoryDao.getCategoriesByParentId(categoryId)
        }
        adapter.updateItems(dataFromDatabase)
    }


    override fun onClickDetail(detailId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val title = categoryDao.getTitleByDetailId(detailId)
            val intent = Intent(requireContext(), Information_Activity::class.java)
            intent.putExtra("detailId", detailId)
            intent.putExtra("categoryNames", title)
            startActivity(intent)
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_menu, menu)

        val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView
        val categoryId =
            requireActivity().intent.getIntExtra("categoryId", 1)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                lifecycleScope.launch {
                    val filteredData = withContext(Dispatchers.IO) {
                        when (categoryId) {
                            1 -> {
                                if (searchText.isNullOrEmpty()) {
                                    categoryDao.getCategoriesByParentId(categoryId)
                                } else {
                                    categoryDao.searchCategoriesparent1(searchText)
                                }
                            }

                            2 -> {
                                if (searchText.isNullOrEmpty()) {
                                    categoryDao.getCategoriesByParentId(categoryId)
                                } else {
                                    categoryDao.searchCategoriesparent2(searchText)
                                }
                            }

                            3 -> {
                                if (searchText.isNullOrEmpty()) {
                                    categoryDao.getCategoriesByParentId(categoryId)
                                } else {
                                    categoryDao.searchCategoriesparent3(searchText)
                                }

                            }

                            4 -> {
                                if (searchText.isNullOrEmpty()) {
                                    categoryDao.getCategoriesByParentId(categoryId)
                                } else {
                                    categoryDao.searchCategoriesparent4(searchText)
                                }

                            }

                            5 -> {
                                if (searchText.isNullOrEmpty()) {
                                    categoryDao.getCategoriesByParentId(categoryId)
                                } else {
                                    categoryDao.searchCategoriesparent5(searchText)
                                }

                            }

                            else -> {

                                emptyList<EntityCategoriModel>()
                            }
                        }
                    }
                    adapter.updateItems(filteredData)
                }
                return false
            }
        })
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }

            R.id.search -> {
                true
            }

            else -> false
        }
    }


}

