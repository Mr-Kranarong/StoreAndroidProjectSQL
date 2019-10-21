package edu.xinge.storeprojectsql


import android.app.Activity
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
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.json.JSONArray
import java.util.*
import kotlin.concurrent.schedule

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_cart, container, false)
        loadCartItem(God.CurrentUserID)
        v.clearButton.setOnClickListener {
            clearButton.progress =1
            Ion.with(v.context)
                .load(God.HOST+"/cart.php?clearCart")
                .setBodyParameter( "OwnerID", God.CurrentUserID)
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        God.spellToast(v.context,e.message)
                        clearButton.progress = -1
                    }else{
                        if(result.toString().contains("ERROR")){
                            God.spellToast(v.context,"Invalid Request - Contact Administrator")
                            clearButton.progress = 0
                        }else{
                            //God.spellToast(itemView.context,result.toString())
                            clearButton.progress = 100
                        }
                    }
                }).tryGet()
            Timer().schedule(1000){
                v.context.startActivity(Intent(v.context, MainActivity::class.java))
                (v.context as Activity).finishAffinity()
            }
        }
        v.checkoutButton.setOnClickListener {
            checkoutButton.progress =1
            Ion.with(v.context)
                .load(God.HOST+"/cart.php?checkout")
                .setBodyParameter( "OwnerID", God.CurrentUserID)
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        God.spellToast(v.context,e.message)
                        checkoutButton.progress = -1
                    }else{
                        if(result.toString().contains("ERROR")) {
                            //God.spellToast(v.context,"Invalid Request - Contact Administrator")
                            God.spellToast(v.context, result.toString())
                            checkoutButton.progress = 0
                        }else if(result.toString().contains("enough")){
                            God.spellToast(v.context, result.toString())
                            checkoutButton.progress = 0
                            Timer().schedule(500){
                                v.context.startActivity(Intent(v.context, MainActivity::class.java))
                                (v.context as Activity).finishAffinity()
                            }
                        }else{
                            God.spellToast(v.context,result.toString())
                            checkoutButton.progress = 100
                            Timer().schedule(500){
                                v.context.startActivity(Intent(v.context, MainActivity::class.java))
                                (v.context as Activity).finishAffinity()
                            }
                        }
                    }
                }).tryGet()

        }

        return v
    }


    fun loadCartItem(str:String ?= ""){
        Ion.with(this)
            .load(God.HOST+"/cartlist.php").setBodyParameter("OwnerID",str)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    God.spellToast(activity!!.applicationContext,e.message)
                }

                val resulted = JSONArray(result.getAsJsonArray("CartList").toString())
                val cartModelArray = ArrayList<cartModel>()
                for(i in 0 until(resulted.length())){
                    val cart = resulted.getJSONObject(i)

                    val ItemImage = cart.getString("ItemImage")
                    val ItemName = cart.getString("ItemName")
                    val ItemDescription = cart.getString("ItemDescription")
                    val ItemPrice = cart.getInt("ItemPrice")
                    val Count = cart.getInt("Count")
                    val ItemID = cart.getInt("ItemID")


                    cartModelArray.add(cartModel(ItemImage,ItemName,ItemDescription,ItemPrice,Count,ItemID))
                }

                shimmerRecycler3.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL,false)
                shimmerRecycler3.adapter = RecyclerviewCartAdapter(cartModelArray)
                God.spellToast(activity!!.applicationContext, "Load Cart Items -- Finished")
            }).tryGet()
    }
}
