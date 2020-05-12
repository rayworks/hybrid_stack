package com.rayworks.hybridstack

import android.app.Application
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.rayworks.hybridstack.plugin.DroidPlugin
import io.flutter.embedding.android.FlutterView


class App : Application() {
    val droidPlugin = DroidPlugin()

    override fun onCreate() {
        super.onCreate()

        setupFlutter()
    }

    private fun setupFlutter() {
        /*val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("flutter_engine_id", flutterEngine)

        flutterEngine.plugins.add(droidPlugin)*/

        val router: INativeRouter = INativeRouter { context, url, urlParams, requestCode, exts ->{}
//            val assembleUrl: String = Utils.assembleUrl(url, urlParams)
//            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: FlutterBoost.BoostLifecycleListener =
            object : FlutterBoost.BoostLifecycleListener {
                override fun beforeCreateEngine() {}
                override fun onEngineCreated() {
                    // time to register plugin
                    FlutterBoost.instance().engineProvider().plugins.add(droidPlugin)
                }
                override fun onPluginsRegistered() {}
                override fun onEngineDestroy() {}
            }

        val platform = FlutterBoost.ConfigBuilder(this, router)
            .isDebug(true)
            .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
            .renderMode(FlutterView.RenderMode.texture)
            .lifecycleListener(boostLifecycleListener)
            .build()
        FlutterBoost.instance().init(platform)
    }
}