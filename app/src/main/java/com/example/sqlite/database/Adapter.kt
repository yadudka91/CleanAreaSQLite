package com.example.sqlite.database

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.EditActivity
import com.example.sqlite.R

class Adapter(listMain: ArrayList<ListItem>, contextM: Context):RecyclerView.Adapter<Adapter.ExampleHolder>() {

    var listArray = listMain
    var context = contextM

    class ExampleHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tVnumber = itemView.findViewById<TextView>(R.id.tvNumber)
        val tVtime = itemView.findViewById<TextView>(R.id.tvTime)
        val context = contextV

        fun setData(item: ListItem){
            tVnumber.text=item.number
            tVtime.text=item.time
            itemView.setOnClickListener {
                val i = Intent(context, EditActivity::class.java).apply {
                    putExtra(IntentConstants.I_NUMBER_KEY, item.number)
                    putExtra(IntentConstants.I_NAME_KEY, item.name)
                    putExtra(IntentConstants.I_ADDRESS_KEY, item.address)
                    putExtra(IntentConstants.I_WORK_KEY, item.work)
                    putExtra(IntentConstants.I_URI_KEY, item.uri)
                    putExtra(IntentConstants.I_ID_KEY, item.id)
                }
                context.startActivity(i)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExampleHolder(inflater.inflate(R.layout.rc_view, parent, false), context)
    }
    override fun onBindViewHolder(holder: ExampleHolder, position: Int) {
        holder.setData(listArray.get(position))
    }
    override fun getItemCount(): Int {
        return listArray.size
    }
    fun updateAdapter(listItem:List<ListItem>){
        listArray.clear()
        listArray.addAll(listItem)
        notifyDataSetChanged()
    }
    fun deleteItemAdapter(pos: Int, dataBaseManager: DataBaseManager){
        dataBaseManager.deleteDataBase(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }
}