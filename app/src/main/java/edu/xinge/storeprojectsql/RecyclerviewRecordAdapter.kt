package edu.xinge.storeprojectsql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewRecordAdapter(val recordModelArray: ArrayList<recordModel>) : RecyclerView.Adapter<RecyclerviewRecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewRecordAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_transaction_layout, parent, false)

        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RecyclerviewRecordAdapter.ViewHolder, position: Int) {
        holder.bindItems(recordModelArray[position])
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return recordModelArray.size
    }
    //the class is hodling the list view
    class ViewHolder(recordView: View) : RecyclerView.ViewHolder(recordView) {
        fun bindItems(record: recordModel){
            val buyorsell = itemView.findViewById(R.id.BUYorSELL) as TextView
            val timestamp = itemView.findViewById(R.id.TimeStamp)  as TextView
            val name = itemView.findViewById(R.id.recordName)  as TextView
            val price = itemView.findViewById(R.id.recordPrice)  as TextView
            val count = itemView.findViewById(R.id.recordCount)  as TextView
            val total = itemView.findViewById(R.id.recordTotal) as TextView
//            val youtubeid = itemView.findViewById(R.id.youtubeText)  as TextView
//            val itempic = itemView.findViewById(R.id.itemPic)  as ImageView
            var isBuyer: String
            if(record.buyerID == God.CurrentUserID){
                isBuyer = "BOUGHT"
                buyorsell.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))

            }else{
                isBuyer = "SOLD"
                buyorsell.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }
            buyorsell.text = isBuyer
            timestamp.text = record.timestamp
            name.text = record.itemName
            price.text = record.priceEA.toString()
            count.text = record.count.toString()
            total.text = (record.priceEA*record.count).toString()

        }

    }

}