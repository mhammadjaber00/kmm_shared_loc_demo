package io.yavero.demo

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * iOS implementation of StringResourceManager.
 * Loads JSON string resources and provides access for native iOS Storyboards.
 */
@OptIn(ExperimentalForeignApi::class)
actual class StringResourceManager {
    
    private var stringCache: Map<String, String>? = null
    
    private fun loadStrings(): Map<String, String> {
        if (stringCache != null) return stringCache!!
        
        val language = getCurrentLanguage()
        
        // For now, use hardcoded strings until we can properly load JSON files
        val strings = if (language == "ar") {
            mapOf(
                "app_name" to "تطبيق تجريبي",
                "button_click_me" to "اضغط هنا!",
                "welcome_title" to "مرحباً بك في تطبيق KMP التجريبي",
                "welcome_description" to "هذا يوضح موارد النصوص المشتركة بين أندرويد و iOS",
                "action_ok" to "موافق",
                "action_cancel" to "إلغاء",
                "action_retry" to "إعادة المحاولة",
                "error_network" to "حدث خطأ في الشبكة",
                "error_generic" to "حدث خطأ ما",
                "sample_text_1" to "هذا مورد نص مشترك",
                "sample_text_2" to "متاح على كل من أندرويد و iOS",
                "sample_text_3" to "مركزي وسهل الصيانة",
                "native_demo_title" to "عرض واجهة المستخدم الأصلية",
                "native_demo_description" to "هذا يوضح استخدام النصوص المشتركة في تخطيطات Android XML الأصلية و iOS Storyboards",
                "counter_label" to "العداد: %d",
                "language_info" to "معلومات اللغة:",
                "current_language" to "اللغة الحالية: %s",
                "is_rtl" to "من اليمين إلى اليسار: %s"
            )
        } else {
            mapOf(
                "app_name" to "demo",
                "button_click_me" to "Click me!",
                "welcome_title" to "Welcome to KMP Demo",
                "welcome_description" to "This demonstrates shared string resources between Android and iOS",
                "action_ok" to "OK",
                "action_cancel" to "Cancel",
                "action_retry" to "Retry",
                "error_network" to "Network error occurred",
                "error_generic" to "Something went wrong",
                "sample_text_1" to "This is a shared string resource",
                "sample_text_2" to "Available on both Android and iOS",
                "sample_text_3" to "Centralized and easy to maintain",
                "native_demo_title" to "Native UI Demo",
                "native_demo_description" to "This demonstrates using shared strings in native Android XML layouts and iOS Storyboards",
                "counter_label" to "Counter: %d",
                "language_info" to "Language Info:",
                "current_language" to "Current Language: %s",
                "is_rtl" to "Is RTL: %s"
            )
        }
        
        stringCache = strings
        return strings
    }
    
    actual fun getString(key: String): String {
        val strings = loadStrings()
        return strings[key] ?: "String not found: $key"
    }
    
    actual fun getFormattedString(key: String, vararg args: Any): String {
        val template = getString(key)
        return try {
            // Simple string formatting for iOS
            var result = template
            args.forEachIndexed { index, arg ->
                result = result.replace("%${index + 1}\$s", arg.toString())
                result = result.replace("%s", arg.toString())
                result = result.replace("%d", arg.toString())
            }
            result
        } catch (e: Exception) {
            "Error formatting string: $key"
        }
    }
    
    actual fun isRTL(): Boolean {
        val locale = NSLocale.currentLocale
        val language = locale.languageCode
        return language == "ar" || language == "he" || language == "fa" || language == "ur"
    }
    
    actual fun getCurrentLanguage(): String {
        return NSLocale.currentLocale.languageCode ?: "en"
    }
    
    actual fun getAllKeys(): Set<String> {
        return loadStrings().keys
    }
}

/**
 * iOS implementation of SharedStrings object.
 */
actual object SharedStrings {
    private val manager = StringResourceManager()
    
    actual fun getString(key: String): String {
        return manager.getString(key)
    }
    
    actual fun getFormattedString(key: String, vararg args: Any): String {
        return manager.getFormattedString(key, *args)
    }
    
    // Common string accessors
    actual val appName: String get() = getString(StringKeys.APP_NAME)
    actual val buttonClickMe: String get() = getString(StringKeys.BUTTON_CLICK_ME)
    actual val welcomeTitle: String get() = getString(StringKeys.WELCOME_TITLE)
    actual val welcomeDescription: String get() = getString(StringKeys.WELCOME_DESCRIPTION)
    actual val actionOk: String get() = getString(StringKeys.ACTION_OK)
    actual val actionCancel: String get() = getString(StringKeys.ACTION_CANCEL)
    actual val actionRetry: String get() = getString(StringKeys.ACTION_RETRY)
    actual val errorNetwork: String get() = getString(StringKeys.ERROR_NETWORK)
    actual val errorGeneric: String get() = getString(StringKeys.ERROR_GENERIC)
    actual val nativeDemoTitle: String get() = getString("native_demo_title")
    actual val nativeDemoDescription: String get() = getString("native_demo_description")
}

/**
 * Helper functions for creating StringResourceManager instances
 */
fun createStringResourceManager(): StringResourceManager {
    return StringResourceManager()
}