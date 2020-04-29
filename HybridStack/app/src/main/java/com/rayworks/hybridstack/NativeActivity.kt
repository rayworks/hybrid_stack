package com.rayworks.hybridstack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NativeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
    }

    fun onGoToNative(view: View) {
        PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, mutableMapOf<String, String>())
    }

    fun onGoToFlutter(view: View) {
        PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL, mutableMapOf<String, String>())
    }
}
