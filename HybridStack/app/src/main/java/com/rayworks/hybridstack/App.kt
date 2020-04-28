package com.rayworks.hybridstack

import android.app.Application
import com.rayworks.hybridstack.plugin.DroidPlugin
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
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("flutter_engine_id", flutterEngine)

        flutterEngine.plugins.add(droidPlugin)
    }
}