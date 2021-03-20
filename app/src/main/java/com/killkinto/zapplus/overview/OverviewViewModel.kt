package com.killkinto.zapplus.overview

import androidx.lifecycle.*
import com.killkinto.zapplus.data.IPropertyRepositoy
import com.killkinto.zapplus.data.model.Property
import kotlinx.coroutines.launch

enum class PropertyStatus { LOADING, ERROR, ERROR_CONNECTED, DONE }

class OverviewViewModel(private val repositoy: IPropertyRepositoy) :
    ViewModel() {
    private val _status = MutableLiveData<PropertyStatus>()
    val status: LiveData<PropertyStatus>
        get() = _status

    private val _navigateToSelectedProperty = MutableLiveData<Property?>()
    val navigateToSelectedProperty: LiveData<Property?>
        get() = _navigateToSelectedProperty

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>>
        get() = _properties

    init {
        _properties.value = emptyList()
    }

    fun loadProperties(portal: String, isConnected: Boolean) {
        if (isConnected) {
            viewModelScope.launch {
                _status.value = PropertyStatus.LOADING
                try {
                    _properties.value = repositoy.getProperties(portal)
                    _status.value = PropertyStatus.DONE
                } catch (e: Exception) {
                    _status.value = PropertyStatus.ERROR
                    _properties.value = emptyList()
                }
            }
        } else {
            _status.value = PropertyStatus.ERROR_CONNECTED
        }
    }

    fun displayPropertyDetails(property: Property) {
        _navigateToSelectedProperty.value = property
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}

class OverviewViweModelFactory(private val repositoy: IPropertyRepositoy) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        (OverviewViewModel(repositoy) as T)
}