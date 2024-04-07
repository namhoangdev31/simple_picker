    //
    //  PhotoPicker.swift
    //  simple_picker
    //
    //  Created by LaptopZone on 05/04/2024.
    //

import UIKit
import MobileCoreServices
import Flutter
import AVFoundation


class PhotoPickers: NSObject {
    open class func getPhotoWithCallback(getPhotoWithCallback callback: @escaping (_ photo: UIImage, _ info: [AnyHashable: Any]) -> Void) {
        let photoPlugin = PhotoPickers()
        photoPlugin.allowsVideo = false
        photoPlugin.didGetPhoto = callback
        photoPlugin.present()
    }
        //
        //    /// Convenience method for getting a video
        //    open class func getVideoWithCallback(getVideoWithCallback callback: @escaping (_ video: URL, _ info: [AnyHashable: Any]) -> Void) {
        //        let camPlugin = CameraPlugin()
        //        camPlugin.allowsPhoto = false
        //        camPlugin.didGetVideo = callback
        //        camPlugin.present()
        //    }
    
    
        // MARK: - Configuration options
    
        /// Whether to allow selecting a photo
    open var allowsPhoto = true
    
        /// Whether to allow selecting a video
    open var allowsVideo = true
    
        ///Whether to allow pick multi photos or videos with photo library
    open var allowMulti = false
    
        /// Whether to allow capturing a photo/video with the camera
    open var allowsTake = true
    
        ///  That's the width of the image that the image was designed for (e.g. 375.0)
    open var reDesignWidth: Double = 0.0
    
        ///  That's the height of the image that the image was designed for (e.g. 667.0)
    open var reDesignHeight: Double = 0.0
    
        /// Whether to allow selecting existing media
    open var allowsSelectFromLibrary = true
    
        /// Whether to allow editing the media after capturing/selection
    open var allowsEditing = false
    
        /// Whether to use full screen camera preview on the iPad
    open var iPadUsesFullScreenCamera = false
    
        /// Enable selfie mode by default
    open var defaultsToFrontCamera = false
    
        /// The UIBarButtonItem to present from (may be replaced by a overloaded methods)
    open var presentingBarButtonItem: UIBarButtonItem? = nil
    
        /// The UIView to present from (may be replaced by a overloaded methods)
    open var presentingView: UIView? = nil
    
        /// The UIRect to present from (may be replaced by a overloaded methods)
    open var presentingRect: CGRect? = nil
    
        /// The UITabBar to present from (may be replaced by a overloaded methods)
    open var presentingTabBar: UITabBar? = nil
    
        /// The UIViewController to present from (may be replaced by a overloaded methods)
    open lazy var presentingViewController: UIViewController = {
        return UIApplication.shared.keyWindow!.rootViewController!
    }()
    
    
        // MARK: - Callbacks
    
        /// A photo was selected
    open var didGetPhoto: ((_ photo: UIImage, _ info: [AnyHashable: Any]) -> Void)?
    
        /// A video was selected
    open var didGetVideo: ((_ video: URL, _ info: [AnyHashable: Any]) -> Void)?
    
        /// The user did not attempt to select a photo
    open var didDeny: (() -> Void)?
    
        /// The user started selecting a photo or took a photo and then hit cancel
    open var didCancel: (() -> Void)?
    
        /// A photo or video was selected but the ImagePicker had NIL for EditedImage and OriginalImage
    open var didFail: (() -> Void)?
    
    
        // MARK: - Localization overrides
    
        /// Custom UI text (skips localization)
    open var cancelText: String? = nil
    
        /// Custom UI text (skips localization)
    open var chooseFromLibraryText: String? = nil
    
        /// Custom UI text (skips localization)
    open var chooseFromPhotoRollText: String? = nil
    
        /// Custom UI text (skips localization)
    open var noSourcesText: String? = nil
    
        /// Custom UI text (skips localization)
    open var takePhotoText: String? = nil
    
        /// Custom UI text (skips localization)
    open var takeVideoText: String? = nil
    
    private lazy var picker: UIImagePickerController = {
        [unowned self] in
        let retval = CustomImagePickerController()
        retval.delegate = self
            //        retval.allowsEditing = true
        return retval
    }()
    
    private var alertController: UIAlertController? = nil
    
    private func topViewController(rootViewController: UIViewController) -> UIViewController {
        var rootViewController = UIApplication.shared.keyWindow!.rootViewController!
        repeat {
            guard let presentedViewController = rootViewController.presentedViewController else {
                return rootViewController
            }
            
            if let navigationController = rootViewController.presentedViewController as? UINavigationController {
                rootViewController = navigationController.topViewController ?? navigationController
                
            } else {
                rootViewController = presentedViewController
            }
        } while true
    }
    
    private func localizeString(_ string:CameraPluginLocationString) -> String {
        let bundle = Bundle(for: type(of: self))
            //let stringsURL = bundle.resourceURL!.appendingPathComponent("Localizable.strings")
        let bundleLocalization = bundle.localizedString(forKey: string.rawValue, value: nil, table: nil)
            //let a = NSLocal
            //let bundleLocalization = NSLocalizedString(string.rawValue, tableName: nil, bundle: bundle, value: string.rawValue, comment: string.comment())
        
        
        switch string {
            case .cancel:
                return self.cancelText ?? bundleLocalization
            case .chooseFromLibrary:
                return self.chooseFromLibraryText ?? bundleLocalization
            case .chooseFromPhotoRoll:
                return self.chooseFromPhotoRollText ?? bundleLocalization
            case .noSources:
                return self.noSourcesText ?? bundleLocalization
            case .takePhoto:
                return self.takePhotoText ?? bundleLocalization
            case .takeVideo:
                return self.takeVideoText ?? bundleLocalization
        }
    }
    
