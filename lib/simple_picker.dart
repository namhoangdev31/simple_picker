import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:simple_picker/source_type.dart';

import 'simple_picker_platform_interface.dart';

class SimplePicker {
  static const EventChannel _eventChannel =
      EventChannel('handle_result_channel');

  late StreamController<bool> _streamController;

  Stream<bool> get eventStream => _streamController.stream;

  SimplePicker() {
    _streamController = StreamController<bool>.broadcast();
    _initialize();
  }

  void _initialize() {
    _eventChannel
        .receiveBroadcastStream()
        .map((event) => event as bool?)
        .listen((event) {
      _streamController.add(event ?? false);
    });
  }

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
