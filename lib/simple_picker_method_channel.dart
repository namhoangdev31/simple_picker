
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:simple_picker/src/picker_service.dart';

import 'simple_picker_platform_interface.dart';
import 'source_type.dart';

/// An implementation of [SimplePickerPlatform] that uses method channels.
class MethodChannelSimplePickerPlatform extends SimplePickerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('simple_picker');
  final PickerService _pickerService = PickerService();
  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<File> showPicker(
      {required SourceType source,
      double? width,
      double? height,
      int? imageQuality}) async {
    Map<String, dynamic> args = {
      'source': source.name,
      'width': width,
      'height': height,
      'imageQuality': imageQuality
    };
    CameraDevice? preferredCameraDevice;
    if (source == SourceType.front) {
      preferredCameraDevice = CameraDevice.front;
    } else if (source == SourceType.back) {
      preferredCameraDevice = CameraDevice.rear;
    }
    String? result;
    if (Platform.isAndroid) {
      result = await _pickerService.pickImageFromCamera(
          width: width,
          height: height,
          imageQuality: imageQuality,
          preferredCameraDevice: preferredCameraDevice);
    } else if (Platform.isIOS) {
      result = await methodChannel.invokeMethod('showPicker', args);
    }

    return File(result!);
  }

  @override
  Future<bool> callOffImage({required bool isAvaiable}) async {
    final bool result = await methodChannel
        .invokeMethod('callOffImage', {'isAvaiable': isAvaiable});
    return result;
  }

  @override
  Future<File> pickImageWithPhotoLibrary(
      {double? width, double? height, int? imageQuality}) async {
    Map<String, dynamic> args = {
      'width': width,
      'height': height,
      'imageQuality': imageQuality
    };
    String? result;
    if (Platform.isAndroid) {
      result = await _pickerService.pickImageFromGallery(
          width: width, height: height, imageQuality: imageQuality);
    } else if (Platform.isIOS) {
      result =
          await methodChannel.invokeMethod('pickImageWithPhotoLibrary', args);
    }
    return File(result!);
  }
}
