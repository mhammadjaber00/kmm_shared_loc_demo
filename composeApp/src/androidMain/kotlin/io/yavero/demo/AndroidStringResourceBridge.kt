package io.yavero.demo

import android.content.Context
import org.json.JSONObject
import java.io.File

/**
 * Android-specific bridge that generates traditional Android strings.xml files
 * from shared JSON resources. This allows using shared strings in XML layouts.
 */
class AndroidStringResourceBridge(private val context: Context) {
    
    /**
     * Generate Android strings.xml file from JSON resources.
     * This creates the traditional Android resource files that can be used in XML layouts.
     */
    fun generateAndroidStringsXml(language: String = "en"): String {
        val fileName = when (language) {
            "ar" -> "strings_ar.json"
            else -> "strings_en.json"
        }
        
        return try {
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            
            val xmlBuilder = StringBuilder()
            xmlBuilder.appendLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
            xmlBuilder.appendLine("<resources>")
            
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject.getString(key)
                // Escape XML special characters
                val escapedValue = value
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "\\'")
                
                xmlBuilder.appendLine("    <string name=\"$key\">$escapedValue</string>")
            }
            
            xmlBuilder.appendLine("</resources>")
            xmlBuilder.toString()
        } catch (e: Exception) {
            "<!-- Error generating strings.xml: ${e.message} -->"
        }
    }
    
    /**
     * Save generated strings.xml to the app's internal storage for demonstration.
     * In a real app, this would be part of the build process.
     */
    fun saveGeneratedStringsXml(language: String = "en"): String {
        val xml = generateAndroidStringsXml(language)
        val fileName = if (language == "ar") "strings_ar.xml" else "strings.xml"
        
        return try {
            val file = File(context.filesDir, fileName)
            file.writeText(xml)
            "Generated strings.xml saved to: ${file.absolutePath}"
        } catch (e: Exception) {
            "Error saving strings.xml: ${e.message}"
        }
    }
    
    /**
     * Get all available string keys from the JSON resources.
     */
    fun getAllStringKeys(): Set<String> {
        return try {
            val inputStream = context.assets.open("strings_en.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            
            val keys = mutableSetOf<String>()
            val jsonKeys = jsonObject.keys()
            while (jsonKeys.hasNext()) {
                keys.add(jsonKeys.next())
            }
            keys
        } catch (e: Exception) {
            emptySet()
        }
    }
    
    /**
     * Demonstrate how to access shared strings in traditional Android code.
     */
    fun demonstrateStringAccess(): Map<String, String> {
        val stringManager = StringResourceManager(context)
        val demo = mutableMapOf<String, String>()
        
        // Show how to access common strings
        demo["App Name"] = stringManager.getString(StringKeys.APP_NAME)
        demo["Welcome Title"] = stringManager.getString(StringKeys.WELCOME_TITLE)
        demo["Button Text"] = stringManager.getString(StringKeys.BUTTON_CLICK_ME)
        demo["OK Action"] = stringManager.getString(StringKeys.ACTION_OK)
        demo["Cancel Action"] = stringManager.getString(StringKeys.ACTION_CANCEL)
        
        // Show formatted strings
        demo["Counter Example"] = stringManager.getFormattedString("counter_label", 42)
        demo["Language Info"] = stringManager.getFormattedString("current_language", stringManager.getCurrentLanguage())
        demo["RTL Status"] = stringManager.getFormattedString("is_rtl", if (stringManager.isRTL()) "Yes" else "No")
        
        return demo
    }
}

/**
 * Utility functions for XML layout integration
 */
object AndroidXmlIntegration {
    
    /**
     * Initialize shared strings for use in XML layouts.
     * Call this in your Application class or MainActivity.
     */
    fun initialize(context: Context) {
        // SharedStrings object is automatically initialized when first accessed
        // This method is provided for consistency with the API
    }
    
    /**
     * Get a string resource for use in XML layouts or traditional Android Views.
     * This can be called from Activities, Fragments, or any Context.
     */
    fun getString(context: Context, key: String): String {
        return StringResourceManager(context).getString(key)
    }
    
    /**
     * Get a formatted string resource for use in XML layouts or traditional Android Views.
     */
    fun getFormattedString(context: Context, key: String, vararg args: Any): String {
        return StringResourceManager(context).getFormattedString(key, *args)
    }
    
    /**
     * Set text on a TextView using shared string resources.
     * Example: AndroidXmlIntegration.setTextFromSharedString(textView, context, StringKeys.WELCOME_TITLE)
     */
    fun setTextFromSharedString(textView: android.widget.TextView, context: Context, key: String) {
        textView.text = getString(context, key)
    }
    
    /**
     * Set button text using shared string resources.
     * Example: AndroidXmlIntegration.setButtonTextFromSharedString(button, context, StringKeys.ACTION_OK)
     */
    fun setButtonTextFromSharedString(button: android.widget.Button, context: Context, key: String) {
        button.text = getString(context, key)
    }
}