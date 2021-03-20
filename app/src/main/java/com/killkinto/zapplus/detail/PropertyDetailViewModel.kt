package com.killkinto.zapplus.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.killkinto.zapplus.data.IPropertyRepositoy
import com.killkinto.zapplus.data.model.Property

class PropertyDetailViewModel(
    private val repositoy: IPropertyRepositoy
) : ViewModel() {

    private val _property = MutableLiveData<Property>()
    val property: LiveData<Property>
        get() = _property

    fun loadProperty(id: String) {
        _property.value = repositoy.getProperty(id)
    }
}

class PropertyDetailViweModelFactory(private val repositoy: IPropertyRepositoy) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        (PropertyDetailViewModel(repositoy) as T)
}