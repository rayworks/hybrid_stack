package com.rayworks.hybridstack

import android.app.Application
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.rayworks.hybridstack.plugin.DroidPlugin
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

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

        val router: INativeRouter = INativeRouter { context, url, urlParams, requestCode, exts ->
            val assembleUrl: String = Utils.assembleUrl(url, urlParams)
            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: FlutterBoost.BoostLifecycleListener =
            object : FlutterBoost.BoostLifecycleListener {
                override fun beforeCreateEngine() {}
                override fun onEngineCreated() {}
                override fun onPluginsRegistered() {}
                override fun onEngineDestroy() {}
            }

        //
        // AndroidManifest.xml 中必须要添加 flutterEmbedding 版本设置
        //
        //   <meta-data android:name="flutterEmbedding"
        //               android:value="2">
        //    </meta-data>
        // GeneratedPluginRegistrant 会自动生成 新的插件方式　
        //
        // 插件注册方式请使用
        //FlutterBoost.instance().engineProvider().plugins.add(droidPlugin/*new FlutterPlugin()*/)

        val platform = FlutterBoost.ConfigBuilder(this, router)
            .isDebug(true)
            .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
            .renderMode(FlutterView.RenderMode.texture)
            .lifecycleListener(boostLifecycleListener)
            .build()
        FlutterBoost.instance().init(platform)
    }
}