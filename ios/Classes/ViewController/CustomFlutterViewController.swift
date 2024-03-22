import UIKit
import Flutter

class CustomFlutterViewController: FlutterViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Khởi tạo gesture recognizer và gán action handleTap
//        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleTap(_:)))
//        self.view.addGestureRecognizer(tapGesture)
    }
    
    // Override các phương thức của UIViewController để chặn sự kiện từ UIImagePickerController
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {}
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {}
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {}
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {}
    
    @objc func handleTap(_ sender: UITapGestureRecognizer) {
        // Xử lý sự kiện khi người dùng chạm vào màn hình
        let touchLocation = sender.location(in: self.view)
        print("Tapped at location: \(touchLocation)")
    }
}
