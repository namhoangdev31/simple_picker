import 'dart:io';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'simple_picker_method_channel.dart';

abstract class SimplePickerPlatform extends PlatformInterface {
  /// Constructs a PluginExamplePlatform.
  SimplePickerPlatform() : super(token: _token);

  static final Object _token = Object();

  static SimplePickerPlatform _instance = MethodChannelSimplePickerPlatform();

  /// The default instance of [PluginExamplePlatform] to use.
  ///
  /// Defaults to [MethodChannelSimplePickerPlatform].
  static SimplePickerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [PluginExamplePlatform] when
  /// they register themselves.
  static set instance(SimplePickerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<File?> showPicker({required String source}) {
    throw UnimplementedError('showPicker() has not been implemented.');
  }

  Future<bool> callOffImage({required bool isAvaiable}) {
    throw UnimplementedError('callOffImage() has not been implemented.');
  }
}
