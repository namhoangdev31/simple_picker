import Flutter
import UIKit

@available(iOS 13.0, *)
public class SimplePickerPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "simple_picker", binaryMessenger: registrar.messenger())
        let instance = SimplePickerPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
        EventHandleChannel.register(with: registrar)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
            case "getPlatformVersion":
                result("iOS " + UIDevice.current.systemVersion)
            case "pickImageWithTakePhoto":
                pickImageWithTakePhoto(call, result: result)
//            case "pickImageWithPhotoLibrary":
//                pickImageWithPhotoLibrary(call, result: result)
            default:
                result(FlutterMethodNotImplemented)
        }
    }
    
    public func pickImageWithTakePhoto(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        do {
//            var argument = call.arguments as! [String: Any]
//            var source = argument["source"] as! String
            let cameraPlugin = CameraPlugin()
            cameraPlugin.allowsVideo = false
            cameraPlugin.allowsSelectFromLibrary = false
            cameraPlugin.didGetPhoto = { photo, _ in
                    /// Handle the selected photo here
                    /// You can return the image path to Dart if needed
                let xFile = self.convertImageToFile(image: photo)
                result(xFile)
            }
            cameraPlugin.present()
        } catch {
            print("LOG ERROR")
            result(FlutterError(code: "invalid_arguments", message: error.localizedDescription, details: nil))
        }
    }
    
    public func pickImageWithPhotoLibrary(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        do {
//            var argument = call.arguments as! [String: Any]
//            var source = argument["source"] as! String
            let cameraPlugin = CameraPlugin()
            cameraPlugin.allowsPhoto = false
            cameraPlugin.allowsVideo = false
            cameraPlugin.allowsSelectFromLibrary = true
            cameraPlugin.didGetPhoto = { photo, _ in
                    /// Handle the selected photo here
                    /// You can return the image path to Dart if needed
                let xFile = self.convertImageToFile(image: photo)
                result(xFile)
            }
            cameraPlugin.present()
        } catch {
            print("LOG ERROR")
            result(FlutterError(code: "invalid_arguments", message: error.localizedDescription, details: nil))
        }
    }
    public func convertToPNG(image: UIImage) -> Data? {
        return image.pngData()
    }
    
    public func saveImageDataToTemporaryFile(imageData: Data) -> String? {
        let temporaryDirectory = FileManager.default.temporaryDirectory
        let currentDate = Date()
        let timestamp = currentDate.timeIntervalSince1970
        let fileName = "\(Int(timestamp)).png" // Tên tệp tạm thời có thể tùy chỉnh
        print(fileName)
        let fileURL = temporaryDirectory.appendingPathComponent(fileName)
        do {
            try imageData.write(to: fileURL)
            return fileURL.path
        } catch {
            print("Failed to save image data to temporary file: \(error)")
            return nil
        }
    }
    
    public func convertImageToFile(image: UIImage) -> String? {
        guard let imageData = convertToPNG(image: image) else {
            return nil
        }
        guard let fileURL = saveImageDataToTemporaryFile(imageData: imageData) else {
            return nil
        }
        return fileURL
    }
}
