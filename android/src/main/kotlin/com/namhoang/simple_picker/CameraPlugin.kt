//package com.namhoang.simple_picker
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.Rect
//import android.net.Uri
//import android.provider.MediaStore
//import android.view.View
//import android.widget.Button
//import android.widget.Toast
//import com.google.android.material.tabs.TabLayout
//import com.namhoang.dialogmenu.FloatingMenuDialog
//
//
//open class CameraPlugin {
//    // MARK: - Configuration options
//
//    /// Whether to allow selecting a photo
//   open var allowPhoto: Boolean = false
//
//    /// Whether to allow selecting a video
//    open var allowVideo: Boolean = false
//
//    /// Whether to allow selecting multiple photos
//    open var allowMultiple: Boolean = false
//
//    /// Whether to allow selecting multiple videos
//
//    open var allowMultipleVideos: Boolean = false
//
//    /// Whether to allow capturing a photo/video with the camera
//    open var allowsTake: Boolean = true
//
//    ///  That's the width of the image that the image was designed for (e.g. 375.0)
//    open var targetWidth: Double = 0.0
//
//    /// That's the height of the image that the image was designed for (e.g. 667.0)
//    open var targetHeight: Double = 0.0
//
//    /// Whether to allow selecting existing media
//    open var allowExistingMedia: Boolean = false
//
//    /// Whether to allow selecting multiple media
//    open var allowMultipleMedia: Boolean = false
//
//    /// Whether to allow editing the media after capturing/selection
//    open var allowEditing: Boolean = false
//
//    /// Whether to use full screen camera preview on the Table
//    open var useFullScreenCamera: Boolean = false
//
//    /// Enable selfie mode by default
//    open var defaultToFrontFacing: Boolean = false
//
//    /// The UIBarButtonItem to present from (may be replaced by a overloaded methods)
//    open var presentingBarButtonItem: Button? = null
//
//    /// The UIView to present from (may be replaced by a overloaded methods)
//    open var presentingView: View? = null
//
//    open var presentingRect: Rect? = null
//
//    open var presentingTabBar: TabLayout? = null
//
//    open var currentActivity: Activity? = null
//    open val presentingViewController: Activity
//        get() {
//            return currentActivity ?: throw IllegalStateException("currentActivity is not set")
//        }
//
//    private lateinit var cameraHelper: CameraHelper
//    // MARK: - Callbacks
//
//    open var didGetPhoto: ((photo: Bitmap, info: Map<Any, Any>) -> Unit)? = null
//
//    open var didGetVideo: ((video: Uri, info: Map<Any, Any>) -> Unit)? = null
//
//    open var didDeny: (() -> Unit)? = null
//
//    open var didCancel: (() -> Unit)? = null
//
//    open var didFail: (() -> Unit)? = null
//
//    // MARK: - Localization overrides
//
//    // Custom UI text (skips localization)
//    open var cancelText: String? = null
//
//    // Custom UI text (skips localization)
//    open var chooseFromLibraryText: String? = null
//
//    // Custom UI text (skips localization)
//    open var chooseFromPhotoRollText: String? = null
//
//    // Custom UI text (skips localization)
//    open var noSourcesText: String? = null
//
//    // Custom UI text (skips localization)
//    open var takePhotoText: String? = null
//
//    // Custom UI text (skips localization)
//    open var takeVideoText: String? = null
//
//    // MARK: - Methods
//
//    private var alertDialog: AlertDialog? = null
//
//    companion object {
//        const val REQUEST_IMAGE_CAPTURE = 1
//        const val REQUEST_IMAGE_PICK = 2
//    }
//
//    private fun localizeString(string: CameraPluginLocationString): String {
//        return when (string) {
//            CameraPluginLocationString.CANCEL -> cancelText ?: string.stringValue
//            CameraPluginLocationString.CHOOSE_FROM_LIBRARY -> chooseFromLibraryText ?: string.stringValue
//            CameraPluginLocationString.CHOOSE_FROM_PHOTO_ROLL -> chooseFromPhotoRollText ?: string.stringValue
//            CameraPluginLocationString.NO_SOURCES -> noSourcesText ?: string.stringValue
//            CameraPluginLocationString.TAKE_PHOTO -> takePhotoText ?: string.stringValue
//            CameraPluginLocationString.TAKE_VIDEO -> takeVideoText ?: string.stringValue
//        }
//    }
//
//    /// Presents the user with an option to take a photo or choose a photo from the library
//
//    open fun present() {
//        val titleToSource = mutableListOf<Pair<CameraPluginLocationString, Int>>()
//
//        if (allowsTake && isCameraAvailable(presentingViewController)) {
//            if (allowPhoto) {
//                titleToSource.add(
//                    Pair(
//                        CameraPluginLocationString.TAKE_PHOTO,
//                        CameraPlugin.REQUEST_IMAGE_CAPTURE
//                    )
//                )
//            }
//            if (allowVideo) {
//                titleToSource.add(
//                    Pair(
//                        CameraPluginLocationString.TAKE_VIDEO,
//                        CameraPlugin.REQUEST_IMAGE_CAPTURE
//                    )
//                )
//            }
//        }
//
//        if (allowExistingMedia && isPhotoLibraryAvailable(presentingViewController)) {
//            if (allowPhoto) {
//                titleToSource.add(
//                    Pair(
//                        CameraPluginLocationString.CHOOSE_FROM_LIBRARY,
//                        CameraPlugin.REQUEST_IMAGE_PICK
//                    )
//                )
//            }
//            if (allowVideo) {
//                titleToSource.add(
//                    Pair(
//                        CameraPluginLocationString.CHOOSE_FROM_PHOTO_ROLL,
//                        CameraPlugin.REQUEST_IMAGE_PICK
//                    )
//                )
//            }
//        }
//
//        if (titleToSource.size <= 0) {
//            val str = localizeString(CameraPluginLocationString.NO_SOURCES)
//
//            alertDialog = AlertDialog.Builder(presentingViewController)
//                .setTitle(str)
//                .setPositiveButton("OK") { _, _ -> didDeny?.invoke() }
//                .setOnCancelListener { didCancel?.invoke() }
//                .create()
//
//            alertDialog?.show()
//            return
//        }
//
//        if (titleToSource.size >= 1) {
//            showOptionsDialog(presentingViewController)
//        }
//    }
//
//    private fun takePhoto() {
//        //
//    }
//
//    private fun takeVideo() {
//        //
//    }
//
//    private fun chooseFromLibrary() {
//        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        pickPhotoIntent.type = "image/*"
//        pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)
//        if (isPhotoLibraryAvailable(presentingViewController)) {
//            presentingViewController.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
//        }
//    }
//
//    private fun chooseFromPhotoRoll() {
//        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//        pickPhotoIntent.type = "video/*"
//        pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultipleVideos)
//        if (pickPhotoIntent.resolveActivity(presentingViewController.packageManager) != null) {
//            presentingViewController.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
//        }
//    }
//
//    private fun isCameraAvailable(context: Context): Boolean {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val packageManager = context.packageManager
//        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
//    }
//
//    private fun isPhotoLibraryAvailable(context: Context): Boolean {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
//        val packageManager = context.packageManager
//        val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
//        return activities.isNotEmpty()
//    }
//
//    private fun showOptionsDialog(activity : Activity) {
//        val diaLogMenu = FloatingMenuDialog(activity)
//        diaLogMenu.setCancelButtonText(CameraPluginLocationString.CANCEL.stringValue)
//        if(allowPhoto) {
//            diaLogMenu.setTakePhotoButtonText(CameraPluginLocationString.TAKE_PHOTO.stringValue)
//            diaLogMenu.setOnTakePhotoBtnClick {
//                chooseFromLibrary()
//            }
//        }
//        if(allowVideo) {
//            diaLogMenu.setTakeVideoButtonText(CameraPluginLocationString.TAKE_VIDEO.stringValue)
//            diaLogMenu.setOnTakeVideoBtnClick {
//                takeVideo()
//            }
//        }
//        if(allowExistingMedia) {
//            diaLogMenu.setChooseFromLibraryButtonText(CameraPluginLocationString.CHOOSE_FROM_LIBRARY.stringValue)
//            diaLogMenu.setOnChooseFromLibraryBtnClick {
//                chooseFromLibrary()
//            }
//        }
//
//        diaLogMenu.show()
//    }
//}
//
