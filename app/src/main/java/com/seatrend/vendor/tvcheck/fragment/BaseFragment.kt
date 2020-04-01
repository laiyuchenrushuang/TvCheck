package com.seatrend.vendor.tvcheck.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Created by seatrend on 2018/10/8.
 */

abstract class BaseFragment : Fragment() {

    private var llNoData: LinearLayout? = null
    private var tvNoDataMsg: TextView? = null
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (null != rootView) {
            val parent: ViewGroup? = container
            if (null != parent) {
                parent.removeView(parent)
            }
        } else {
            rootView = getLayoutView(inflater, container)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View
    abstract fun initView()

    protected fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    protected fun showLog(s: String) {
        Log.d("lylog", s)
    }
}
