package edu.xinge.storeprojectsql

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.muddzdev.styleabletoast.StyleableToast
import org.json.JSONArray

object God {

    val HOST = "https://justdontdoit.000webhostapp.com"
    var CurrentUserID : String ?= null
    var AuthKey : String ?= null
    var isValid: Boolean = false
    var hasStore: Boolean = false

    var cacheFullName: String ?= null
    var cacheEmail: String ?= null
    var cacheAddress: String ?= null
    var cachePassword: String ?= null
    var cacheFund: String ?= null




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
            CurrentUserID = Key
            isValid = true
        }

        hasStore = hasStore(context)

        return isValid
    }

    fun sessionDestroy(){
        CurrentUserID = ""
        AuthKey = ""
        isValid = false
        hasStore = false
    }

    fun hasStore(context: Context):Boolean{
        var Returnee = false
        Ion.with(context)
            .load(HOST+"/quickCheck.php?hasStore")
            .setBodyParameter( "OwnerID", CurrentUserID)
            .asString()
            .setCallback(FutureCallback<String> { e, result ->
                if(e != null){
                    spellToast(context,e.message)
                }else{
                    if(result.toString().contains("NO")){
                        Returnee = false
                    }else if(result.toString().contains("YES")){
                        Returnee = true
                    }
                }
            }).get()
        return Returnee
    }

    fun getDetailedUserInfo(context: Context){
        Ion.with(context)
            .load(HOST+"/cacheUserInfo.php")
            .setBodyParameter( "OwnerID", CurrentUserID)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if(e != null){
                    spellToast(context,e.message)
                }

                val resulted = JSONArray(result.getAsJsonArray("UserData").toString())
                val selectedUser = resulted.getJSONObject(0)

                cacheFullName= selectedUser.getString("FullName")
                cacheEmail= selectedUser.getString("Email")
                cacheAddress= selectedUser.getString("Address")
                cachePassword= selectedUser.getString("Password")
                cacheFund= selectedUser.getString("Fund")

                spellToast(context, "UserDetailRefresh -- Finished")
            }).get()
    }

    fun getFundDialog(context: Context){
        val InputTextbox = EditText(context)
        InputTextbox.inputType = InputType.TYPE_CLASS_NUMBER

        val builder = AlertDialog.Builder(context).setTitle("Fund Management")
        builder.setView(InputTextbox)
        .setPositiveButton("DEPOSIT", { _,_->
            Ion.with(context)
                .load(HOST+"/fund.php")
                .setBodyParameter( "OwnerID", CurrentUserID)
                .setBodyParameter( "DepoFund",InputTextbox.text.toString())
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        spellToast(context,e.message)
                    }else{
                        if(result.toString().contains("ERROR")){
                            spellToast(context,"Invalid amount requested")
                        }else{spellToast(context,result.toString())}
                    }
                }).get()
        })
        .setNegativeButton("WITHDRAW", { _, _ ->
            Ion.with(context)
                .load(HOST+"/fund.php")
                .setBodyParameter( "OwnerID", CurrentUserID)
                .setBodyParameter( "WithFund",InputTextbox.text.toString())
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        spellToast(context,e.message)
                    }else{
                        if(result.toString().contains("ERROR")){
                            spellToast(context,"Invalid amount requested")
                        }else{spellToast(context,result.toString())}
                    }
                }).get()
        }).create()
        builder.show()
    }

}