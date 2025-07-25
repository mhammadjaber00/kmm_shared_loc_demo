package io.yavero.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create UI programmatically to demonstrate native Android approach
        setupNativeUI()
    }
    
    private fun setupNativeUI() {
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }
        
        val titleTextView = TextView(this).apply {
            text = "Native Android & iOS String Resources Demo"
            textSize = 20f
            setPadding(0, 0, 0, 24)
        }
        mainLayout.addView(titleTextView)
        
        val descriptionTextView = TextView(this).apply {
            text = "This demo shows how to use shared string resources in native Android XML layouts and iOS Storyboards without Compose Multiplatform."
            textSize = 16f
            setPadding(0, 0, 0, 32)
        }
        mainLayout.addView(descriptionTextView)
        
        val nativeXmlButton = Button(this).apply {
            text = "Launch Native XML Activity"
            setOnClickListener {
                val intent = Intent(this@MainActivity, NativeXmlActivity::class.java)
                startActivity(intent)
            }
        }
        mainLayout.addView(nativeXmlButton)
        
        setContentView(mainLayout)
        
        // Set title using shared strings
        val stringManager = StringResourceManager(this)
        title = stringManager.getString(StringKeys.APP_NAME)
    }
}