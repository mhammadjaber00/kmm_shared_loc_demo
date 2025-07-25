import Foundation
import SharedApp

/**
 * Swift helper class for accessing shared string resources from native iOS code.
 * This provides a clean Swift API for accessing the centralized string resources.
 */
class StringResourcesHelper {
    private let sharedResources: SharedStringResources
    
    init() {
        self.sharedResources = SharedStringResourcesKt.createSharedStringResources()
    }
    
    // MARK: - App Information
    var appName: String {
        return sharedResources.getString(key: StringKeysKt.APP_NAME)
    }
    
    // MARK: - UI Strings
    var buttonClickMe: String {
        return sharedResources.getString(key: StringKeysKt.BUTTON_CLICK_ME)
    }
    
    
    // MARK: - Welcome Messages
    var welcomeTitle: String {
        return sharedResources.getString(key: StringKeysKt.WELCOME_TITLE)
    }
    
    var welcomeDescription: String {
        return sharedResources.getString(key: StringKeysKt.WELCOME_DESCRIPTION)
    }
    
    // MARK: - Common Actions
    var actionOk: String {
        return sharedResources.getString(key: StringKeysKt.ACTION_OK)
    }
    
    var actionCancel: String {
        return sharedResources.getString(key: StringKeysKt.ACTION_CANCEL)
    }
    
    var actionRetry: String {
        return sharedResources.getString(key: StringKeysKt.ACTION_RETRY)
    }
    
    // MARK: - Error Messages
    var errorNetwork: String {
        return sharedResources.getString(key: StringKeysKt.ERROR_NETWORK)
    }
    
    var errorGeneric: String {
        return sharedResources.getString(key: StringKeysKt.ERROR_GENERIC)
    }
    
    // MARK: - Sample Texts
    var sampleText1: String {
        return sharedResources.getString(key: StringKeysKt.SAMPLE_TEXT_1)
    }
    
    var sampleText2: String {
        return sharedResources.getString(key: StringKeysKt.SAMPLE_TEXT_2)
    }
    
    var sampleText3: String {
        return sharedResources.getString(key: StringKeysKt.SAMPLE_TEXT_3)
    }
    
    // MARK: - Utility Methods
    
    /**
     * Get a string by key
     */
    func getString(key: String) -> String {
        return sharedResources.getString(key: key)
    }
    
    /**
     * Get a formatted string with parameters
     */
    func getFormattedString(key: String, args: Any...) -> String {
        return sharedResources.getFormattedString(key: key, args: args)
    }
    
    /**
     * Check if current locale is RTL
     */
    var isRTL: Bool {
        return sharedResources.isRTL()
    }
    
    /**
     * Get current language code
     */
    var currentLanguage: String {
        return sharedResources.getCurrentLanguage()
    }
}

// MARK: - Global Instance
/**
 * Shared instance for easy access throughout the app
 */
let SharedStrings = StringResourcesHelper()

// MARK: - SwiftUI Extensions
import SwiftUI

/**
 * SwiftUI extension for easy string resource access
 */
extension Text {
    init(stringKey: String) {
        self.init(SharedStrings.getString(key: stringKey))
    }
}

/**
 * Button extension for shared string resources
 */
extension Button where Label == Text {
    init(stringKey: String, action: @escaping () -> Void) {
        self.init(action: action) {
            Text(SharedStrings.getString(key: stringKey))
        }
    }
}