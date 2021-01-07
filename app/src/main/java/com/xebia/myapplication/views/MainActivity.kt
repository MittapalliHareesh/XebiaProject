package com.xebia.myapplication.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.xebia.myapplication.R
import com.xebia.myapplication.adapter.ImageGalleryAdapter
import com.xebia.myapplication.model.AppConstants
import com.xebia.myapplication.model.Entry
import com.xebia.myapplication.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var imageAdapter: ImageGalleryAdapter
    private lateinit var data: ArrayList<Entry>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.actionSortPub -> sortImagesBasedOnPubDate()
            R.id.actionSortUpd -> sortImagesBasedOnUpdDate()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        data = ArrayList()
        initialization()
    }

    private fun initialization() {
        rc_view.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            imageAdapter =
                ImageGalleryAdapter(data, object : ImageGalleryAdapter.OnEntryClickListener {
                    override fun onClickEntry(entry: Entry) {
                        var intent = Intent(this@MainActivity, ImageDescription::class.java)
                        intent.putExtra(AppConstants.key, entry)
                        startActivity(intent)
                    }
                })
            adapter = imageAdapter
        }
        viewModelInitialization()
    }

    private fun viewModelInitialization() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.progressBarStatus.observe(this, Observer {
            if (it.status)
                progress_bar.visibility = View.VISIBLE
            else {
                progress_bar.visibility = View.GONE
                if (it.message.isNotEmpty()) showErrorDialog()
            }
        })
        viewModel.getImagesList()
        viewModel.entryList.observe(this, Observer {
            this.data.clear()
            this.data.addAll(it)
            rc_view.adapter?.notifyDataSetChanged()
        })
    }

    private fun sortImagesBasedOnPubDate(): Boolean {
        if (data.isNotEmpty())
            notifyToAdapter(ArrayList(data.sortedBy { it.publishedDt }))
        return true
    }

    private fun sortImagesBasedOnUpdDate(): Boolean {
        if (data.isNotEmpty())
            notifyToAdapter(ArrayList(data.sortedBy { it.updatedDt }))
        return true
    }

    private fun notifyToAdapter(sortedData: ArrayList<Entry>) {
        this.data.clear()
        this.data.addAll(sortedData)
        rc_view.adapter?.notifyDataSetChanged()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.errorMessage))
        builder.setPositiveButton("OK") { _, _ -> finish() }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}