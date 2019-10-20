package edu.xinge.storeprojectsql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_store_edit.*
import java.io.File
import java.util.*
import kotlin.concurrent.schedule

class StoreEditActivity : AppCompatActivity() {

    private var IMAGEPATHODIT: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_edit)

        intent = getIntent()
        Ion.with(storeImageEdit)
            .placeholder(R.drawable.rect_progress)
            .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
            .load(God.HOST+intent.getStringExtra("PhotoURL").toString())
        storeNameBoxEdit.setText(intent.getStringExtra("Name").toString())
        storeDescriptionBoxEdit.setText(intent.getStringExtra("Description").toString())
        storeImageEdit.setOnClickListener { pickImageFromGallery() }


        StoreEditSubmit.setOnClickListener {
            if(imagePathEdit.text!=""){
                StoreEditSubmit.progress = 1
                Ion.with(this)
                    .load(God.HOST+"/uploader.php?EDIT")
                    .setMultipartParameter("OwnerID", God.CurrentUserID)
                    .setMultipartParameter("StoreName", storeNameBoxEdit.text.toString())
                    .setMultipartParameter("StoreDescription", storeDescriptionBoxEdit.text.toString())
                    .setMultipartParameter("StorePhotoURL", intent.getStringExtra("PhotoURL").toString())
                    .setMultipartFile("filename", "image/*", File(IMAGEPATHODIT))
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(this,"Shit! "+e.message+ "!!")
                            StoreEditSubmit.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(this,"ERROR: "+result.toString())
                                StoreEditSubmit.progress = 0
                            }else if(result.toString().contains("Everything -- Completed")){
                                God.spellToast(this,"Store Updated")
                                StoreEditSubmit.progress = 100
                                Timer().schedule(1000){
                                    startActivity(Intent(this@StoreEditActivity, MainActivity::class.java))
                                    finishAffinity()
                                }
                            }else{
                                God.spellToast(this,"Stuff happened: "+result.toString()+ "!!")
                                StoreEditSubmit.progress = -1
                            }
                        }
                    }).tryGet()
            }else{
                StoreEditSubmit.progress = 1
                Ion.with(this)
                    .load(God.HOST+"/storeInfoUpdate.php")
                    .setBodyParameter( "OwnerID",God.CurrentUserID)
                    .setBodyParameter( "Name",storeNameBoxEdit.text.toString())
                    .setBodyParameter( "Description",storeDescriptionBoxEdit.text.toString())
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(this,e.message)
                            StoreEditSubmit.progress = -1
                        }
                        if(result.toString() == "Store Update -- Completed"){
                            StoreEditSubmit.progress = 100
                            God.spellToast(this,"Store Updated")
                            Timer().schedule(1000){
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                finishAffinity()
                            }
                        }else{
                            StoreEditSubmit.progress = 0
                            God.spellToast(this,result.toString())
                        }
                    }).tryGet()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == 1000){
            super.onActivityResult(requestCode, resultCode, data)

            storeImageEdit.setImageURI(data?.data)
            IMAGEPATHODIT = PathUtils.getPath(this,data!!.data)
            imagePathEdit.text = IMAGEPATHODIT.toString()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onBackPressed() {
        if(StoreEditSubmit.progress>0 && StoreEditSubmit.progress< 100){

        }else{
            super.onBackPressed()
        }

    }
}
