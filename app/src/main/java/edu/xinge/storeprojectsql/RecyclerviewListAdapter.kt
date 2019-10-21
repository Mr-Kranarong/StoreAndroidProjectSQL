package edu.xinge.storeprojectsql

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dd.processbutton.iml.ActionProcessButton
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import java.util.*
import kotlin.concurrent.schedule

class RecyclerviewListAdapter(val listModelArray: ArrayList<storeListModel>) : RecyclerView.Adapter<RecyclerviewListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_layout, parent, false)

        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RecyclerviewListAdapter.ViewHolder, position: Int) {
        holder.bindItems(listModelArray[position])
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listModelArray.size
    }
    //the class is hodling the list view
    class ViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
        fun bindItems(list: storeListModel){

            val ItemImg = itemView.findViewById(R.id.ListItemImage)  as ImageView
            val ItemN =  itemView.findViewById(R.id.ListItemName)  as TextView
            val ItemDes = itemView.findViewById(R.id.ListItemDesc)  as TextView
            val Pric = itemView.findViewById(R.id.listPrice)  as TextView
            val Coun = itemView.findViewById(R.id.listCount)  as TextView
            val editListButt = itemView.findViewById(R.id.editListButton) as ActionProcessButton
            val removeListButt = itemView.findViewById(R.id.removeFromListButton) as ActionProcessButton

            Ion.with(ItemImg)
                .placeholder(R.drawable.rect_progress)
                .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
                .load(God.HOST+list.ItemImage)
            ItemN.text = list.ItemName
            ItemDes.text = list.ItemDescription
            Pric.text = list.ItemPrice.toString()
            Coun.text = list.ItemCount.toString()
            editListButt.setOnClickListener {
                var intent = Intent(itemView.context, ItemEditActivity::class.java)
                intent.putExtra("ItemID", list.ItemID)
                intent.putExtra("ItemImage", list.ItemImage)
                intent.putExtra("ItemName", list.ItemName)
                intent.putExtra("ItemDescription", list.ItemDescription)
                intent.putExtra("ItemPrice", list.ItemPrice)
                intent.putExtra("ItemCount", list.ItemCount)

                itemView.context.startActivity(intent)
            }
            removeListButt.setOnClickListener {
                removeListButt.progress =1
                Ion.with(itemView.context)
                    .load(God.HOST+"/itemInfoUpdate.php?DELETE")
                    .setBodyParameter("ItemID", list.ItemID.toString())
                    .setBodyParameter("ImageURL", list.ItemImage)
                    .asString()
                    .setCallback(FutureCallback<String> { e, result ->
                        if(e != null){
                            God.spellToast(itemView.context,e.message)
                            removeListButt.progress = -1
                        }else{
                            if(result.toString().contains("ERROR")){
                                God.spellToast(itemView.context,"ERROR: "+result.toString())
                                removeListButt.progress =-1
                            }else if(result.toString().contains("Item Deletion -- Completed")){
                                God.spellToast(itemView.context,"Item has been removed")
                                removeListButt.progress =100
                            }else{
                                God.spellToast(itemView.context,"Weird:"+result.toString())
                                removeListButt.progress =0
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