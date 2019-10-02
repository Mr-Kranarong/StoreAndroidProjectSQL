package edu.xinge.storeprojectsql

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dd.processbutton.iml.ActionProcessButton
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import java.util.*
import kotlin.concurrent.schedule


class RecyclerviewCartAdapter(val cartModelArray: ArrayList<cartModel>) : RecyclerView.Adapter<RecyclerviewCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewCartAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_cart_layout, parent, false)

//        if(!God.isValid){
//            v.cartAddButton.visibility = View.INVISIBLE
//            v.cartAddButton.text = "YOU ARENT SUPPOSED TO SEE THIS"
//        }else{
//            v.cartAddButton.visibility = View.VISIBLE
//            v.cartAddButton.text = "ADD 1 TO CART"
//        }
//
//        v.cartAddButton.setOnClickListener{
//            God.spellToast(v.context,"ADDED")
//        }

        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RecyclerviewCartAdapter.ViewHolder, position: Int) {
        holder.bindItems(cartModelArray[position])
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return cartModelArray.size
    }
    //the class is hodling the list view
    class ViewHolder(cartView: View) : RecyclerView.ViewHolder(cartView) {
        fun bindItems(cart: cartModel){
            val name = itemView.findViewById(R.id.ItemName) as TextView
            val description = itemView.findViewById(R.id.ItemDesc)  as TextView
            val price = itemView.findViewById(R.id.Price)  as TextView
            val count = itemView.findViewById(R.id.Count)  as TextView
            val photo = itemView.findViewById(R.id.ItemImage)  as ImageView
            val total = itemView.findViewById(R.id.Total)  as TextView
            val changeCount = itemView.findViewById(R.id.changeCountButton) as ActionProcessButton
            val removeItem = itemView.findViewById(R.id.removeFromCartButton) as ActionProcessButton
//            val youtubeid = itemView.findViewById(R.id.youtubeText)  as TextView
//            val itempic = itemView.findViewById(R.id.itemPic)  as ImageView

            name.text = cart.ItemName
            description.text = cart.ItemDescription
            price.text = cart.ItemPrice.toString()
            count.text = cart.Count.toString()
            total.text = (cart.ItemPrice*cart.Count).toString()
            Ion.with(photo)
                .placeholder(R.drawable.rect_progress)
                .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
                .load(God.HOST+cart.ItemImage)


            photo.setOnClickListener {
                val ItemImage = ImageView(itemView.context)
                ItemImage.setImageDrawable(photo.drawable)
                val builder = AlertDialog.Builder(itemView.context).setTitle(cart.ItemName)
                builder.setView(ItemImage)
                builder.show()
            }
            changeCount.setOnClickListener {
//                God.getCountDialog(itemView.context,cart.ItemID.toString(),"?UPDATE", "UPDATE CART")
//                Ion.with(itemView.context)
//                    .load(God.HOST+"/cart.php?checkCount")
//                    .setBodyParameter( "OwnerID", God.CurrentUserID)
//                    .setBodyParameter("ItemID", cart.ItemID.toString())
//                    .asString()
//                    .setCallback(FutureCallback<String> { e, result ->
//                        if(e != null){
//                            God.spellToast(itemView.context,e.message)
//                        }else{
//                            if(result.toString().contains("ERROR")){
//                                God.spellToast(itemView.context,"Invalid Request - Contact Administrator")
//                            }else{
//                                //God.spellToast(itemView.context,result.toString())
//                                count.text = result.toString()
//                                total.text = (result.toString().toInt()*cart.ItemPrice).toString()
//                            }
//                        }
//                    }).get()
//                Timer().schedule(1000){
//                    itemView.context.startActivity(Intent(itemView.context, MainActivity::class.java))
//                    (itemView.context as Activity).finishAffinity()
//                }
                changeCount.progress = 1

                val InputTextbox = EditText(itemView.context)
                InputTextbox.inputType = InputType.TYPE_CLASS_NUMBER

                val builder = AlertDialog.Builder(itemView.context).setTitle("How many items you want?")
                builder.setView(InputTextbox)
                    .setPositiveButton("UPDATE CART", { _,_->
                        Ion.with(itemView.context)
                            .load(God.HOST+"/cart.php?UPDATE")
                            .setBodyParameter( "OwnerID", God.CurrentUserID)
                            .setBodyParameter("ItemID", cart.ItemID.toString())
                            .setBodyParameter( "Count",InputTextbox.text.toString())
                            .asString()
                            .setCallback(FutureCallback<String> { e, result ->
                                if(e != null){
                                    God.spellToast(itemView.context,e.message)
                                    changeCount.progress = -1
                                }else{
                                    if(result.toString().contains("ERROR")){
                                        God.spellToast(itemView.context,"Invalid Request - Contact Administrator")
                                        changeCount.progress = -1
                                    }else{
                                        //God.spellToast(itemView.context,result.toString())
                                        count.text = result.toString()
                                        total.text = (result.toString().toInt()*cart.ItemPrice).toString()
                                        changeCount.progress = 0
                                    }
                                }
                            }).get()
                    })
                    .setNegativeButton("CANCEL", { _, _ ->
                        changeCount.progress = 0
                    }).show()
            }
            removeItem.setOnClickListener {
                removeItem.progress =1
                Ion.with(itemView.context)
                    .load(God.HOST+"/cart.php?DELETE")
                    .setBodyParameter( "OwnerID", God.CurrentUserID)
                    .setBodyParameter("ItemID", cart.ItemID.toString())
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(itemView.context,e.message)
                            removeItem.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(itemView.context,"Invalid Request - Contact Administrator")
                                removeItem.progress =-1
                            }else{
                                God.spellToast(itemView.context,result.toString())
                                removeItem.progress =100
                            }
                        }
                    }).get()
                Timer().schedule(1000){
                    itemView.context.startActivity(Intent(itemView.context, MainActivity::class.java))
                    (itemView.context as Activity).finishAffinity()
                }
            }
        }

    }

}