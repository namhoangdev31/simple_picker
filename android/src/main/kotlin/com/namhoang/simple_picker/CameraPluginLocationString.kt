//package com.namhoang.simple_picker
//
//enum class CameraPluginLocationString(val stringValue: String) {
//    // Decline to proceed with operation
//    CANCEL("Cancel"),
//
//    // Option to select photo from library
//    CHOOSE_FROM_LIBRARY("Choose From Library"),
//
//    // Option to select photo from photo roll
//    CHOOSE_FROM_PHOTO_ROLL("Choose From PhotoRoll"),
//
//    // There are no sources available to select a photo
//    NO_SOURCES("No Sources"),
//
//    // Option to take photo using camera
//    TAKE_PHOTO("Take Photo"),
//
//    // Option to take video using camera
//    TAKE_VIDEO("Take Video");
//
//    fun comment(): String {
//        return when (this) {
//            CANCEL -> "Decline to proceed with operation"
//            CHOOSE_FROM_LIBRARY -> "Option to select photo/video from library"
//            CHOOSE_FROM_PHOTO_ROLL -> "Option to select photo from photo roll"
//            NO_SOURCES -> "There are no sources available to select a photo"
//            TAKE_PHOTO -> "Option to take photo using camera"
//            TAKE_VIDEO -> "Option to take video using camera"
//        }
//    }
//}