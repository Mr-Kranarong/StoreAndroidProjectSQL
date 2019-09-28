package edu.xinge.storeprojectsql

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dd.processbutton.iml.ActionProcessButton
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        RegisterLink.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        LoginSubmit.setMode(ActionProcessButton.Mode.ENDLESS)
        LoginSubmit.setOnClickListener{
            generateAuthen()
        }
    }

    fun generateAuthen(){
        val prefs = getSharedPreferences(getString(R.string.shared_preferences_filename), Context.MODE_PRIVATE).edit()

        LoginSubmit.progress = 1

        Ion.with(this)
            .load(God.HOST+"/requestAuthKey.php")
            .setBodyParameter( "OwnerID",useridBox.text.toString())
            .setBodyParameter( "Password",passwordBox.text.toString())
            .asString()
            .setCallback(FutureCallback<String> { e, result ->
                if(e != null){
                    God.spellToast(this,e.message)
                    LoginSubmit.progress = -1
                }
                val Key = result.toString()
                if(Key.length==100){
                    God.AuthKey = Key
                    prefs.putString("AuthKey",Key).commit()
                    God.spellToast(this,"Logged In Successfully")
                    LoginSubmit.progress = 100
                    Timer().schedule(1000){
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finishAffinity()
                    }
                }else{
                    God.spellToast(this,Key)
                    LoginSubmit.progress = 0
                }
            })


    }
}
