package io.yavero.demo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Native Android Activity that demonstrates using shared string resources
 * in traditional XML layouts and Android Views (without Compose).
 */
class NativeXmlActivity : Activity() {
    
    private lateinit var stringManager: StringResourceManager
    private lateinit var stringBridge: AndroidStringResourceBridge
    private var counter = 0
    private lateinit var counterTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize shared string resources
        stringManager = StringResourceManager(this)
        stringBridge = AndroidStringResourceBridge(this)
        
        // Set up the UI programmatically (demonstrating XML-free approach)
        setupUI()
        
        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
        
        // Set the activity title using shared strings
        title = stringManager.getString(StringKeys.APP_NAME)
    }
    
    private fun setupUI() {
        // Create main scroll view
        val scrollView = ScrollView(this)
        
        // Create main layout
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }
        
        // Title section
        val titleTextView = TextView(this).apply {
            text = stringManager.getString("native_demo_title")
            textSize = 24f
            setPadding(0, 0, 0, 16)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        mainLayout.addView(titleTextView)
        
        // Description
        val descriptionTextView = TextView(this).apply {
            text = stringManager.getString("native_demo_description")
            textSize = 16f
            setPadding(0, 0, 0, 24)
        }
        mainLayout.addView(descriptionTextView)
        
        // Sample texts section
        addSampleTextsSection(mainLayout)
        
        // Interactive section
        addInteractiveSection(mainLayout)
        
        // String generation demo section
        addStringGenerationSection(mainLayout)
        
        // Language info section
        addLanguageInfoSection(mainLayout)
        
        scrollView.addView(mainLayout)
        setContentView(scrollView)
    }
    
    private fun addSampleTextsSection(parent: LinearLayout) {
        val sampleHeaderTextView = TextView(this).apply {
            text = "Sample Shared Strings:"
            textSize = 18f
            setPadding(0, 0, 0, 8)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        parent.addView(sampleHeaderTextView)
        
        val sampleText1 = TextView(this).apply {
            text = "• ${stringManager.getString(StringKeys.SAMPLE_TEXT_1)}"
            textSize = 14f
            setPadding(16, 0, 0, 4)
        }
        parent.addView(sampleText1)
        
        val sampleText2 = TextView(this).apply {
            text = "• ${stringManager.getString(StringKeys.SAMPLE_TEXT_2)}"
            textSize = 14f
            setPadding(16, 0, 0, 4)
        }
        parent.addView(sampleText2)
        
        val sampleText3 = TextView(this).apply {
            text = "• ${stringManager.getString(StringKeys.SAMPLE_TEXT_3)}"
            textSize = 14f
            setPadding(16, 0, 0, 24)
        }
        parent.addView(sampleText3)
    }
    
    private fun addInteractiveSection(parent: LinearLayout) {
        val interactiveHeaderTextView = TextView(this).apply {
            text = "Interactive Demo:"
            textSize = 18f
            setPadding(0, 0, 0, 8)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        parent.addView(interactiveHeaderTextView)
        
        // Counter section
        counterTextView = TextView(this).apply {
            text = stringManager.getFormattedString("counter_label", counter)
            textSize = 20f
            setPadding(0, 0, 0, 16)
        }
        parent.addView(counterTextView)
        
        // Buttons layout
        val buttonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 16)
        }
        
        // Click me button using AndroidXmlIntegration utility
        val clickMeButton = Button(this).apply {
            AndroidXmlIntegration.setButtonTextFromSharedString(this, this@NativeXmlActivity, StringKeys.BUTTON_CLICK_ME)
            setOnClickListener {
                counter++
                updateCounter()
            }
        }
        buttonsLayout.addView(clickMeButton)
        
        // Add some spacing
        val spacer = TextView(this).apply {
            text = "  "
        }
        buttonsLayout.addView(spacer)
        
        // Retry button
        val retryButton = Button(this).apply {
            AndroidXmlIntegration.setButtonTextFromSharedString(this, this@NativeXmlActivity, StringKeys.ACTION_RETRY)
            setOnClickListener {
                counter = 0
                updateCounter()
            }
        }
        buttonsLayout.addView(retryButton)
        
        parent.addView(buttonsLayout)
        
        // More action buttons
        val showAlertButton = Button(this).apply {
            text = "Show Alert Dialog"
            setOnClickListener { showAlert() }
        }
        parent.addView(showAlertButton)
        
        val showToastButton = Button(this).apply {
            text = "Show Toast"
            setOnClickListener { showToast() }
        }
        parent.addView(showToastButton)
        
        val generateXmlButton = Button(this).apply {
            text = "Generate strings.xml"
            setOnClickListener { generateAndShowXml() }
            setPadding(0, 0, 0, 24)
        }
        parent.addView(generateXmlButton)
    }
    
    private fun addStringGenerationSection(parent: LinearLayout) {
        val generationHeaderTextView = TextView(this).apply {
            text = "String Resource Generation:"
            textSize = 18f
            setPadding(0, 0, 0, 8)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        parent.addView(generationHeaderTextView)
        
        val generationDescTextView = TextView(this).apply {
            text = "This demo shows how shared JSON resources can be converted to traditional Android strings.xml format for use in XML layouts."
            textSize = 14f
            setPadding(0, 0, 0, 16)
        }
        parent.addView(generationDescTextView)
        
        // Show available string keys
        val keysTextView = TextView(this).apply {
            val keys = stringBridge.getAllStringKeys()
            text = "Available string keys (${keys.size}): ${keys.joinToString(", ")}"
            textSize = 12f
            setPadding(0, 0, 0, 24)
        }
        parent.addView(keysTextView)
    }
    
    private fun addLanguageInfoSection(parent: LinearLayout) {
        val languageHeaderTextView = TextView(this).apply {
            AndroidXmlIntegration.setTextFromSharedString(this, this@NativeXmlActivity, "language_info")
            textSize = 14f
            setPadding(0, 0, 0, 8)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        parent.addView(languageHeaderTextView)
        
        val currentLanguageTextView = TextView(this).apply {
            text = stringManager.getFormattedString("current_language", stringManager.getCurrentLanguage())
            textSize = 12f
            setPadding(0, 0, 0, 4)
        }
        parent.addView(currentLanguageTextView)
        
        val isRTLTextView = TextView(this).apply {
            text = stringManager.getFormattedString("is_rtl", if (stringManager.isRTL()) "Yes" else "No")
            textSize = 12f
            setPadding(0, 0, 0, 4)
        }
        parent.addView(isRTLTextView)
        
        val localeTextView = TextView(this).apply {
            text = "System Locale: ${java.util.Locale.getDefault().displayName}"
            textSize = 12f
        }
        parent.addView(localeTextView)
    }
    
    private fun updateCounter() {
        counterTextView.text = stringManager.getFormattedString("counter_label", counter)
    }
    
    private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("Alert Demo")
            .setMessage(stringManager.getString(StringKeys.ERROR_GENERIC))
            .setPositiveButton(stringManager.getString(StringKeys.ACTION_OK)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(stringManager.getString(StringKeys.ACTION_CANCEL)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showToast() {
        Toast.makeText(
            this,
            stringManager.getString(StringKeys.ERROR_NETWORK),
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun generateAndShowXml() {
        val englishXml = stringBridge.generateAndroidStringsXml("en")
        val arabicXml = stringBridge.generateAndroidStringsXml("ar")
        
        AlertDialog.Builder(this)
            .setTitle("Generated strings.xml")
            .setMessage("English XML:\n\n$englishXml\n\n---\n\nArabic XML:\n\n$arabicXml")
            .setPositiveButton(stringManager.getString(StringKeys.ACTION_OK)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}