import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:simple_picker/source_type.dart';

import 'simple_picker_platform_interface.dart';

class SimplePicker {
  Future<String?> getPlatformVersion() {
    return SimplePickerPlatform.instance.getPlatformVersion();
  }

  Future<File?> showPicker({required SourceType source}) {
    return SimplePickerPlatform.instance.showPicker(source: source);
  }

  Future<bool> callOffImage({required bool isAvaiable}) {
    return SimplePickerPlatform.instance.callOffImage(isAvaiable: isAvaiable);
  }

  Future<File> pickImageWithPhotoLibrary() {
    return SimplePickerPlatform.instance.pickImageWithPhotoLibrary();
  }
}
