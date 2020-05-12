package com.rayworks.hybridstack.plugin

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import com.rayworks.hybridstack.MainActivity
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

data class MethodChannelResult(val success: Boolean, val data: Any? = null)

class NormalMethodHandler : MethodChannel.MethodCallHandler {
    private var ec: EventChannel? = null
    private var appCtx: Context? = null
    private var methodChannel: MethodChannel? = null

    fun initialize(bm: BinaryMessenger, channel: String, ctx: Context) {
        this.appCtx = ctx;
        methodChannel = MethodChannel(bm, channel)
        methodChannel?.setMethodCallHandler(this)
    }

    fun dispose() {
        methodChannel = null
    }

    fun invokeMethod(method: String, arguments: Any, callback: (result: MethodChannelResult) -> Unit) {
        println(">>> send n2f : cmd - $method")
        methodChannel?.invokeMethod(method, arguments, object : MethodChannel.Result{
            override fun notImplemented() {
                Log.d("NormalMethodHandler", "notImplemented")
                callback.invoke(MethodChannelResult(false, "notImplemented"))
            }

            override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                Log.d("NormalMethodHandler", "error $errorMessage $ errorDetails")
                callback.invoke(MethodChannelResult(false, "error $errorMessage $ errorDetails"))
            }

            override fun success(result: Any?) {
                Log.d("NormalMethodHandler", "success")
                callback.invoke(MethodChannelResult(true, result))
            }
        })
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        println(">>> recv f2n : cmd - ${call.method}")
        when (call.method) {
            "jump" -> {
                val intent = Intent(appCtx, MainActivity::class.java)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                appCtx?.startActivity(intent)
            }
            else -> result.notImplemented()
        }
    }
}
