package edu.xinge.storeprojectsql

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dd.processbutton.iml.ActionProcessButton
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.concurrent.schedule


class RegisterActivity : AppCompatActivity() {

    private var update: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerSubmit.setMode(ActionProcessButton.Mode.ENDLESS)

        update = intent.getBooleanExtra("UpdateInfo",false)
        if(update == false){
            registerSubmit.text = "Register"
            fundButton.visibility = View.INVISIBLE
            useridReg.isEnabled = true
            useridReg.setText("")
            passwordReg.setText("")
            emailReg.setText("")
            fullnameReg.setText("")
            addressReg.setText("")
        }else if(update == true){
            registerSubmit.text = "Update Information"
            fundButton.visibility = View.VISIBLE
            useridReg.isEnabled = false
            useridReg.setText(God.CurrentUserID)
            passwordReg.setText(God.cachePassword)
            emailReg.setText(God.cacheEmail)
            fullnameReg.setText(God.cacheFullName)
            addressReg.setText(God.cacheAddress)
        }

        registerSubmit.setOnClickListener{
            register()
        }
        fundButton.setOnClickListener{
            God.getFundDialog(this)
        }
    }

    override fun onBackPressed() {
        if(update==false){
            super.onBackPressed()
        }else if(update==true){
            God.spellToast(this,"Changes Cancelled")
            useridReg.setText(God.CurrentUserID)
            passwordReg.setText(God.cachePassword)
            emailReg.setText(God.cacheEmail)
            fullnameReg.setText(God.cacheFullName)
            addressReg.setText(God.cacheAddress)
            register()
        }
    }

    fun register(){

        if(update==false){
            registerSubmit.progress = 1
            Ion.with(this)
                .load(God.HOST+"/register.php")
                .setBodyParameter( "OwnerID",useridReg.text.toString())
                .setBodyParameter( "FullName",fullnameReg.text.toString())
                .setBodyParameter( "Address",addressReg.text.toString())
                .setBodyParameter( "Email",emailReg.text.toString())
                .setBodyParameter( "Password",passwordReg.text.toString())
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        God.spellToast(this,e.message)
                        registerSubmit.progress = -1
                    }
                    if(result.toString() == "Account Registration -- Completed"){
                        registerSubmit.progress = 100
                        God.spellToast(this,"Account Registration Completed")
                        Timer().schedule(1000){
                            finish()
                        }
                    }else{
                        registerSubmit.progress = 0
                        God.spellToast(this,result.toString())
                    }
                })
        }  else if(update==true){
            registerSubmit.progress = 1
            Ion.with(this)
                .load(God.HOST+"/register.php?UPDATE")
                .setBodyParameter( "OwnerID",useridReg.text.toString())
                .setBodyParameter( "FullName",fullnameReg.text.toString())
                .setBodyParameter( "Address",addressReg.text.toString())
                .setBodyParameter( "Email",emailReg.text.toString())
                .setBodyParameter( "Password",passwordReg.text.toString())
                .asString()
                .setCallback(FutureCallback<String> { e, result ->
                    if(e != null){
                        God.spellToast(this,e.message)
                        registerSubmit.progress = -1
                    }
                    if(result.toString() == "Account Update -- Completed"){
                        registerSubmit.progress = 100
                        God.spellToast(this,"Information Updated")
                        Timer().schedule(1000){
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finishAffinity()
                        }
                    }else{
                        registerSubmit.progress = 0
                        God.spellToast(this,result.toString())
                    }
                })
        }

    }
}