    open func present() {
        var titleToSource = [(buttonTitle: CameraPluginLocationString, source: UIImagePickerController.SourceType)]()
        
        if self.allowsSelectFromLibrary {
            if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
                titleToSource.append((buttonTitle: .chooseFromLibrary, source: .photoLibrary))
            } else if UIImagePickerController.isSourceTypeAvailable(.savedPhotosAlbum) {
                titleToSource.append((buttonTitle: .chooseFromPhotoRoll, source: .savedPhotosAlbum))
            }
        }
        
        guard titleToSource.count > 0 else {
            let str = localizeString(.noSources)
            let alert = UIAlertController(title: nil, message: str, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: localizeString(.cancel), style: .default, handler: nil))
            
            let alertWindow = UIWindow(frame: UIScreen.main.bounds)
            alertWindow.rootViewController = UIViewController()
            alertWindow.windowLevel = UIWindow.Level.alert + 1;
            alertWindow.makeKeyAndVisible()
            alertWindow.rootViewController?.present(alert, animated: true, completion: nil)
            return
        }
        
        alertController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)
        for (title, source) in titleToSource {
            let action = UIAlertAction(title: localizeString(title), style: .default) {
                (UIAlertAction) -> Void in
                self.picker.sourceType = source
                
                var mediaTypes = [String]()
                if self.allowsPhoto {
                    mediaTypes.append(String(kUTTypeImage))
                }
                if self.allowsVideo {
                    mediaTypes.append(String(kUTTypeMovie))
                }
                self.picker.mediaTypes = mediaTypes
                
                var popOverPresentRect: CGRect = self.presentingRect ?? CGRect(x: 0, y: 0, width: 1, height: 1)
                if popOverPresentRect.size.height == 0 || popOverPresentRect.size.width == 0 {
                    popOverPresentRect = CGRect(x: 0, y: 0, width: 1, height: 1)
                }
                let topVC = self.topViewController(rootViewController: self.presentingViewController)
                
                if UI_USER_INTERFACE_IDIOM() == .phone || (source == .camera && self.iPadUsesFullScreenCamera) {
                    topVC.present(self.picker, animated: true, completion: nil)
                } else {
                    self.picker.modalPresentationStyle = .popover
                    self.picker.popoverPresentationController?.sourceRect = popOverPresentRect
                    topVC.present(self.picker, animated: true, completion: nil)
                }
            }
            alertController!.addAction(action)
        }
        
        let cancelAction = UIAlertAction(title: localizeString(.cancel), style: .cancel) {
            (UIAlertAction) -> Void in
            self.didCancel?()
        }
        alertController!.addAction(cancelAction)
        
        let topVC = topViewController(rootViewController: presentingViewController)
        
        alertController?.modalPresentationStyle = .popover
        if let presenter = alertController!.popoverPresentationController {
            presenter.sourceView = presentingView;
            if let presentingRect = self.presentingRect {
                presenter.sourceRect = presentingRect
            }
        }
        topVC.present(alertController!, animated: true, completion: nil)
    }
    
    
        /// Dismisses the displayed view. Especially handy if the sheet is displayed while suspending the app,
    open func dismiss() {
        alertController?.dismiss(animated: true, completion: nil)
        picker.dismiss(animated: true, completion: nil)
    }
}


extension PhotoPickers: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            // Local variable inserted by Swift 4.2 migrator.
        let info = convertFromUIImagePickerControllerInfoKeyDictionary(info)
        
        UIApplication.shared.isStatusBarHidden = true
        let mediaType: String = info[convertFromUIImagePickerControllerInfoKey(UIImagePickerController.InfoKey.mediaType)] as! String
        var imageToSave: UIImage
            // Handle a still image capture
        if mediaType == kUTTypeImage as String {
            if let editedImage = info[convertFromUIImagePickerControllerInfoKey(UIImagePickerController.InfoKey.editedImage)] as? UIImage {
                let rotatedImage = editedImage.rotate(radians: .pi * 2)
                imageToSave = rotatedImage
            } else if let originalImage = info[convertFromUIImagePickerControllerInfoKey(UIImagePickerController.InfoKey.originalImage)] as? UIImage {
                let rotatedImage = originalImage.rotate(radians: .pi * 2)
//                let resizeImage = rotatedImage.imageResize(newSize: CGSize(width: reDesignWidth, height: reDesignHeight))
                imageToSave = rotatedImage
            } else {
                self.didCancel?()
                return
            }
            self.didGetPhoto?(imageToSave, info)
            if UI_USER_INTERFACE_IDIOM() == .pad {
                self.picker.dismiss(animated: true)
            }
        } else if mediaType == kUTTypeMovie as String {
            self.didGetVideo?(info[convertFromUIImagePickerControllerInfoKey(UIImagePickerController.InfoKey.mediaURL)] as! URL, info)
        }
            //        EventHandleChannel.shared.sendEvent(dataSend: true)
        picker.dismiss(animated: true, completion: nil)
    }
    
        /// Conformance for image picker delegate
    public func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        UIApplication.shared.isStatusBarHidden = true
            //        EventHandleChannel.shared.sendEvent(dataSend: true)
        picker.dismiss(animated: true, completion: nil)
        self.didDeny?()
    }
    
        // Helper function inserted by Swift 4.2 migrator.
    private func convertFromUIImagePickerControllerInfoKeyDictionary(_ input: [UIImagePickerController.InfoKey: Any]) -> [String: Any] {
        return Dictionary(uniqueKeysWithValues: input.map {key, value in (key.rawValue, value)})
    }
    
        // Helper function inserted by Swift 4.2 migrator.
    private func convertFromUIImagePickerControllerInfoKey(_ input: UIImagePickerController.InfoKey) -> String {
        return input.rawValue
    }
}
