package com.killkinto.zapplus

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.killkinto.zapplus.customview.CompositionView
import com.killkinto.zapplus.data.model.Address
import com.killkinto.zapplus.data.model.BusinessType
import com.killkinto.zapplus.data.model.Composition
import com.killkinto.zapplus.overview.PropertyStatus
import java.text.NumberFormat

val format: NumberFormat = NumberFormat.getCurrencyInstance()

@BindingAdapter("image_url")
fun ImageView.setPropertyImage(imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(this.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

@BindingAdapter("property_title")
fun TextView.setpropertyTitle(type: BusinessType?) {
    type?.let {
        text = context.getString(
            R.string.property_title,
            when (type) {
                BusinessType.SALE -> context.getString(R.string.business_type_sale)
                BusinessType.RENTAL -> context.getString(R.string.business_type_rental)
            }
        )
    }
}

@BindingAdapter("property_price")
fun TextView.setPropertyPrice(price: Double?) {
    price?.let {
        text = format.format(price)
    }
}

@BindingAdapter("condominium_value")
fun TextView.setCondominiumValue(value: String?) {
    value?.let {
        try {
            text = context.getString(
                R.string.condominium_value,
                if (value != "0.0") format.format(value.toDouble())
                    else context.getString(R.string.uninformed)
            )
        } catch (e: NumberFormatException) {
            text = ""
        }

    }
}

@BindingAdapter("iptu_value")
fun TextView.setIptuValue(value: Double?) {
    value?.let {
        text = context.getString(
            R.string.iptu_value,
            if (value != 0.0) format.format(value) else context.getString(R.string.uninformed)
        )
    }
}

@BindingAdapter("property_composition")
fun TextView.setPropetyComposition(composition: Composition) {
    val str = mutableListOf<String>()
    if (composition.bedrooms != 0) {
        str.add(
            context.resources.getQuantityString(
                R.plurals.bedrooms,
                composition.bedrooms, composition.bedrooms
            )
        )
    }
    if (composition.parkingSpaces != 0) {
        str.add(
            context.resources.getQuantityString(
                R.plurals.parking_spaces,
                composition.parkingSpaces, composition.parkingSpaces
            )
        )
    }
    if (composition.area != 0) {
        str.add(context.getString(R.string.area, composition.area))
    }
    this.text = str.joinToString(" | ")
}

@BindingAdapter("area")
fun View.setArea(area: Int?) {
    area?.let {
        val label = context.getString(R.string.area, area)
        if (this is TextView) {
            this.text = label
        } else if (this is CompositionView) {
            this.findViewById<TextView>(R.id.text).text = label
        }
    }
}

@BindingAdapter("bathrooms")
fun View.setBathrooms(bathrooms: Int?) {
    bathrooms?.let {
        val label = context.resources.getQuantityString(R.plurals.bathrooms, bathrooms, bathrooms)
        if (this is TextView) {
            this.text = label
        } else if (this is CompositionView) {
            this.findViewById<TextView>(R.id.text).text = label
        }
    }
}

@BindingAdapter("bedrooms")
fun View.setBedrooms(bedrooms: Int?) {
    bedrooms?.let {
        val label = context.resources.getQuantityString(R.plurals.bedrooms, bedrooms, bedrooms)
        if (this is TextView) {
            this.text = label
        } else if (this is CompositionView) {
            this.findViewById<TextView>(R.id.text).text = label
        }
    }
}

@BindingAdapter("parking_spaces")
fun View.setParkingSpaces(parkingSpaces: Int?) {
    parkingSpaces?.let {
        val label = context.resources.getQuantityString(
            R.plurals.parking_spaces,
            parkingSpaces,
            parkingSpaces
        )
        if (this is TextView) {
            this.text = label
        } else if (this is CompositionView) {
            this.findViewById<TextView>(R.id.text).text = label
        }
    }
}

@BindingAdapter("property_address")
fun TextView.setPropetyAddress(address: Address) {
    if (address.city.isNotEmpty() || address.neighborhood.isNotEmpty()) {
        this.text =
            this.context.getString(R.string.property_address, address.neighborhood, address.city)
    }
}

@BindingAdapter("empty_list")
fun TextView.setMessageErroList(status: PropertyStatus?) {
    status?.let {
        if (status == PropertyStatus.ERROR_CONNECTED) {
            visibility = View.VISIBLE
            text = context.getString(R.string.not_connected)
        } else {
            visibility = View.GONE
        }
    }
}


