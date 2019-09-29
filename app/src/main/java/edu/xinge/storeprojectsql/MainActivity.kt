package edu.xinge.storeprojectsql

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences(getString(R.string.shared_preferences_filename), Context.MODE_PRIVATE).getString("AuthKey","")
        if(prefs != ""){

            if(!God.checkUser(this,prefs)){
                getSharedPreferences(getString(R.string.shared_preferences_filename), Context.MODE_PRIVATE).edit().clear().commit()
                God.sessionDestroy()
                Timer().schedule(1000){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finishAffinity()
                }
            }else{
                //God.spellToast(this,"Valid Prefs")
                //getting and updating realtime userdata
                God.getDetailedUserInfo(this)
                UserName.text = God.cacheFullName
                Fund.text = "Fund: "+God.cacheFund
            }

            LogLabel.text = "Logout"
            LogDrawerButton.setOnClickListener {
                duoNav.closeDrawer()
                getSharedPreferences(getString(R.string.shared_preferences_filename), Context.MODE_PRIVATE).edit().clear().commit()
                God.sessionDestroy()
                UserName.setText("Not Logged In")
                Fund.setText("Fund: 0")
                God.spellToast(this,"Logged Out Successfully")
                Timer().schedule(1000){
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finishAffinity()
                }
            }
            YourStoreDrawerButton.isVisible = true
            TransactionDrawerButton.isVisible = true
            CartDrawerButton.isVisible = true
            ProfileDrawerButton.isVisible = true
        }else{
            LogLabel.text = "Login"
            LogDrawerButton.setOnClickListener {
                duoNav.closeDrawer()
                intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            YourStoreDrawerButton.isVisible = false
            TransactionDrawerButton.isVisible = false
            CartDrawerButton.isVisible = false
            ProfileDrawerButton.isVisible = false
        }


        //FragmentFrame.setBackgroundColor(resources.getColor(R.color.white))
        replaceFragmenty(MarketFragment(),false,R.id.FragmentFrame)
        duoNav.openDrawer()

        MarketDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            replaceFragmenty(MarketFragment(),false,R.id.FragmentFrame)
            God.spellToast(this,"Implemented -- Partially")
        }
        YourStoreDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            replaceFragmenty(YourStoreFragment(),false,R.id.FragmentFrame)
            God.spellToast(this,"Not yet implemented -- TODO")
        }
        TransactionDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            replaceFragmenty(TransactionFragment(),false,R.id.FragmentFrame)
            God.spellToast(this,"Not yet implemented -- TODO")
        }
        CartDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            replaceFragmenty(CartFragment(),false,R.id.FragmentFrame)
            God.spellToast(this,"Not yet implemented -- TODO")
        }
        ProfileDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("UpdateInfo",true)
            startActivity(intent)
        }

    }


}
