import 'dart:developer';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'simple_picker_platform_interface.dart';

/// An implementation of [SimplePickerPlatform] that uses method channels.
class MethodChannelSimplePickerPlatform extends SimplePickerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('simple_picker');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<File> showPicker({required String source}) async {
    final String result =
        await methodChannel.invokeMethod('pickImageWithTakePhoto');
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
