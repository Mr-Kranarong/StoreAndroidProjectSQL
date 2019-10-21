package edu.xinge.storeprojectsql


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.fragment_your_store.*
import kotlinx.android.synthetic.main.fragment_your_store.view.*
import org.json.JSONArray
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class YourStoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_your_store, container, false)

        var temName:String = ""
        var temDesc:String = ""
        var temImgURL:String = ""



        Ion.with(context)
            .load(God.HOST+"/storeAPI.php?getStore")
            .setBodyParameter( "OwnerID", God.CurrentUserID)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(activity!!.applicationContext,e.message)
                }

                val resulted = JSONArray(result.getAsJsonArray("StoreData").toString())
                val selectedUser = resulted.getJSONObject(0)

                temName = selectedUser.getString("Name")
                temDesc = selectedUser.getString("Description")
                temImgURL = selectedUser.getString("PhotoURL")

                storeName.setText(temName)
                storeDescription.setText(temDesc)
                Ion.with(storeImg)
                    .placeholder(R.drawable.rect_progress)
                    .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
                    .load(God.HOST+temImgURL)

                God.spellToast(activity!!.applicationContext, "Load Store Info -- Done")
            }).tryGet()

        loadList(God.CurrentUserID)

        v.storeEdit.setOnClickListener {
            val intent = Intent(v.context, StoreEditActivity::class.java)
            intent.putExtra("Name",temName)
            intent.putExtra("Description",temDesc)
            intent.putExtra("PhotoURL",temImgURL)

            v.context.startActivity(intent)
        }

        v.itemAdd.setOnClickListener {
            val intent = Intent(v.context, ItemEditActivity::class.java)
            intent.putExtra("ADD",true)
            v.context.startActivity(intent)
        }

        return v
    }

    fun loadList(str:String ?= ""){
        Ion.with(this)
            .load(God.HOST+"/storeAPI.php?getList")
            .setBodyParameter("OwnerID",str)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(activity!!.applicationContext,e.message)
                }

                val resulted = JSONArray(result.getAsJsonArray("StoreList").toString())
                val storeModelArray = ArrayList<storeListModel>()
                for(i in 0 until(resulted.length())){
                    val list = resulted.getJSONObject(i)

                    val IteID = list.getInt("ItemID")
                    val ItemImg = list.getString("ItemImage")
                    val ItemN = list.getString("ItemName")
                    val ItemDes = list.getString("ItemDescription")
                    val Pric = list.getInt("ItemPrice")
                    val Coun = list.getInt("ItemCount")

                    storeModelArray.add(storeListModel(IteID,ItemImg,ItemN,ItemDes,Pric,Coun))
                }

                shimmerRecycler4.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL,false)
                shimmerRecycler4.adapter = RecyclerviewListAdapter(storeModelArray)
                God.spellToast(activity!!.applicationContext, "Load Items-- Finished")
            }).tryGet()
    }

}
