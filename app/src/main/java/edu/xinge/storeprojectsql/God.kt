package edu.xinge.storeprojectsql

import android.content.Context
import android.widget.Toast
import com.koushikdutta.ion.Ion
import com.muddzdev.styleabletoast.StyleableToast


object God {

    val HOST = "https://justdontdoit.000webhostapp.com"
    var CurrentUser : String ?= null
    var AuthKey : String ?= null
    var isValid: Boolean = false

    fun spellToast(context: Context, text: String?=null){
        StyleableToast.makeText(context, text, Toast.LENGTH_LONG, R.style.mytoast).show();
    }

    fun checkUser(context: Context,CURRENT_AUTHKEY:String?): Boolean{

        var Key = Ion.with(context)
            .load(HOST+"/requestAuthKey.php")
            .setBodyParameter( "CHECK",CURRENT_AUTHKEY)
            .asString().get()

        if(Key == "AuthKey no longer valid -- Clearing Session"){
            AuthKey = ""
            spellToast(context,"Session no longer valid -- Restarting")
            isValid = false
        }else{
            AuthKey = CURRENT_AUTHKEY
            CurrentUser = Key
            isValid = true
        }

        return isValid
    }

    fun sessionDestroy(){
        CurrentUser = ""
        AuthKey = ""
        isValid = false
    }
}