package edu.xinge.storeprojectsql

import android.content.Context
import android.widget.Toast
import com.muddzdev.styleabletoast.StyleableToast

object God {

    val HOST = "https://justdontdoit.000webhostapp.com"

    fun spellToast(context: Context, text: String?=null){
        StyleableToast.makeText(context, text, Toast.LENGTH_LONG, R.style.mytoast).show();
    }

}