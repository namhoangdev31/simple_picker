#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint plugin_example.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'simple_picker'
  s.version          = '0.0.3'
  s.summary          = 'Simple picker is fix issue of image_picker plugin on iOS 14.5'
  s.description      = <<-DESC
  Simple picker is fix issue of image_picker plugin on iOS 14.5
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.platform = :ios, '13.0'
  s.dependency 'FDTake'
  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
  s.swift_version = '5.0'
end
