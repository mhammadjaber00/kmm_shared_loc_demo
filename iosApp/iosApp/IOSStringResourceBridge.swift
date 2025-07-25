import Foundation
import SharedApp

/**
 * iOS-specific bridge that generates traditional iOS Localizable.strings files
 * from shared string resources. This allows using shared strings in Storyboards.
 */
class IOSStringResourceBridge {
    
    private let stringManager = StringResourceManager()
    
    /**
     * Generate iOS Localizable.strings file content from shared resources.
     * This creates the traditional iOS resource files that can be used in Storyboards.
     */
    func generateIOSLocalizableStrings(language: String = "en") -> String {
        let strings = getStringsForLanguage(language: language)
        
        var localizableContent = "/* Generated Localizable.strings from shared resources */\n\n"
        
        for (key, value) in strings.sorted(by: { $0.key < $1.key }) {
            // Escape quotes in the value
            let escapedValue = value.replacingOccurrences(of: "\"", with: "\\\"")
            localizableContent += "\"\(key)\" = \"\(escapedValue)\";\n"
        }
        
        return localizableContent
    }
    
    /**
     * Get all strings for a specific language from the shared resource manager.
     */
    private func getStringsForLanguage(language: String) -> [String: String] {
        // Since we're using hardcoded strings in the iOS implementation,
        // we'll recreate the same structure here
        if language == "ar" {
            return [
                "app_name": "تطبيق تجريبي",
                "button_click_me": "اضغط هنا!",
                "welcome_title": "مرحباً بك في تطبيق KMP التجريبي",
                "welcome_description": "هذا يوضح موارد النصوص المشتركة بين أندرويد و iOS",
                "action_ok": "موافق",
                "action_cancel": "إلغاء",
                "action_retry": "إعادة المحاولة",
                "error_network": "حدث خطأ في الشبكة",
                "error_generic": "حدث خطأ ما",
                "sample_text_1": "هذا مورد نص مشترك",
                "sample_text_2": "متاح على كل من أندرويد و iOS",
                "sample_text_3": "مركزي وسهل الصيانة",
                "native_demo_title": "عرض واجهة المستخدم الأصلية",
                "native_demo_description": "هذا يوضح استخدام النصوص المشتركة في تخطيطات Android XML الأصلية و iOS Storyboards",
                "counter_label": "العداد: %d",
                "language_info": "معلومات اللغة:",
                "current_language": "اللغة الحالية: %@",
                "is_rtl": "من اليمين إلى اليسار: %@"
            ]
        } else {
            return [
                "app_name": "demo",
                "button_click_me": "Click me!",
                "welcome_title": "Welcome to KMP Demo",
                "welcome_description": "This demonstrates shared string resources between Android and iOS",
                "action_ok": "OK",
                "action_cancel": "Cancel",
                "action_retry": "Retry",
                "error_network": "Network error occurred",
                "error_generic": "Something went wrong",
                "sample_text_1": "This is a shared string resource",
                "sample_text_2": "Available on both Android and iOS",
                "sample_text_3": "Centralized and easy to maintain",
                "native_demo_title": "Native UI Demo",
                "native_demo_description": "This demonstrates using shared strings in native Android XML layouts and iOS Storyboards",
                "counter_label": "Counter: %d",
                "language_info": "Language Info:",
                "current_language": "Current Language: %@",
                "is_rtl": "Is RTL: %@"
            ]
        }
    }
    
    /**
     * Save generated Localizable.strings to the app's documents directory for demonstration.
     * In a real app, this would be part of the build process.
     */
    func saveGeneratedLocalizableStrings(language: String = "en") -> String {
        let content = generateIOSLocalizableStrings(language: language)
        let fileName = language == "ar" ? "Localizable_ar.strings" : "Localizable.strings"
        
        guard let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first else {
            return "Error: Could not access documents directory"
        }
        
        let fileURL = documentsDirectory.appendingPathComponent(fileName)
        
        do {
            try content.write(to: fileURL, atomically: true, encoding: .utf8)
            return "Generated \(fileName) saved to: \(fileURL.path)"
        } catch {
            return "Error saving \(fileName): \(error.localizedDescription)"
        }
    }
    
    /**
     * Get all available string keys from the shared resources.
     */
    func getAllStringKeys() -> Set<String> {
        let strings = getStringsForLanguage(language: "en")
        return Set(strings.keys)
    }
    
    /**
     * Demonstrate how to access shared strings in traditional iOS code.
     */
    func demonstrateStringAccess() -> [String: String] {
        var demo: [String: String] = [:]
        
        // Show how to access common strings
        demo["App Name"] = stringManager.getString(key: "app_name")
        demo["Welcome Title"] = stringManager.getString(key: "welcome_title")
        demo["Button Text"] = stringManager.getString(key: "button_click_me")
        demo["OK Action"] = stringManager.getString(key: "action_ok")
        demo["Cancel Action"] = stringManager.getString(key: "action_cancel")
        
        // Show formatted strings
        demo["Counter Example"] = stringManager.getFormattedString(key: "counter_label", args: [42])
        demo["Language Info"] = stringManager.getFormattedString(key: "current_language", args: [stringManager.getCurrentLanguage()])
        demo["RTL Status"] = stringManager.getFormattedString(key: "is_rtl", args: [stringManager.isRTL() ? "Yes" : "No"])
        
        return demo
    }
}

/**
 * Utility functions for Storyboard integration
 */
class IOSStoryboardIntegration {
    
    private static let stringManager = StringResourceManager()
    
    /**
     * Get a string resource for use in Storyboards or traditional iOS Views.
     * This can be called from ViewControllers or any UIKit component.
     */
    static func getString(key: String) -> String {
        return stringManager.getString(key: key)
    }
    
    /**
     * Get a formatted string resource for use in Storyboards or traditional iOS Views.
     */
    static func getFormattedString(key: String, args: [Any]) -> String {
        return stringManager.getFormattedString(key: key, args: args)
    }
    
    /**
     * Set text on a UILabel using shared string resources.
     * Example: IOSStoryboardIntegration.setLabelTextFromSharedString(label, key: "welcome_title")
     */
    static func setLabelTextFromSharedString(_ label: UILabel, key: String) {
        label.text = getString(key: key)
    }
    
    /**
     * Set button title using shared string resources.
     * Example: IOSStoryboardIntegration.setButtonTitleFromSharedString(button, key: "action_ok")
     */
    static func setButtonTitleFromSharedString(_ button: UIButton, key: String, for state: UIControl.State = .normal) {
        button.setTitle(getString(key: key), for: state)
    }
    
    /**
     * Set navigation title using shared string resources.
     * Example: IOSStoryboardIntegration.setNavigationTitleFromSharedString(viewController, key: "app_name")
     */
    static func setNavigationTitleFromSharedString(_ viewController: UIViewController, key: String) {
        viewController.title = getString(key: key)
    }
    
    /**
     * Show alert with shared string resources.
     * Example: IOSStoryboardIntegration.showAlertWithSharedStrings(viewController, titleKey: "error_generic", messageKey: "error_network")
     */
    static func showAlertWithSharedStrings(_ viewController: UIViewController, titleKey: String, messageKey: String) {
        let alert = UIAlertController(
            title: getString(key: titleKey),
            message: getString(key: messageKey),
            preferredStyle: .alert
        )
        
        alert.addAction(UIAlertAction(
            title: getString(key: "action_ok"),
            style: .default
        ))
        
        viewController.present(alert, animated: true)
    }
}