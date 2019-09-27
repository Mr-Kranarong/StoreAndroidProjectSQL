package edu.xinge.storeprojectsql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dd.processbutton.iml.ActionProcessButton
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.concurrent.schedule


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerSubmit.setMode(ActionProcessButton.Mode.ENDLESS)
        registerSubmit.setOnClickListener{
            register()
        }
    }

    fun register(){
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
                God.spellToast(this,result.toString())
                registerSubmit.progress = 100
                Timer().schedule(1000){
                    finish()
                }
            })
    }
}
