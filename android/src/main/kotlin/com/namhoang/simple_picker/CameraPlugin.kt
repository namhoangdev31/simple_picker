package com.namhoang.simple_picker

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import com.google.android.material.tabs.TabLayout
import com.namhoang.dialogmenu.FloatingMenuDialog


open class CameraPlugin {
    // MARK: - Configuration options

    /// Whether to allow selecting a photo
    var allowPhoto: Boolean = false

    /// Whether to allow selecting a video
    var allowVideo: Boolean = false

    /// Whether to allow selecting multiple photos
    var allowMultiple: Boolean = false

    /// Whether to allow selecting multiple videos

    var allowMultipleVideos: Boolean = false

    /// Whether to allow capturing a photo/video with the camera
    var allowsTake: Boolean = true

    ///  That's the width of the image that the image was designed for (e.g. 375.0)
    var targetWidth: Double = 0.0

    /// That's the height of the image that the image was designed for (e.g. 667.0)
    var targetHeight: Double = 0.0

    /// Whether to allow selecting existing media
    var allowExistingMedia: Boolean = false

    /// Whether to allow selecting multiple media
    var allowMultipleMedia: Boolean = false

    /// Whether to allow editing the media after capturing/selection
    var allowEditing: Boolean = false

    /// Whether to use full screen camera preview on the Table
    var useFullScreenCamera: Boolean = false

    /// Enable selfie mode by default
    var defaultToFrontFacing: Boolean = false

    /// The UIBarButtonItem to present from (may be replaced by a overloaded methods)
    var presentingBarButtonItem: Button? = null

    /// The UIView to present from (may be replaced by a overloaded methods)
    var presentingView: View? = null

    var presentingRect: Rect? = null

    var presentingTabBar: TabLayout? = null

    var currentActivity: Activity? = null
    val presentingViewController: Activity
        get() {
            return currentActivity ?: throw IllegalStateException("currentActivity is not set")
        }

    // MARK: - Callbacks

    var didGetPhoto: ((photo: Bitmap, info: Map<Any, Any>) -> Unit)? = null

    var didGetVideo: ((video: Uri, info: Map<Any, Any>) -> Unit)? = null

    var didDeny: (() -> Unit)? = null

    var didCancel: (() -> Unit)? = null

    var didFail: (() -> Unit)? = null

    // MARK: - Localization overrides

    // Custom UI text (skips localization)
    var cancelText: String? = null

    // Custom UI text (skips localization)
    var chooseFromLibraryText: String? = null

    // Custom UI text (skips localization)
    var chooseFromPhotoRollText: String? = null

    // Custom UI text (skips localization)
    var noSourcesText: String? = null

    // Custom UI text (skips localization)
    var takePhotoText: String? = null

    // Custom UI text (skips localization)
    var takeVideoText: String? = null

    // MARK: - Methods

