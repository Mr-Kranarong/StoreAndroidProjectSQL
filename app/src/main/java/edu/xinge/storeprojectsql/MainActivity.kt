package edu.xinge.storeprojectsql

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentFrame.setBackgroundColor(resources.getColor(R.color.white))

        replaceFragmenty(MarketFragment(),false,R.id.FragmentFrame)

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
        LogDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            God.spellToast(this,"Login & Register -- TODO")
        }

    }
}
