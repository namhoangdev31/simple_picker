import 'dart:developer';
import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';
import 'package:image/image.dart' as img;
import 'package:simple_picker/simple_picker.dart';
import 'package:simple_picker/source_type.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late SimplePicker _simplePickerPlugin;
  final String _platformVersion = 'Unknown';
  File? _value;
  @override
  void initState() {
    super.initState();
    _simplePickerPlugin = SimplePicker();
  }

  Future<void> _showPicker() async {
    try {
      var result =
          await _simplePickerPlugin.showPicker(source: SourceType.back);
      log('result: $result');
      setState(() {
        _value = result;
      });
    } catch (e) {
      log('error: $e');
    }
  }

  Future<void> pickImageWithPhotoLibrary() async {
    try {
      var result = await _simplePickerPlugin.pickImageWithPhotoLibrary();
      setState(() {
        _value = result;
      });
    } catch (e) {
      log('error: $e');
    }
  }

  //show dialog to show image
  void _showImageDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          content:
              Container(height: 300, width: 300, child: Image.file(_value!)),
        );
      },
    );
  }

  @override
  void dispose() {
    // _simplePickerPlugin.dispose(); // Don't forget to dispose the plugin
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        bottomNavigationBar: BottomAppBar(
          child: Row(
            children: [
              Expanded(
                child: ElevatedButton(
                  onPressed: () {
                    print('tapped left button');
                  },
                  child: const Text('Show Picker'),
                ),
              ),
              Expanded(
                child: ElevatedButton(
                  onPressed: () {
                    print('tapped');
                  },
                  child: const Text('Show Picker'),
                ),
              ),
            ],
          ),
        ),
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              ElevatedButton(
                onPressed: _showPicker,
                child: const Text('Show Picker'),
              ),
              ElevatedButton(
                onPressed: pickImageWithPhotoLibrary,
                child: const Text('pickImageWithPhotoLibrary'),
              ),
              Text('Data received True or Null: ${_value}'),
              if (_value != null)
                InkWell(
                    onTap: () {
                      // _showImageDialog();
                    },
                    child: Image.file(_value!)),
              ElevatedButton(
                onPressed: () {
                  // _showImageDialog();
                },
                child: const Text('Call Off Image'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<img.Image> _getImageSize(String imagePath) async {
    final bytes = await File(imagePath).readAsBytes();
    return img.decodeImage(bytes)!;
  }
}
