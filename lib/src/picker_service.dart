import 'dart:io';
import 'package:image_picker/image_picker.dart';

class PickerService {
  final ImagePicker _picker = ImagePicker();

  Future<String?> pickImageFromGallery(
      {double? width, double? height, int? imageQuality}) async {
    try {
      final pickedFile = await _picker.pickImage(
          source: ImageSource.gallery,
          imageQuality: imageQuality,
          maxWidth: width,
          maxHeight: height);
      if (pickedFile != null) {
        return pickedFile.path;
      } else {
        print('No image selected.');
        return null;
      }
    } catch (e) {
      print('Error picking image from gallery: $e');
      return null;
    }
  }

  Future<String?> pickImageFromCamera(
      {double? width, double? height, int? imageQuality , CameraDevice? preferredCameraDevice}) async {
    try {
      final pickedFile = await _picker.pickImage(
          source: ImageSource.camera,
          imageQuality: imageQuality,
          maxWidth: width,
          preferredCameraDevice : preferredCameraDevice ?? CameraDevice.rear,
          maxHeight: height);
      if (pickedFile != null) {
        return pickedFile.path;
      } else {
        print('No image selected.');
        return null;
      }
    } catch (e) {
      print('Error picking image from camera: $e');
      return null;
    }
  }
}
