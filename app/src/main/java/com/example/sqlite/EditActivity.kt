package com.example.sqlite

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sqlite.database.DataBaseManager
import com.example.sqlite.database.IntentConstants
import com.example.sqlite.databinding.EditActivityBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    val dataBaseManager = DataBaseManager(this)
    val imageRequestCode=10
    var editState = false
    var id = 0
    var imageUri = "empty"
    lateinit var bindingClass : EditActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = EditActivityBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Clean area"

        getMyIntent()
        time()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBaseManager.closeDataBase()
    }

    override fun onResume() {
        super.onResume()
        dataBaseManager.openDataBase()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode==imageRequestCode){
            bindingClass.imageView.setImageURI(data?.data)
            imageUri=data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    }

    fun onClickAddImage(view: View) {
        bindingClass.imageLayout.visibility = View.VISIBLE
        bindingClass.fbAddImage.visibility=View.GONE

    }

    fun onClickDeleteImage(view: View) {
        bindingClass.imageLayout.visibility= View.GONE
        bindingClass.fbAddImage.visibility=View.VISIBLE
        imageUri = "empty"
    }

    fun onClickChooseImage (view: View){
        val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
        i.type = "image/*"
        startActivityForResult(i, imageRequestCode)
    }

    fun onClickSave(view: View){
        val phone = bindingClass.edPhone.text.toString()
        val name = bindingClass.edName.text.toString()
        val address = bindingClass.edAdress.text.toString()
        val work = bindingClass.edWork.text.toString()
        if (phone != "" && name != "" && address != "" && work != ""){
            if (editState){
                dataBaseManager.updateDataBase(phone, name, address, work, imageUri, id, time())
            } else{
                dataBaseManager.insertDataBase(phone, name, address, work, imageUri, time())
            }
            toastSave()

            finish()
        } else {
            bindingClass.apply {
                if (edPhone.text.isNullOrEmpty()) edPhone.error ="Поле обовязкове для заповнення"
                if (edName.text.isNullOrEmpty()) edName.error = "Поле обовязкове для заповнення"
                if (edAdress.text.isNullOrEmpty()) edAdress.error = "Поле обовязкове для заповнення"
                if (edWork.text.isNullOrEmpty()) edWork.error = "Поле обовязкове для заповнення"

            }
        }
    }

    fun getMyIntent(){
        val i = intent
        if (i != null){
            if (i.getStringExtra(IntentConstants.I_NUMBER_KEY) != null){
                bindingClass.fbAddImage.visibility=View.GONE
                bindingClass.edPhone.setText(i.getStringExtra(IntentConstants.I_NUMBER_KEY))
                editState=true
                bindingClass.edPhone.isEnabled = false
                bindingClass.edName.isEnabled=false
                bindingClass.edAdress.isEnabled=false
                bindingClass.edWork.isEnabled= false
                bindingClass.fbEditText.visibility= View.VISIBLE
                bindingClass.edName.setText(i.getStringExtra(IntentConstants.I_NAME_KEY))
                bindingClass.edAdress.setText(i.getStringExtra(IntentConstants.I_ADDRESS_KEY))
                bindingClass.edWork.setText(i.getStringExtra(IntentConstants.I_WORK_KEY))
                id = i.getIntExtra(IntentConstants.I_ID_KEY, 0)
                if (i.getStringExtra(IntentConstants.I_URI_KEY) != "empty"){
                    bindingClass.imageLayout.visibility = View.VISIBLE
                    imageUri = i.getStringExtra(IntentConstants.I_URI_KEY)!!
                    bindingClass.imageView.setImageURI(Uri.parse(imageUri))
                    bindingClass.imBtDeleteImage.visibility = View.GONE
                    bindingClass.imageBtEditImage.visibility = View.GONE

                }
            }
        }
    }

    fun onClickEditText(view: View) {
        toast()
        bindingClass.edPhone.isEnabled = true
        bindingClass.edName.isEnabled=true
        bindingClass.edAdress.isEnabled=true
        bindingClass.edWork.isEnabled= true
        bindingClass.fbAddImage.visibility = View.VISIBLE
        bindingClass.fbEditText.visibility = View.GONE
        if (imageUri== "empty") return
        bindingClass.imBtDeleteImage.visibility = View.VISIBLE
        bindingClass.imageBtEditImage.visibility = View.VISIBLE
    }

    fun time(): String {
        val time1 = Calendar.getInstance().time
        val format1 = SimpleDateFormat("dd-MM-yy, kk:mm", Locale.getDefault())
        return format1.format(time1)
    }

    private fun toast (){
        val inflater = layoutInflater
        val container = findViewById<ViewGroup>(R.id.c_l)
        val layout: View = inflater.inflate(R.layout.custom_toast, container)
        val text = layout.findViewById<TextView>(R.id.textView)
        text.text = "Редагування"
        with(Toast(applicationContext)){
            duration=Toast.LENGTH_SHORT
            setView(layout)
            show()
        }

    }

    private fun toastSave (){
        val inflater = layoutInflater
        val container = findViewById<ViewGroup>(R.id.c_l2)
        val layout: View = inflater.inflate(R.layout.custom_toast2, container)
        val text = layout.findViewById<TextView>(R.id.textView)
        text.text = "Збережено"
        with(Toast(applicationContext)){
            duration=Toast.LENGTH_SHORT
            setView(layout)
            show()
        }

    }


}