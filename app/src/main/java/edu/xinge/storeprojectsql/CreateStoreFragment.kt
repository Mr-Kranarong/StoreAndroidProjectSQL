package edu.xinge.storeprojectsql


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.fragment_create_store.*
import kotlinx.android.synthetic.main.fragment_create_store.view.*
import java.io.File
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass.
 */
class CreateStoreFragment : Fragment() {

    private var  IMAGEPATHO: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_create_store, container, false)

        v.storeImage.setOnClickListener { pickImageFromGallery() }
        v.CreateSubmit.setOnClickListener {
            if(storeNameBox.text.toString()!=""&&storeDescriptionBox.text.toString()!=""&&imagePath.text.toString()!=""){
                CreateSubmit.progress = 1
                Ion.with(getContext())
                    .load(God.HOST+"/uploader.php")
                    .setMultipartParameter("OwnerID", God.CurrentUserID)
                    .setMultipartParameter("StoreName", storeNameBox.text.toString())
                    .setMultipartParameter("StoreDescription", storeDescriptionBox.text.toString())
                    .setMultipartFile("filename", "image/*", File(IMAGEPATHO))
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(v.context,"Shit! "+e.message+ "!!")
                            CreateSubmit.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(v.context,"ERROR: "+result.toString())
                                CreateSubmit.progress = 0
                            }else if(result.toString().contains("Store Registration -- Completed")){
                                God.spellToast(v.context,"Store creation completed")
                                CreateSubmit.progress = 100
                                Timer().schedule(1000){
                                    v.context.startActivity(Intent(v.context, MainActivity::class.java))
                                    (v.context as Activity).finishAffinity()
                                }
                            }else{
                                God.spellToast(v.context,"Stuff happened: "+result.toString()+ "!!")
                                CreateSubmit.progress = -1
                            }
                        }
                    }).tryGet()
            }else{God.spellToast(activity!!.applicationContext,"Please make sure you did everything correctly!")}
        }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000){

            storeImage.setImageURI(data?.data)

            IMAGEPATHO = PathUtils.getPath(activity!!.applicationContext,data!!.data)
            imagePath.text = IMAGEPATHO.toString()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

}
