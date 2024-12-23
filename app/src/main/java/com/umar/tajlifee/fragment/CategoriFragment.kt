package com.umar.tajlifee.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.umar.tajlifee.Categori_Detal_Activity
import com.umar.tajlifee.R
import com.umar.tajlifee.adapter.CategoryAdapter
import com.umar.tajlifee.categori.dbCategory.DatabaseManager
import com.umar.tajlifee.categori.dbCategory.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategory.entity.EntityCategoriModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class CategoryFragment : Fragment(R.layout.fragment_categori), CategoryAdapter.Listener, MenuProvider {
    private lateinit var categoryDao: CategoriDao
    private val adapter = CategoryAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.CREATED)

        val appCompatActivity = requireActivity() as AppCompatActivity
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        appCompatActivity.setSupportActionBar(toolbar)


        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            view.findViewById<ImageView>(R.id.imageViewInfo).setImageResource(R.drawable.banner)
        } else {
            view.findViewById<ImageView>(R.id.imageViewInfo).setImageResource(R.drawable.banner_night)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
        recyclerView.adapter = adapter


        val db = DatabaseManager.getDatabase(requireContext())
        categoryDao = db.categoryDao()

        lifecycleScope.launch {
            addDataToDatabase()
        }

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
        intent.putExtra("categoryName", item.title)
        startActivity(intent)
    }

    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView: SearchView = menu.findItem(R.id.search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.toLowerCase(Locale.getDefault())
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
                return false
            }
        })
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return  when (menuItem.itemId) {

            R.id.search -> {
                true
            }

            R.id.dark_mode -> {
                if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }

            else -> false
        }
    }

}