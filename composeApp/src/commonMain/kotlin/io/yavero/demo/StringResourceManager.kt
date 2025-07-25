package io.yavero.demo

/**
 * Cross-platform string resource manager that provides access to shared string resources
 * without any Compose dependencies. Works with native Android XML layouts and iOS Storyboards.
 */
expect class StringResourceManager {
    
    /**
     * Get a string resource by its key name.
     * @param key The string resource key (e.g., "app_name", "button_click_me")
     * @return The localized string value
     */
    fun getString(key: String): String
    
    /**
     * Get a formatted string resource with parameters.
     * @param key The string resource key
     * @param args The arguments to format the string
     * @return The formatted localized string
     */
    fun getFormattedString(key: String, vararg args: Any): String
    
    /**
     * Check if the current locale is RTL (Right-to-Left).
     * Useful for Arabic and other RTL languages.
     * @return true if current locale is RTL, false otherwise
     */
    fun isRTL(): Boolean
    
    /**
     * Get the current locale language code.
     * @return Language code (e.g., "en", "ar")
     */
    fun getCurrentLanguage(): String
    
    /**
     * Get all available string keys.
     * @return Set of all string resource keys
     */
    fun getAllKeys(): Set<String>
}

/**
 * String resource keys for type-safe access to shared strings.
 * These constants match the keys in the JSON resource files.
 */
object StringKeys {
    // App information
    const val APP_NAME = "app_name"
    
    // UI strings
    const val BUTTON_CLICK_ME = "button_click_me"
    
    // Welcome messages
    const val WELCOME_TITLE = "welcome_title"
    const val WELCOME_DESCRIPTION = "welcome_description"
    
    // Common actions
    const val ACTION_OK = "action_ok"
    const val ACTION_CANCEL = "action_cancel"
    const val ACTION_RETRY = "action_retry"
    
    // Error messages
    const val ERROR_NETWORK = "error_network"
    const val ERROR_GENERIC = "error_generic"
    
    // Sample texts
    const val SAMPLE_TEXT_1 = "sample_text_1"
    const val SAMPLE_TEXT_2 = "sample_text_2"
    const val SAMPLE_TEXT_3 = "sample_text_3"
    
    // Native demo strings
    const val NATIVE_DEMO_TITLE = "native_demo_title"
    const val NATIVE_DEMO_DESCRIPTION = "native_demo_description"
    const val COUNTER_LABEL = "counter_label"
    const val LANGUAGE_INFO = "language_info"
    const val CURRENT_LANGUAGE = "current_language"
    const val IS_RTL = "is_rtl"
}

/**
 * Convenience object for easy access to string resources.
 * This provides a simple API for accessing shared strings across platforms.
 */
expect object SharedStrings {
    
    /**
     * Get a string resource by key.
     */
    fun getString(key: String): String
    
    /**
     * Get a formatted string resource with parameters.
     */
    fun getFormattedString(key: String, vararg args: Any): String
    
    // Common string accessors for easy access
    val appName: String
    val buttonClickMe: String
    val welcomeTitle: String
    val welcomeDescription: String
    val actionOk: String
    val actionCancel: String
    val actionRetry: String
    val errorNetwork: String
    val errorGeneric: String
    val nativeDemoTitle: String
    val nativeDemoDescription: String
}