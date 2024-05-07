//package com.namhoang.simple_picker
//
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.provider.MediaStore
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//
//
//class CameraHelper(private val activity: Activity, private val permissionCallback: PermissionCallback) {
//    companion object {
//        private const val CAMERA_PERMISSION_CODE = 100
//        private const val REQUEST_IMAGE_CAPTURE = 101
//    }
//
//    fun takePhoto() {
//        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
//            return
//        }
//
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
//            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        }
//    }
//
//    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                permissionCallback.onPermissionGranted()
//            } else {
//                permissionCallback.onPermissionDenied()
//            }
//        }
//    }
//}
