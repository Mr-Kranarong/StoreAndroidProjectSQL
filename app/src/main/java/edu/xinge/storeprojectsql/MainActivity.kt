package edu.xinge.storeprojectsql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmenty(MarketFragment(),false,R.id.FragmentFrame)

        MarketDrawerButton.setOnClickListener {
            duoNav.closeDrawer()
            replaceFragmenty(MarketFragment(),false,R.id.FragmentFrame)
            God.spellToast(this,"Implemented -- Functional")
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

    }
}
