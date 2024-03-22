import Flutter
import UIKit

class EventHandleChannel: NSObject, FlutterStreamHandler {
    private var eventSink: FlutterEventSink?
    private let channelName: String
    
    // Singleton instance
    static let shared = EventHandleChannel(channelName: "handle_result_channel")
    
    private init(channelName: String) {
        self.channelName = channelName
        super.init()
    }
    
    static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterEventChannel(name: "handle_result_channel", binaryMessenger: registrar.messenger())
        channel.setStreamHandler(shared)
    }
    
    func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
        self.eventSink = events
        sendEvent(dataSend: nil)
        return nil
    }
    
    func onCancel(withArguments arguments: Any?) -> FlutterError? {
        self.eventSink = nil
        return nil
    }
    
    func sendEvent(dataSend: Bool?) {
        self.eventSink?(dataSend)
    }
}

