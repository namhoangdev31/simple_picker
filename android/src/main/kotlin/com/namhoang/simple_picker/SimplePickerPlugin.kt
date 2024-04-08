package com.namhoang.simple_picker

import android.app.Activity
import android.graphics.Bitmap
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** SimplePickerPlugin */
class SimplePickerPlugin : FlutterPlugin, MethodCallHandler , ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private var activity: Activity? = null // Add variable activity

  private val cameraPlugin = CameraPlugin() // Add cameraPlugin
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "simple_picker")
    channel.setMethodCallHandler(this)
  }
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    TODO("Not yet implemented")
  }

  override fun onDetachedFromActivity() {
    TODO("Not yet implemented")
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "getPlatformVersion" -> {
        result.success("Android ${android.os.Build.VERSION.RELEASE}")
      }
      "pickImageWithTakePhoto" -> {
        print("pickImageWithTakePhoto")
        activity?.let { activity ->
          cameraPlugin.allowPhoto = true
          cameraPlugin.allowVideo = false
          cameraPlugin.allowsTake = true
          cameraPlugin.currentActivity = activity
          cameraPlugin.didGetPhoto = { image: Bitmap, metadata: Map<Any, Any> ->
            // Thực hiện một số công việc với hình ảnh và dữ liệu metadata
          }
          cameraPlugin.present()
        }
      }
      "pickImageWithPhotoLibrary" -> {
        // Xử lý trường hợp thứ ba
      }
      "pickListImageWithPL" -> {
        // Xử lý trường hợp thứ tư
      }
      else -> {
        result.notImplemented()
      }
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  fun setActivity(activity: Activity) {
    this.activity = activity
  }
}
