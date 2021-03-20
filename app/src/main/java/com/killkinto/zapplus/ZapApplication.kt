package com.killkinto.zapplus

import android.app.Application
import com.killkinto.zapplus.data.IPropertyRepositoy

class ZapApplication : Application() {
    val propertyRepositoy: IPropertyRepositoy
        get() = ServiceLocator.providePropertyRepository()
}