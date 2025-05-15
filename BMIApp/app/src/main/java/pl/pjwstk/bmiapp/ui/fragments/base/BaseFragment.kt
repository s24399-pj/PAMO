package pl.pjwstk.bmiapp.ui.fragments.base

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected var rootView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    override fun onResume() {
        super.onResume()
        fixLayout()
    }

    fun refreshLayout() {
        fixLayout()
    }

    protected open fun fixLayout() {
        rootView?.let { root ->
            root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    root.requestLayout()
                }
            })
        }
    }
}