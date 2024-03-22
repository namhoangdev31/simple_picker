import 'dart:developer';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'plugin_example_platform_interface.dart';

/// An implementation of [PluginExamplePlatform] that uses method channels.
class MethodChannelPluginExample extends PluginExamplePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('plugin_example');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<File> showPicker({required String source}) async {
    final String result =
        await methodChannel.invokeMethod('pickImage', {'source': source});
    log('result: $result');
    return File(result);
  }

  @override
  Future<bool> callOffImage({required bool isAvaiable}) async {
    final bool result = await methodChannel
        .invokeMethod('callOffImage', {'isAvaiable': isAvaiable});
    return result;
  }
}
