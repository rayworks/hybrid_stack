package com.rayworks.hybridstack

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.idlefish.flutterboost.containers.BoostFlutterActivity
import java.util.HashMap

object PageRouter {
    val pageName: Map<String, String> =
        object : HashMap<String, String>() {
            init {
                put("init", "init")
                put("gotoRoute", "gotoRoute")
                put("gotoDemo", "gotoDemo")
                put("sample://flutterPage", "flutterPage")
            }
        }
    const val NATIVE_PAGE_URL = "sample://nativePage"
    const val FLUTTER_PAGE_URL = "sample://flutterPage"

    @JvmOverloads
    fun openPageByUrl(
        context: Context,
        url: String,
        params: Map<String, Any>?,
        requestCode: Int = 0
    ): Boolean {
        val path = url.split("\\?").toTypedArray()[0]
        Log.i("openPageByUrl", path)
        return try {
            if (pageName.containsKey(path)) {
                val intent =
                    BoostFlutterActivity.withNewEngine().url(pageName[path]!!)
                        .params(params!!)
                        .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context)
                if (context is Activity) {
                    context.startActivityForResult(intent, requestCode)
                } else {
                    context.startActivity(intent)
                }
                return true
            } else if (url.startsWith(NATIVE_PAGE_URL) || url.contains("native")) {
                context.startActivity(
                    Intent(
                        context,
                        NativeActivity::class.java
                    )
                )
                return true
            }
            false
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }
}