# Native XML and Storyboard Integration Guide

This guide demonstrates how to use shared string resources in native Android XML layouts and iOS Storyboards without any Compose Multiplatform dependencies.

## Overview

This implementation provides:
- ✅ Pure Kotlin Multiplatform shared string resources
- ✅ Native Android XML layout integration
- ✅ Native iOS Storyboard integration
- ✅ JSON-based localization (English and Arabic)
- ✅ Type-safe string access
- ✅ Easy migration from existing native resources

## Architecture

```
Shared String Resources (Pure KMP)
├── JSON Resources
│   ├── androidMain/assets/strings_en.json (English)
│   └── androidMain/assets/strings_ar.json (Arabic)
├── Common Interface
│   ├── StringResourceManager.kt (expect class)
│   └── StringKeys.kt (string constants)
└── Platform Implementations
    ├── StringResourceManager.android.kt (actual class)
    └── StringResourceManager.ios.kt (actual class)
```

## Android XML Layout Integration

### 1. Basic Setup

Initialize the string resource manager in your Activity:

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var stringManager: StringResourceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize string manager
        stringManager = StringResourceManager(this)
        
        // Set activity title using shared strings
        title = stringManager.getString(StringKeys.APP_NAME)
    }
}
```

### 2. Using Shared Strings in XML Layouts

#### Method 1: Programmatic Assignment

```kotlin
// In your Activity or Fragment
val titleTextView = findViewById<TextView>(R.id.title)
titleTextView.text = stringManager.getString(StringKeys.WELCOME_TITLE)

