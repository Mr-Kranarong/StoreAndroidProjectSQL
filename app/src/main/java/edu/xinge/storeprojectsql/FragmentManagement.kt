package edu.xinge.storeprojectsql

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun AppCompatActivity.replaceFragmenty(fragment: Fragment,
                                       allowStateLoss: Boolean = false,
                                       @IdRes containerViewId: Int) {
    val ft = supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        .replace(containerViewId, fragment)
    if (!supportFragmentManager.isStateSaved) {
        ft.commit()
    } else if (allowStateLoss) {
        ft.commitAllowingStateLoss()
    }
}
