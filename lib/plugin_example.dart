import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

import 'plugin_example_platform_interface.dart';

class PluginExample {
  static const EventChannel _eventChannel =
      EventChannel('handle_result_channel');

  late StreamController<bool> _streamController;

  Stream<bool> get eventStream => _streamController.stream;

  PluginExample() {
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
    return PluginExamplePlatform.instance.getPlatformVersion();
  }

  Future<File?> showPicker({required String source}) {
    return PluginExamplePlatform.instance.showPicker(source: source);
  }

  Future<bool> callOffImage({required bool isAvaiable}) {
    return PluginExamplePlatform.instance.callOffImage(isAvaiable: isAvaiable);
  }
}
