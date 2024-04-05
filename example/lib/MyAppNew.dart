import 'dart:developer';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:plugin_example_example/tryhard.dart';
import 'package:simple_picker/simple_picker.dart';

// class MyApp extends StatefulWidget {
//   const MyApp({Key? key}) : super(key: key);

//   @override
//   _MyAppState createState() => _MyAppState();
// }

// class _MyAppState extends State<MyApp> {
//   late SimplePicker _simplePickerPlugin;
//   final String _platformVersion = 'Unknown';
//   File? _value;
//   @override
//   void initState() {
//     super.initState();
//     _simplePickerPlugin = SimplePicker();
//   }

//   Future<void> _showPicker() async {
//     try {
//       var result = await _simplePickerPlugin.showPicker(source: 'front');
//       log('result: $result');
//       setState(() {
//         _value = result;
//       });
//     } catch (e) {
//       log('error: $e');
//     }
//   }

//   //show dialog to show image
//   void _showImageDialog() {
//     showDialog(
//       context: context,
//       builder: (BuildContext context) {
//         return AlertDialog(
//           content: Image.file(_value!),
//         );
//       },
//     );
//   }

//   @override
//   void dispose() {
//     // _simplePickerPlugin.dispose(); // Don't forget to dispose the plugin
//     super.dispose();
//   }

//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       home: Scaffold(
//         bottomNavigationBar: BottomAppBar(
//           child: Row(
//             children: [
//               Expanded(
//                 child: ElevatedButton(
//                   onPressed: () {
//                     print('tapped left button');
//                   },
//                   child: const Text('Show Picker'),
//                 ),
//               ),
//               Expanded(
//                 child: ElevatedButton(
//                   onPressed: () {
//                     print('tapped');
//                   },
//                   child: const Text('Show Picker'),
//                 ),
//               ),
//             ],
//           ),
//         ),
//         appBar: AppBar(
//           title: const Text('Plugin example app'),
//         ),
//         body: Center(
//           child: StreamBuilder<bool>(
//             stream: _simplePickerPlugin.eventStream,
//             builder: (context, snapshot) {
//               if (snapshot.hasData || snapshot.data == null) {
//                 return SingleChildScrollView(
//                   child: Column(
//                     children: [
//                       ElevatedButton(
//                         onPressed: _showPicker,
//                         child: const Text('Show Picker'),
//                       ),
//                       Text('Data received True or Null: ${_value}'),
//                       if (_value != null)
//                         InkWell(onTap: () {}, child: Image.file(_value!)),
//                       Text('Data received: ${snapshot.data}'),
//                       ElevatedButton(
//                         onPressed: () {
//                           // _showImageDialog();
//                         },
//                         child: const Text('Call Off Image'),
//                       ),
//                     ],
//                   ),
//                 );
//               } else if (snapshot.hasError) {
//                 return Text('Error: ${snapshot.error}');
//               } else {
//                 return const CircularProgressIndicator();
//               }
//             },
//           ),
//         ),
//       ),
//     );
//   }
// }
