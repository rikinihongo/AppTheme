package com.riki.apptheme

import android.app.Application

/**
 * Created by Sonpx on 07/10/2024
 * Copyright(©)Cloudxanh. All rights reserved.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeHelper.init(this)
    }
}