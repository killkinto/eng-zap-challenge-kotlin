package com.killkinto.zapplus.overview

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.killkinto.zapplus.R
import com.killkinto.zapplus.data.model.Property

@BindingAdapter("listData")
fun RecyclerView.setListData(list: List<Property>) {
    val adapter = this.adapter as PropertyListAdapter
    adapter.submitList(list)
}

@BindingAdapter("statusImage")
fun ImageView.setStatusImage(status: PropertyStatus) {
    when (status) {
        PropertyStatus.LOADING -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.loading_animation)
        }
        PropertyStatus.ERROR, PropertyStatus.ERROR_CONNECTED  -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.ic_baseline_cloud_off_48)
        }
        PropertyStatus.DONE -> this.visibility = View.GONE
    }
}
