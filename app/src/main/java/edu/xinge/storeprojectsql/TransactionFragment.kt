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
import kotlinx.android.synthetic.main.fragment_transaction.*
import org.json.JSONArray
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_transaction, container, false)
        loadRecord(God.CurrentUserID)
        return v
    }

    fun loadRecord(str:String ?= ""){
        Ion.with(this)
            .load(God.HOST+"/transaction.php").setBodyParameter("OwnerID",str)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(activity!!.applicationContext,e.message)
                }

                val resulted = JSONArray(result.getAsJsonArray("RecordList").toString())
                val recordModelArray = ArrayList<recordModel>()
                for(i in 0 until(resulted.length())){
                    val record = resulted.getJSONObject(i)

                    val BuyerID = record.getString("BuyerID")
                    val SellerID = record.getString("SellerID")
                    val ItemName = record.getString("ItemName")
                    val PriceEA = record.getInt("PriceEach")
                    val Count = record.getInt("Count")
                    val TimeStamp = record.getString("TimeStamp")

                    recordModelArray.add(recordModel(BuyerID,SellerID,ItemName,PriceEA,Count,TimeStamp))
                }

                shimmerRecycler2.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL,false)
                shimmerRecycler2.adapter = RecyclerviewRecordAdapter(recordModelArray)
                God.spellToast(activity!!.applicationContext, "Load Transaction Records -- Finished")
            }).tryGet()
    }

}
