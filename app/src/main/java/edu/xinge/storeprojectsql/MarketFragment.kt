package edu.xinge.storeprojectsql


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.fragment_market.*
import org.json.JSONArray
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MarketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_market, container, false)
        loadMainItem()


        return v
    }

    fun loadMainItem(){
        Ion.with(this)
            .load(God.HOST)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(activity!!.applicationContext,e.message)
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

                    itemModelArray.add(itemModel(ItemID,Name,Description,Count,Price,Store,PhotoURL))
                }

                shimmerRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL,false)
                shimmerRecycler.adapter = RecyclerviewAdapter(itemModelArray)
                God.spellToast(activity!!.applicationContext, "Load Market Items -- Finished")
            })
    }

}
