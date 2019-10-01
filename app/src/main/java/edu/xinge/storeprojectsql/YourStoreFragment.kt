package edu.xinge.storeprojectsql


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_your_store.*
import kotlinx.android.synthetic.main.fragment_your_store.view.*


/**
 * A simple [Fragment] subclass.
 */
class YourStoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_your_store, container, false)


        v.imageView.setOnClickListener { pickImageFromGallery() }

        if(God.hasStore){
            God.spellToast(activity!!.applicationContext,"Store PAGE")
        }else if(!God.hasStore){
            God.spellToast(activity!!.applicationContext,"Store DOESNT Exist")
        }
        //God.spellToast(v.context,hasStore.toString())

        return v
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000){
            textView.text = data?.data.toString()
            imageView.setImageURI(data?.data)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }
}
