import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:simple_picker/simple_picker.dart';
import 'package:simple_picker/simple_picker_method_channel.dart';
import 'package:simple_picker/simple_picker_platform_interface.dart';
import 'package:simple_picker/source_type.dart';

class MockPluginExamplePlatform
    with MockPlatformInterfaceMixin
    implements SimplePickerPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<File?> showPicker({required SourceType source}) {
    // TODO: implement showPicker
    throw UnimplementedError();
  }

  @override
  Future<bool> callOffImage({required bool isAvaiable}) {
    // TODO: implement callOffImage
    throw UnimplementedError();
  }

  @override
  Future<File> pickImageWithPhotoLibrary() {
    // TODO: implement pickImageWithPhotoLibrary
    throw UnimplementedError();
  }
}

void main() {
  final SimplePickerPlatform initialPlatform = SimplePickerPlatform.instance;

  test('$MethodChannelSimplePickerPlatform is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSimplePickerPlatform>());
  });

  test('getPlatformVersion', () async {
    SimplePicker pluginExamplePlugin = SimplePicker();
    MockPluginExamplePlatform fakePlatform = MockPluginExamplePlatform();
    SimplePickerPlatform.instance = fakePlatform;

    expect(await pluginExamplePlugin.getPlatformVersion(), '42');
  });
}