val button = findViewById<Button>(R.id.action_button)
button.text = stringManager.getString(StringKeys.BUTTON_CLICK_ME)
```

#### Method 2: Using AndroidXmlIntegration Utility

```kotlin
// Set text using utility functions
AndroidXmlIntegration.setTextFromSharedString(titleTextView, this, StringKeys.WELCOME_TITLE)
AndroidXmlIntegration.setButtonTextFromSharedString(button, this, StringKeys.ACTION_OK)
```

#### Method 3: Extension Functions

```kotlin
// Using context extension functions
titleTextView.text = this.getSharedString(StringKeys.WELCOME_TITLE)
button.text = this.getSharedFormattedString(StringKeys.COUNTER_LABEL, counter)
```

### 3. Complete Activity Example

```kotlin
class NativeXmlActivity : AppCompatActivity() {
    private lateinit var stringManager: StringResourceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)
        
        stringManager = StringResourceManager(this)
        setupUI()
    }
    
    private fun setupUI() {
        // Set title
        title = stringManager.getString(StringKeys.APP_NAME)
        
        // Configure UI elements
        val titleTextView = findViewById<TextView>(R.id.title)
        titleTextView.text = stringManager.getString(StringKeys.WELCOME_TITLE)
        
        val descriptionTextView = findViewById<TextView>(R.id.description)
        descriptionTextView.text = stringManager.getString(StringKeys.WELCOME_DESCRIPTION)
        
        val actionButton = findViewById<Button>(R.id.action_button)
        actionButton.text = stringManager.getString(StringKeys.BUTTON_CLICK_ME)
        actionButton.setOnClickListener {
            showAlert()
        }
    }
    
    private fun showAlert() {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(stringManager.getString(StringKeys.ERROR_GENERIC))
            .setPositiveButton(stringManager.getString(StringKeys.ACTION_OK)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(stringManager.getString(StringKeys.ACTION_CANCEL)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
```

### 4. XML Layout File Example

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="16dp" />

    <TextView
        android:id="@+id/description"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:layout_marginBottom="24dp" />

    <Button
        android:id="@+id/action_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center" />

</LinearLayout>
```

## iOS Storyboard Integration

### 1. Basic Setup

Use the StringResourceManager in your iOS ViewController:

```swift
import UIKit
import SharedApp

class NativeStoryboardViewController: UIViewController {
    private let stringManager = StringResourceManager()
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        // Set navigation title
        title = stringManager.getString(key: StringKeysKt.APP_NAME)
        
        // Configure UI elements
        titleLabel.text = stringManager.getString(key: StringKeysKt.WELCOME_TITLE)
        descriptionLabel.text = stringManager.getString(key: StringKeysKt.WELCOME_DESCRIPTION)
        actionButton.setTitle(stringManager.getString(key: StringKeysKt.BUTTON_CLICK_ME), for: .normal)
    }
}
```

### 2. Using IOSStoryboardIntegration Utility

```swift
class MyViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set navigation title using utility
        IOSStoryboardIntegration.setNavigationTitleFromSharedString(self, key: "app_name")
        
        // Set label text using utility
        IOSStoryboardIntegration.setLabelTextFromSharedString(titleLabel, key: "welcome_title")
        
        // Set button title using utility
        IOSStoryboardIntegration.setButtonTitleFromSharedString(actionButton, key: "button_click_me")
    }
    
    @IBAction func showAlertTapped(_ sender: UIButton) {
        IOSStoryboardIntegration.showAlertWithSharedStrings(
            self,
            titleKey: "error_generic",
            messageKey: "error_network"
        )
    }
}
```

### 3. Storyboard Configuration

1. **Create your Storyboard** with UILabels, UIButtons, etc.
2. **Connect IBOutlets** to your ViewController
3. **Set text programmatically** in `viewDidLoad` using shared strings
4. **Connect IBActions** for button interactions

### 4. Complete Storyboard Example

```swift
import UIKit
import SharedApp

class NativeStoryboardViewController: UIViewController {
    
    private let stringManager = StringResourceManager()
    private let stringBridge = IOSStringResourceBridge()
    private var counter = 0
    
    // Storyboard outlets
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var counterLabel: UILabel!
    @IBOutlet weak var clickMeButton: UIButton!
    @IBOutlet weak var retryButton: UIButton!
    @IBOutlet weak var sampleText1Label: UILabel!
    @IBOutlet weak var sampleText2Label: UILabel!
    @IBOutlet weak var sampleText3Label: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        updateCounter()
    }
    
    private func setupUI() {
        // Set navigation title
        title = stringManager.getString(key: "app_name")
        
        // Configure labels
        titleLabel.text = stringManager.getString(key: "welcome_title")
        descriptionLabel.text = stringManager.getString(key: "welcome_description")
        
        // Configure buttons
        clickMeButton.setTitle(stringManager.getString(key: "button_click_me"), for: .normal)
        retryButton.setTitle(stringManager.getString(key: "action_retry"), for: .normal)
        
        // Configure sample texts
        sampleText1Label.text = "• " + stringManager.getString(key: "sample_text_1")
        sampleText2Label.text = "• " + stringManager.getString(key: "sample_text_2")
        sampleText3Label.text = "• " + stringManager.getString(key: "sample_text_3")
    }
    
    @IBAction func clickMeButtonTapped(_ sender: UIButton) {
        counter += 1
        updateCounter()
    }
    
    @IBAction func retryButtonTapped(_ sender: UIButton) {
        counter = 0
        updateCounter()
    }
    
    @IBAction func showAlertButtonTapped(_ sender: UIButton) {
        let alert = UIAlertController(
            title: "Alert",
            message: stringManager.getString(key: "error_generic"),
            preferredStyle: .alert
        )
        
        alert.addAction(UIAlertAction(
            title: stringManager.getString(key: "action_ok"),
            style: .default
        ))
        
        alert.addAction(UIAlertAction(
            title: stringManager.getString(key: "action_cancel"),
            style: .cancel
        ))
        
        present(alert, animated: true)
    }
    
    private func updateCounter() {
        counterLabel.text = stringManager.getFormattedString(key: "counter_label", args: [counter])
    }
}
```

## Localization Support

### 1. JSON Resource Files

**English** (`androidMain/assets/strings_en.json`):
```json
{
  "app_name": "demo",
  "welcome_title": "Welcome to KMP Demo",
  "button_click_me": "Click me!",
  "action_ok": "OK",
  "action_cancel": "Cancel",
  "counter_label": "Counter: %d"
}
```

**Arabic** (`androidMain/assets/strings_ar.json`):
```json
{
  "app_name": "تطبيق تجريبي",
  "welcome_title": "مرحباً بك في تطبيق KMP التجريبي",
  "button_click_me": "اضغط هنا!",
  "action_ok": "موافق",
  "action_cancel": "إلغاء",
  "counter_label": "العداد: %d"
}
```

### 2. RTL Support

Both platforms automatically handle RTL layout:

**Android:**
```kotlin
val isRTL = stringManager.isRTL()
val currentLanguage = stringManager.getCurrentLanguage()

// Android automatically handles RTL when android:supportsRtl="true" in manifest
```

**iOS:**
```swift
let isRTL = stringManager.isRTL()
let currentLanguage = stringManager.getCurrentLanguage()

// Apply RTL layout if needed
if isRTL {
    view.semanticContentAttribute = .forceRightToLeft
}
```

## Migration Strategies

### From Android strings.xml

1. **Convert XML to JSON:**
   ```xml
   <!-- Before: strings.xml -->
   <string name="welcome_title">Welcome</string>
   
   <!-- After: strings_en.json -->
   "welcome_title": "Welcome"
   ```

2. **Update code:**
   ```kotlin
   // Before
   getString(R.string.welcome_title)
   
   // After
   stringManager.getString(StringKeys.WELCOME_TITLE)
   ```

### From iOS Localizable.strings

1. **Convert format:**
   ```
   // Before: Localizable.strings
   "welcome_title" = "Welcome";
   
   // After: strings_en.json
   "welcome_title": "Welcome"
   ```

2. **Update code:**
   ```swift
   // Before
   NSLocalizedString("welcome_title", comment: "")
   
   // After
   stringManager.getString(key: "welcome_title")
   ```

## Best Practices

### 1. Initialization
- Initialize StringResourceManager in Activity/ViewController lifecycle methods
- Cache the instance to avoid repeated JSON parsing
- Use SharedStrings object for global access (Android only, requires initialization)

### 2. Performance
- String resources are cached after first load
- JSON parsing happens once per language
- Use formatted strings for dynamic content

### 3. Error Handling
- Missing strings return "String not found: key" fallback
- Invalid formatting returns "Error formatting string: key"
- Always test with different locales

### 4. Testing
- Test on both platforms with different device languages
- Verify RTL layout for Arabic
- Test string formatting with various parameters

## Demo Applications

### Android Demo
Run the Android app and tap "Launch Native XML Activity" to see:
- Native Android Views using shared strings
- AlertDialogs with localized text
- Toast messages with shared resources
- String generation utilities

### iOS Demo
The iOS app includes a complete Storyboard-based demo showing:
- Native UIKit components with shared strings
- UIAlertController with localized buttons
- Dynamic string formatting
- RTL layout support

## Conclusion

This implementation provides a complete solution for using shared string resources in native Android XML layouts and iOS Storyboards without any Compose dependencies. The approach offers:

- **Pure KMP**: No Compose Multiplatform dependencies
- **Native Integration**: Works seamlessly with existing XML/Storyboard workflows
- **Localization Ready**: Built-in support for multiple languages and RTL
- **Easy Migration**: Clear path from existing native string resources
- **Type Safety**: Compile-time checking with StringKeys constants
- **Performance**: Efficient JSON-based resource loading

The demo applications show practical examples of integrating shared strings into real-world native applications.