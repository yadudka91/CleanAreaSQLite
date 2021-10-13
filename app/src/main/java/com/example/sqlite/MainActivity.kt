package com.example.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.database.Adapter
import com.example.sqlite.database.DataBaseManager
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val dataBaseManager = DataBaseManager(this)
    lateinit var bindingClass: ActivityMainBinding
    val rcAdapret = Adapter(ArrayList(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        init()
        search()
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseManager.closeDataBase()
    }

    override fun onResume() {
        super.onResume()
        dataBaseManager.openDataBase()
        fillAdapter()


    }
    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)



    }

    fun init(){
        bindingClass.rcView.layoutManager =LinearLayoutManager(this)
        val swap = swapMg()
        swap.attachToRecyclerView(bindingClass.rcView)
        bindingClass.rcView.adapter = rcAdapret
    }

    fun search (){
        var searchView = bindingClass.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.d("MyLog", "New Text : $newText")
                rcAdapret.updateAdapter(dataBaseManager.readDataBase(newText!!))
                return true
            }

        })
    }

    fun fillAdapter(){
        rcAdapret.updateAdapter(dataBaseManager.readDataBase(""))
    }

    private fun swapMg(): ItemTouchHelper{
        return ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rcAdapret.deleteItemAdapter(viewHolder.adapterPosition, dataBaseManager)
            }
        })
    }


}