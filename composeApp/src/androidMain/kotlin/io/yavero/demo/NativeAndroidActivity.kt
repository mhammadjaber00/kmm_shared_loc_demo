package io.yavero.demo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import java.util.Locale

/**
 * Native Android Activity that demonstrates using shared string resources
 * in pure native Android applications.
 */
class NativeAndroidActivity : Activity() {
    
    private lateinit var sharedStrings: StringResourceManager
    private var counter = 0
    private lateinit var counterTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize shared string resources
        sharedStrings = StringResourceManager(this)
        
        // Set up the UI
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
    }
    
    private fun setupUI() {
        // Create main layout
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }
        
        // Title
        val titleTextView = TextView(this).apply {
            text = sharedStrings.getString(StringKeys.WELCOME_TITLE)
            textSize = 24f
            setPadding(0, 0, 0, 16)
        }
        mainLayout.addView(titleTextView)
        
        // Description
        val descriptionTextView = TextView(this).apply {
            text = sharedStrings.getString(StringKeys.WELCOME_DESCRIPTION)
            textSize = 16f
            setPadding(0, 0, 0, 24)
        }
        mainLayout.addView(descriptionTextView)
        
        // Sample texts section
        val sampleHeaderTextView = TextView(this).apply {
            text = "Sample Texts:"
            textSize = 18f
            setPadding(0, 0, 0, 8)
        }
        mainLayout.addView(sampleHeaderTextView)
        
        val sampleText1 = TextView(this).apply {
            text = "• ${sharedStrings.getString(StringKeys.SAMPLE_TEXT_1)}"
            textSize = 14f
            setPadding(16, 0, 0, 4)
        }
        mainLayout.addView(sampleText1)
        
        val sampleText2 = TextView(this).apply {
            text = "• ${sharedStrings.getString(StringKeys.SAMPLE_TEXT_2)}"
            textSize = 14f
            setPadding(16, 0, 0, 4)
        }
        mainLayout.addView(sampleText2)
        
        val sampleText3 = TextView(this).apply {
            text = "• ${sharedStrings.getString(StringKeys.SAMPLE_TEXT_3)}"
            textSize = 14f
            setPadding(16, 0, 0, 24)
        }
        mainLayout.addView(sampleText3)
        
        // Counter section
        counterTextView = TextView(this).apply {
            text = "Counter: $counter"
            textSize = 20f
            setPadding(0, 0, 0, 16)
        }
        mainLayout.addView(counterTextView)
        
        // Buttons layout
        val buttonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 24)
        }
        
        // Click me button
        val clickMeButton = Button(this).apply {
            text = sharedStrings.getString(StringKeys.BUTTON_CLICK_ME)
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
            text = sharedStrings.getString(StringKeys.ACTION_RETRY)
            setOnClickListener {
                counter = 0
                updateCounter()
            }
        }
        buttonsLayout.addView(retryButton)
        
        mainLayout.addView(buttonsLayout)
        
        // Show alert button
        val showAlertButton = Button(this).apply {
            text = "Show Alert"
            setOnClickListener {
                showAlert()
            }
        }
        mainLayout.addView(showAlertButton)
        
        // Show toast button
        val showToastButton = Button(this).apply {
            text = "Show Toast"
            setOnClickListener {
                showToast()
            }
        }
        mainLayout.addView(showToastButton)
        
        // Language info section
        val languageInfoLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 32, 0, 0)
        }
        
        val languageHeaderTextView = TextView(this).apply {
            text = "Language Info:"
            textSize = 14f
            setPadding(0, 0, 0, 8)
        }
        languageInfoLayout.addView(languageHeaderTextView)
        
        val currentLanguageTextView = TextView(this).apply {
            text = "Current Language: ${sharedStrings.getCurrentLanguage()}"
            textSize = 12f
            setPadding(0, 0, 0, 4)
        }
        languageInfoLayout.addView(currentLanguageTextView)
        
        val isRTLTextView = TextView(this).apply {
            text = "Is RTL: ${if (sharedStrings.isRTL()) "Yes" else "No"}"
            textSize = 12f
            setPadding(0, 0, 0, 4)
        }
        languageInfoLayout.addView(isRTLTextView)
        
        val localeTextView = TextView(this).apply {
            text = "System Locale: ${Locale.getDefault().displayName}"
            textSize = 12f
        }
        languageInfoLayout.addView(localeTextView)
        
        mainLayout.addView(languageInfoLayout)
        
        // Set the content view
        setContentView(mainLayout)
        
        // Set the title
        title = sharedStrings.getString(StringKeys.APP_NAME)
    }
    
    private fun updateCounter() {
        counterTextView.text = "Counter: $counter"
    }
    
    private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(sharedStrings.getString(StringKeys.ERROR_GENERIC))
            .setPositiveButton(sharedStrings.getString(StringKeys.ACTION_OK)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(sharedStrings.getString(StringKeys.ACTION_CANCEL)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showToast() {
        Toast.makeText(
            this,
            sharedStrings.getString(StringKeys.ERROR_NETWORK),
            Toast.LENGTH_SHORT
        ).show()
    }
}