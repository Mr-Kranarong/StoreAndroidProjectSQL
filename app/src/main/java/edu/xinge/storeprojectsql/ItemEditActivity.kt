package edu.xinge.storeprojectsql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_item_edit.*
import java.io.File
import java.util.*
import kotlin.concurrent.schedule

class ItemEditActivity : AppCompatActivity() {

    private var ITEMIMAGETO: String? = null
    private var ADD: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)
        itemImageSelect.setOnClickListener { pickImageFromGallery() }
        ItemEditActSubmit.setOnClickListener { execution() }

        ADD = intent.getBooleanExtra("ADD",false)
        if(ADD == false){

//            intent.putExtra("ItemID", list.ItemID)
//            intent.putExtra("ItemImage", list.ItemImage)
//            intent.putExtra("ItemName", list.ItemName)
//            intent.putExtra("ItemDescription", list.ItemDescription)
//            intent.putExtra("ItemPrice", list.ItemPrice)
//            intent.putExtra("ItemCount", list.ItemCount)

            itemNameBox.setText(intent.getStringExtra("ItemName"))
            itemDescBox.setText(intent.getStringExtra("ItemDescription"))
            itemPriceBox.setText(intent.getIntExtra("ItemPrice",0).toString())
            itemCountBox.setText(intent.getIntExtra("ItemCount",0).toString())
            Ion.with(itemImageSelect)
                .placeholder(R.drawable.rect_progress)
                .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
                .load(God.HOST+intent.getStringExtra("ItemImage").toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == 1000){
            super.onActivityResult(requestCode, resultCode, data)

            itemImageSelect.setImageURI(data?.data)
            ITEMIMAGETO = PathUtils.getPath(this,data!!.data)
            itemImageSelectPath.text = ITEMIMAGETO.toString()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    fun execution(){
        if(ADD==false){
            if(itemImageSelectPath.text!=""){
                ItemEditActSubmit.progress = 1
                Ion.with(this)
                    .load(God.HOST+"/uploader.php?ITEM&EDIT")
                    .setMultipartParameter( "ItemID",intent.getIntExtra("ItemID",-1).toString())
                    .setMultipartParameter( "ImageURL",intent.getStringExtra("ItemImage").toString())
                    .setMultipartParameter( "Name",itemNameBox.text.toString())
                    .setMultipartParameter( "Description",itemDescBox.text.toString())
                    .setMultipartParameter( "Price",itemPriceBox.text.toString())
                    .setMultipartParameter( "Count",itemCountBox.text.toString())
                    .setMultipartFile("filename", "image/*", File(ITEMIMAGETO))
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(this,"Shit! "+e.message+ "!!")
                            ItemEditActSubmit.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(this,"ERROR: "+result.toString())
                                ItemEditActSubmit.progress = 0
                            }else if(result.toString().contains("Everything -- Completed")){
                                God.spellToast(this,"Item Updated")
                                ItemEditActSubmit.progress = 100
                                Timer().schedule(1000){
                                    startActivity(Intent(this@ItemEditActivity, MainActivity::class.java))
                                    finishAffinity()
                                }
                            }else{
                                God.spellToast(this,"Stuff happened: "+result.toString()+ "!!")
                                ItemEditActSubmit.progress = -1
                            }
                        }
                    }).tryGet()
            }else{
                ItemEditActSubmit.progress = 1
                Ion.with(this)
                    .load(God.HOST+"/itemInfoUpdate.php")
                    .setBodyParameter( "ItemID",intent.getIntExtra("ItemID",-1).toString())
                    .setBodyParameter( "Name",itemNameBox.text.toString())
                    .setBodyParameter( "Description",itemDescBox.text.toString())
                    .setBodyParameter( "Price",itemPriceBox.text.toString())
                    .setBodyParameter( "Count",itemCountBox.text.toString())
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(this,e.message)
                            ItemEditActSubmit.progress = -1
                        }
                        if(result.toString() == "Item Update -- Completed"){
                            ItemEditActSubmit.progress = 100
                            God.spellToast(this,"Item Updated")
                            Timer().schedule(1000){
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finishAffinity()
                            }
                        }else{
                            ItemEditActSubmit.progress = 0
                            God.spellToast(this,result.toString())
                        }
                    }).tryGet()
            }
        }else{
            if(itemNameBox.text.toString()!=""&&itemDescBox.text.toString()!=""&&itemPriceBox.text.toString()!=""&&itemCountBox.text.toString()!=""&&itemImageSelectPath.text.toString()!=""){
                ItemEditActSubmit.progress = 1
                Ion.with(this)
                    .load(God.HOST+"/uploader.php?ITEM")
                    .setMultipartParameter("OwnerID", God.CurrentUserID)
                    .setMultipartParameter( "Name",itemNameBox.text.toString())
                    .setMultipartParameter( "Description",itemDescBox.text.toString())
                    .setMultipartParameter( "Price",itemPriceBox.text.toString())
                    .setMultipartParameter( "Count",itemCountBox.text.toString())
                    .setMultipartFile("filename", "image/*", File(ITEMIMAGETO))
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(this,"Shit! "+e.message+ "!!")
                            ItemEditActSubmit.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(this,"ERROR: "+result.toString())
                                ItemEditActSubmit.progress = 0
                            }else if(result.toString().contains("Item Listing -- Completed")){
                                God.spellToast(this,"Item Listed")
                                ItemEditActSubmit.progress = 100
                                Timer().schedule(1000){
                                    startActivity(Intent(this@ItemEditActivity, MainActivity::class.java))
                                    finishAffinity()
                                }
                            }else{
                                God.spellToast(this,"Stuff happened: "+result.toString()+ "!!")
                                ItemEditActSubmit.progress = -1
                            }
                        }
                    }).tryGet()
            }else{God.spellToast(this,"Please make sure you did everything correctly!")}
        }
    }
}
