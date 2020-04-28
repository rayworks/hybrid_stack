package com.rayworks.hybridstack.plugin

import io.flutter.embedding.engine.plugins.FlutterPlugin

const val FLUTTER_NORMAL_METHOD_CHANNEL = "com.rayworks.hybridstack/method_channel"
class DroidPlugin : FlutterPlugin {
    var normalMethodHandler: NormalMethodHandler = NormalMethodHandler()
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        normalMethodHandler.initialize(binding.binaryMessenger, FLUTTER_NORMAL_METHOD_CHANNEL, binding.applicationContext)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        normalMethodHandler.dispose()
    }
}