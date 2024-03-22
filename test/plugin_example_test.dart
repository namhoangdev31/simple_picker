import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:simple_picker/plugin_example.dart';
import 'package:simple_picker/plugin_example_method_channel.dart';
import 'package:simple_picker/plugin_example_platform_interface.dart';

class MockPluginExamplePlatform
    with MockPlatformInterfaceMixin
    implements PluginExamplePlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<File?> showPicker({required String source}) {
    // TODO: implement showPicker
    throw UnimplementedError();
  }

  @override
  Future<bool> callOffImage({required bool isAvaiable}) {
    // TODO: implement callOffImage
    throw UnimplementedError();
  }
}

void main() {
  final PluginExamplePlatform initialPlatform = PluginExamplePlatform.instance;

  test('$MethodChannelPluginExample is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelPluginExample>());
  });

  test('getPlatformVersion', () async {
    PluginExample pluginExamplePlugin = PluginExample();
    MockPluginExamplePlatform fakePlatform = MockPluginExamplePlatform();
    PluginExamplePlatform.instance = fakePlatform;

    expect(await pluginExamplePlugin.getPlatformVersion(), '42');
  });
}
