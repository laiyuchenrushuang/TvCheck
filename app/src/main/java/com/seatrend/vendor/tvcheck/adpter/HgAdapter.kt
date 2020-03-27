package com.seatrend.vendor.tvcheck.adpter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.seatrend.vendor.tvcheck.R
import com.seatrend.vendor.tvcheck.Utils.StringUtils
import com.seatrend.vendor.tvcheck.entity.Data

class HgAdapter(private var mContext: Context? = null, private var mData: ArrayList<Data>? = null) : RecyclerView.Adapter<HgAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemView(position)
    }


    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: ArrayList<Data>?) {
        this.mData = data
        notifyDataSetChanged()
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tv_hphm: TextView? = null
        private var tv_aj: TextView? = null
        private var tv_hj: TextView? = null



        init {
            tv_hphm = view.findViewById(R.id.tv_hphm)
            tv_aj = view.findViewById(R.id.tv_aj)
            tv_hj = view.findViewById(R.id.tv_hj)

        }

        fun setItemView(position: Int) {
            tv_hphm!!.text =  StringUtils.isNull(mData!![position].hphm)
            tv_aj!!.text =  StringUtils.isNull(mData!![position].aj)
            tv_hj!!.text =  StringUtils.isNull(mData!![position].hj)
        }
    }
}
