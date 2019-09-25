package edu.xinge.storeprojectsql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMainItem()

    }

    fun loadMainItem(){
        Ion.with(this)
            .load(God.HOST)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                //God.spellToast(this,e.message)
                textView.setText(result.toString())
            })
    }
}
