package com.dsm.restaurant

import android.app.Application
import com.dsm.restaurant.di.account.accountModule
import com.dsm.restaurant.di.address.addressModule
import com.dsm.restaurant.di.auth.authModule
import com.dsm.restaurant.di.localModule
import com.dsm.restaurant.di.menu.menuModule
import com.dsm.restaurant.di.networkModule
import com.dsm.restaurant.di.restaurant.restaurantModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class ThikiraRestaurantApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ThikiraRestaurantApplication)
            modules(
                listOf(
                    networkModule,
                    localModule,

                    accountModule,
                    addressModule,
                    authModule,
                    restaurantModule,
                    menuModule
                )
            )
        }
    }

    open fun getApiUrl(): String = "http://192.168.1.86:1234/"

    open fun getNaverApiUrl(): String = "https://openapi.naver.com/"
}