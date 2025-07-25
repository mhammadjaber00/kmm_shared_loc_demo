package io.yavero.demo

import android.content.Context
import android.content.res.Configuration
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import org.json.JSONObject
import java.util.Locale

/**
 * Android implementation of StringResourceManager.
 * Loads JSON string resources and provides access for native Android XML layouts.
 */
actual class StringResourceManager(private val context: Context) {
    
    private var stringCache: Map<String, String>? = null
    
    private fun loadStrings(): Map<String, String> {
        if (stringCache != null) return stringCache!!
        
        val language = getCurrentLanguage()
        val fileName = when (language) {
            "ar" -> "strings_ar.json"
            else -> "strings_en.json"
        }
        
        return try {
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            
            val strings = mutableMapOf<String, String>()
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                strings[key] = jsonObject.getString(key)
            }
            
            stringCache = strings
            strings
        } catch (e: Exception) {
            // Fallback to English if language-specific file fails
            if (fileName != "strings_en.json") {
                try {
                    val inputStream = context.assets.open("strings_en.json")
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(jsonString)
                    
                    val strings = mutableMapOf<String, String>()
                    val keys = jsonObject.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        strings[key] = jsonObject.getString(key)
                    }
                    
                    stringCache = strings
                    strings
                } catch (fallbackException: Exception) {
                    emptyMap()
                }
            } else {
                emptyMap()
            }
        }
    }
    
    actual fun getString(key: String): String {
        val strings = loadStrings()
        return strings[key] ?: "String not found: $key"
    }
    
    actual fun getFormattedString(key: String, vararg args: Any): String {
        val template = getString(key)
        return try {
            String.format(template, *args)
        } catch (e: Exception) {
            "Error formatting string: $key"
        }
    }
    
    actual fun isRTL(): Boolean {
        val locale = getCurrentLocale()
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == ViewCompat.LAYOUT_DIRECTION_RTL
    }
    
    actual fun getCurrentLanguage(): String {
        return getCurrentLocale().language
    }
    
    actual fun getAllKeys(): Set<String> {
        return loadStrings().keys
    }
    
    private fun getCurrentLocale(): Locale {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }
}

/**
 * Android implementation of SharedStrings object.
 */
actual object SharedStrings {
    private var manager: StringResourceManager? = null
    
    fun initialize(context: Context) {
        manager = StringResourceManager(context.applicationContext)
    }
    
    private fun getManager(): StringResourceManager {
        return manager ?: throw IllegalStateException(
            "SharedStrings not initialized. Call SharedStrings.initialize(context) first."
        )
    }
    
    actual fun getString(key: String): String {
        return getManager().getString(key)
    }
    
    actual fun getFormattedString(key: String, vararg args: Any): String {
        return getManager().getFormattedString(key, *args)
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
fun createStringResourceManager(context: Context): StringResourceManager {
    return StringResourceManager(context)
}