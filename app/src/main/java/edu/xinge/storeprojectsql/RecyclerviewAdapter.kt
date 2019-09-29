package edu.xinge.storeprojectsql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.recycler_item_layout.view.*

class RecyclerviewAdapter(val itemModelArray: ArrayList<itemModel>) : RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent, false)

        if(!God.isValid){
            v.cartAddButton.visibility = View.INVISIBLE
            v.cartAddButton.text = "YOU ARENT SUPPOSED TO SEE THIS"
        }else{
            v.cartAddButton.visibility = View.VISIBLE
            v.cartAddButton.text = "ADD 1 TO CART"
        }

        v.cartAddButton.setOnClickListener{
            God.spellToast(v.context,"ADDED")
        }

        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RecyclerviewAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemModelArray[position])
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return itemModelArray.size
    }
    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: itemModel){
            val name = itemView.findViewById(R.id.itemName)  as TextView
            val description = itemView.findViewById(R.id.itemDesc)  as TextView
            val count = itemView.findViewById(R.id.itemCount)  as TextView
            val price = itemView.findViewById(R.id.itemPrice)  as TextView
            val store = itemView.findViewById(R.id.itemStore)  as TextView
            val image = itemView.findViewById(R.id.itemImage) as ImageView
//            val youtubeid = itemView.findViewById(R.id.youtubeText)  as TextView
//            val itempic = itemView.findViewById(R.id.itemPic)  as ImageView
            name.text = item.ItemName
            description.text = item.itemDesc
            count.text = item.itemCount.toString()
            price.text = item.itemPrice.toString()
            store.text = item.itemStore
            Ion.with(image)
                .placeholder(R.drawable.rect_progress)
                .error(R.drawable.rect_error).animateIn(R.anim.fade_in)
                .load(God.HOST+item.itemPhotoURL);
        }

    }
}
