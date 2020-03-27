package com.seatrend.vendor.tvcheck

import com.seatrend.vendor.tvcheck.fragment.CheckFragment

class MainActivity : BaseActivity() {
    override fun initView() {

        initFragment()

    }

    private fun initFragment() {

        //检测站合格不合格的
        getCheckStationType()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }


    private fun getCheckStationType() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val cf = CheckFragment()
        transaction.replace(R.id.fg_parent, cf)
        transaction.commit()
    }
}
