package edu.xinge.storeprojectsql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMainItem()
    }

    fun loadMainItem(){
        Ion.with(this)
            .load(God.HOST)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(this,e.message)
                }

                val resulted: JSONArray = JSONArray(result.getAsJsonArray("ItemList").toString())
                val itemModelArray = ArrayList<itemModel>()
                for(i in 0 until(resulted.length())){
                    val item = resulted.getJSONObject(i)

                    val ItemID = item.getInt("ItemID")
                    val Name = item.getString("Name")
                    val Description = item.getString("Description")
                    val Price = item.getInt("Price")
                    val Count = item.getInt("Count")
                    val Store = item.getString("Store")
                    val PhotoURL = item.getString("PhotoURL")

                    itemModelArray.add(itemModel(ItemID,Name,Description,Price,Count,Store,PhotoURL))
                }

                shimmerRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                shimmerRecycler.adapter = RecyclerviewAdapter(itemModelArray)
                God.spellToast(this, "Load Market Items -- Finished")
            })
    }
}