    private var alertDialog: AlertDialog? = null

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
    }

    private fun localizeString(string: CameraPluginLocationString): String {
        return when (string) {
            CameraPluginLocationString.CANCEL -> cancelText ?: string.stringValue
            CameraPluginLocationString.CHOOSE_FROM_LIBRARY -> chooseFromLibraryText ?: string.stringValue
            CameraPluginLocationString.CHOOSE_FROM_PHOTO_ROLL -> chooseFromPhotoRollText ?: string.stringValue
            CameraPluginLocationString.NO_SOURCES -> noSourcesText ?: string.stringValue
            CameraPluginLocationString.TAKE_PHOTO -> takePhotoText ?: string.stringValue
            CameraPluginLocationString.TAKE_VIDEO -> takeVideoText ?: string.stringValue
        }
    }

    /// Presents the user with an option to take a photo or choose a photo from the library

    open fun present(){
        val titleToSource = mutableListOf<Pair<CameraPluginLocationString, Int>>()

        if (allowsTake && isCameraAvailable(presentingViewController)) {
            if (allowPhoto) {
                titleToSource.add(Pair(CameraPluginLocationString.TAKE_PHOTO, CameraPlugin.REQUEST_IMAGE_CAPTURE))
            }
            if (allowVideo) {
                titleToSource.add(Pair(CameraPluginLocationString.TAKE_VIDEO, CameraPlugin.REQUEST_IMAGE_CAPTURE))
            }
        }

        if (allowExistingMedia && isPhotoLibraryAvailable(presentingViewController)) {
            if (allowPhoto) {
                titleToSource.add(Pair(CameraPluginLocationString.CHOOSE_FROM_LIBRARY, CameraPlugin.REQUEST_IMAGE_PICK))
            }
            if (allowVideo) {
                titleToSource.add(Pair(CameraPluginLocationString.CHOOSE_FROM_PHOTO_ROLL, CameraPlugin.REQUEST_IMAGE_PICK))
            }
        }

        if (titleToSource.size <= 0 ) {
            val str = localizeString(CameraPluginLocationString.NO_SOURCES)

            alertDialog = AlertDialog.Builder(presentingViewController)
                .setTitle(str)
                .setPositiveButton("Check") { _, _ -> didDeny?.invoke() }.setPositiveButton("c") { _, _ -> didDeny?.invoke() }
                .setPositiveButton("OK") { _, _ -> didDeny?.invoke() }
                .setOnCancelListener { didCancel?.invoke() }
                .create()

            alertDialog?.show()
            return
        }

        if (titleToSource.size == 1) {
            val (title, source) = titleToSource.first()
            showOptionsDialog(presentingViewController)
        } else {
            val items = titleToSource.map { localizeString(it.first) }.toTypedArray()

            alertDialog = AlertDialog.Builder(presentingViewController)
                .setTitle("Choose a source")
                .setItems(items) { _, which ->
                    val (title, source) = titleToSource[which]
                    when (title) {
                        CameraPluginLocationString.TAKE_PHOTO -> takePhoto()
                        CameraPluginLocationString.TAKE_VIDEO -> takeVideo()
                        CameraPluginLocationString.CHOOSE_FROM_LIBRARY -> chooseFromLibrary()
                        CameraPluginLocationString.CHOOSE_FROM_PHOTO_ROLL -> chooseFromPhotoRoll()
                        else -> didDeny?.invoke()
                    }
                }
                .setOnCancelListener { didCancel?.invoke() }
                .create()
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(presentingViewController.packageManager) != null) {
            presentingViewController.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun takeVideo() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takeVideoIntent.resolveActivity(presentingViewController.packageManager) != null) {
            presentingViewController.startActivityForResult(takeVideoIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun chooseFromLibrary() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhotoIntent.type = "image/*"
        pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)
        if (pickPhotoIntent.resolveActivity(presentingViewController.packageManager) != null) {
            presentingViewController.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
        }
    }

    private fun chooseFromPhotoRoll() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        pickPhotoIntent.type = "video/*"
        pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultipleVideos)
        if (pickPhotoIntent.resolveActivity(presentingViewController.packageManager) != null) {
            presentingViewController.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
        }
    }

    fun isCameraAvailable(context: Context): Boolean {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = context.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    fun isPhotoLibraryAvailable(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val packageManager = context.packageManager
        val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return activities.isNotEmpty()
    }

    fun showOptionsDialog(activity : Activity) {
        FloatingMenuDialog(activity)
                .setDialogTitle("Add Picture")
                .setPositveButtonText("From Camera")
                .setNeutralButtonText("From Gallery")
                .setExtraButtonText("From Google Drive")
                .setNegativeButtonText("Close Dialog")
                .setDismissDialogOnMenuOnClick(false) //Dismiss the dialog anytime a menu item is clicked
                .setDialogCancelable(true) // Set dialog cancellable
                .setOnPositiveButtonOnClick(object : OnMenuItemClickListener {
                    override fun onClick() {
                        Toast.makeText(activity, "Positive", Toast.LENGTH_SHORT).show()
                    }
                })
                .setOnNegativeButtonOnClick(object : OnMenuItemClickListener {
                    override fun onClick() {
                        Toast.makeText(activity, "Negative", Toast.LENGTH_SHORT).show()
                    }
                })
                .setOnNeutralButtonOnClick(object : OnMenuItemClickListener {
                    override fun onClick() {
                        Toast.makeText(activity, "Neutral", Toast.LENGTH_SHORT).show()
                    }
                })
                .setOnExtraButtonOnClick(object : OnMenuItemClickListener {
                    override fun onClick() {
                        Toast.makeText(activity, "Extra", Toast.LENGTH_SHORT).show()
                    }
                })
                .show()

    }
}