package com.umar.tajlifee

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import com.bumptech.glide.Glide
import com.umar.tajlifee.categori.dbCategory.DatabaseManager
import com.umar.tajlifee.categori.dbCategory.entity.EntityInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Information_Activity : AppCompatActivity(), MenuProvider {

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)


        imageView = findViewById(R.id.imageViewInfo)
        textView = findViewById(R.id.information)

        val detailId = intent.getIntExtra("detailId", -1)
        loadCategoryDetails(detailId)
    }

    private fun loadCategoryDetails(detailId: Int) {
        GlobalScope.launch {
            val informatione = getCategoryFromDatabase(detailId)
            informatione?.let { displayCategoryDetails(it) }
        }
    }

    private suspend fun getCategoryFromDatabase(detailId: Int): EntityInformation? {
        return withContext(Dispatchers.IO) {
            val db = DatabaseManager.getDatabase(this@Information_Activity)
            val informationDao = db.InformationDao()
            return@withContext informationDao.getInformationByDetailId(detailId)
        }
    }


    private fun displayCategoryDetails(information: EntityInformation) {
        runOnUiThread {

            textView.text = information.information


            val imagePathFromDatabase = information.images_url

            val fullImagePath = "file:///android_asset/$imagePathFromDatabase"

            Glide.with(this@Information_Activity)
                .load(fullImagePath)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.eror)
                .into(imageView)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> false
        }
    }
}