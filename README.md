# Shared String Resources for Kotlin Multiplatform (XML/STORYBOARD)

A demonstration project showcasing **shared string resources between Android and iOS** in Kotlin Multiplatform projects, with support for native Android Activities and iOS applications without Compose dependencies.

## 🎯 Project Purpose

This project demonstrates how to:
- ✅ **Centralize string resources** in one location for both Android and iOS
- ✅ **Support native development workflows** (Android Activities, iOS Swift/UIKit)
- ✅ **Provide localization support** with RTL languages (English and Arabic included)
- ✅ **Maintain type safety** with compile-time string validation
- ✅ **Work without Compose** - pure native Android and iOS implementations

## 🏗️ Architecture Overview

```
Shared String Resources (Pure KMP)
├── JSON Resources (Android)
│   ├── androidMain/assets/strings_en.json (English)
│   └── androidMain/assets/strings_ar.json (Arabic)
├── Common Interface
│   ├── StringResourceManager.kt (expect class + StringKeys)
├── Platform Implementations
│   ├── StringResourceManager.android.kt (loads JSON files)
│   └── StringResourceManager.ios.kt (hardcoded strings)
└── Demo Applications
    ├── Android Activities (MainActivity, NativeXmlActivity, NativeAndroidActivity)
    └── iOS App (SwiftUI + UIKit demos)
```

## 🚀 Quick Start

### Prerequisites
- Kotlin Multiplatform setup
- Android Studio / IntelliJ IDEA
- Xcode (for iOS development)

### 1. Clone and Setup
```bash
git clone <repository-url>
cd demo
```

### 2. Run Android Demo
```bash
./gradlew :composeApp:installDebug
```

### 3. Run iOS Demo
```bash
cd iosApp
xcodebuild -project iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 15' build
```

## 📱 Demo Applications

### Android App
- **MainActivity**: Entry point with navigation to native demo
- **NativeXmlActivity**: Comprehensive demonstration of shared strings in native Android Views
- **NativeAndroidActivity**: Alternative native Android implementation
- **Features**: AlertDialogs, Toasts, dynamic content, counter updates, language info

### iOS App
- **SwiftUI Views**: Native SwiftUI implementation using shared strings
- **UIKit Components**: Native iOS components with shared string resources
- **Features**: Alerts, dynamic content, RTL support, localization

## 💡 Usage Examples

### Basic Usage (Pure KMP)

```kotlin
// Initialize string manager
val stringManager = StringResourceManager(context) // Android
val stringManager = StringResourceManager() // iOS

// Get strings using StringKeys constants
val appName = stringManager.getString(StringKeys.APP_NAME)
val welcomeMsg = stringManager.getString(StringKeys.WELCOME_TITLE)

// Formatted strings
val counter = stringManager.getFormattedString(StringKeys.COUNTER_LABEL, 42)

// Localization info
val isRTL = stringManager.isRTL()
val currentLang = stringManager.getCurrentLanguage()
```

### Android Activities

```kotlin
class MainActivity : Activity() {
    private lateinit var stringManager: StringResourceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        stringManager = StringResourceManager(this)
        
        // Set title using shared strings
        title = stringManager.getString(StringKeys.APP_NAME)
        
        // Create UI with shared strings
        val titleText = stringManager.getString(StringKeys.WELCOME_TITLE)
        val buttonText = stringManager.getString(StringKeys.BUTTON_CLICK_ME)
    }
}
```

### iOS Swift Integration

```swift
import SharedApp

class ViewController: UIViewController {
    private let stringManager = StringResourceManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set text using shared strings
        titleLabel.text = stringManager.getString(key: StringKeys.shared.WELCOME_TITLE)
        actionButton.setTitle(stringManager.getString(key: StringKeys.shared.BUTTON_CLICK_ME), for: .normal)
        
        // Set navigation title
        title = stringManager.getString(key: StringKeys.shared.APP_NAME)
    }
}
```

### SwiftUI Integration

```swift
struct ContentView: View {
    var body: some View {
        VStack {
            Text(SharedStrings.shared.welcomeTitle)
                .font(.largeTitle)
            
            Button(SharedStrings.shared.buttonClickMe) {
                // Action
            }
        }
        .navigationTitle(SharedStrings.shared.appName)
    }
}
```

## 🌍 Localization Support

### Supported Languages
- **English** (default): `strings_en.json`
- **Arabic** (RTL): `strings_ar.json`

### String Resources Structure

The project includes the following string categories:

```kotlin
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
    
    // Demo-specific strings
    const val NATIVE_DEMO_TITLE = "native_demo_title"
    const val COUNTER_LABEL = "counter_label" // Supports formatting: "Counter: %d"
    const val CURRENT_LANGUAGE = "current_language" // Supports formatting: "Current Language: %s"
}
```

### RTL Support
- Automatic RTL detection for Arabic and other RTL languages
- Layout direction handled automatically on both platforms
- Text alignment and UI mirroring supported

## 🏗️ Project Structure

```
demo/
├── README.md                                    # This file
├── build.gradle.kts                            # Root build configuration
├── settings.gradle.kts                         # Project settings
├── gradle/                                     # Gradle wrapper and dependencies
│   └── libs.versions.toml                     # Version catalog
├── composeApp/                                 # Main KMP module
│   ├── build.gradle.kts                       # Module build configuration
│   ├── src/
│   │   ├── commonMain/kotlin/io/yavero/demo/
│   │   │   └── StringResourceManager.kt       # Common interface + StringKeys
│   │   ├── androidMain/
│   │   │   ├── assets/
│   │   │   │   ├── strings_en.json           # English strings
│   │   │   │   └── strings_ar.json           # Arabic strings
│   │   │   └── kotlin/io/yavero/demo/
│   │   │       ├── StringResourceManager.android.kt  # Android implementation
│   │   │       ├── MainActivity.kt                   # Main Android activity
│   │   │       ├── NativeXmlActivity.kt              # Native XML demo
│   │   │       ├── NativeAndroidActivity.kt          # Alternative native demo
│   │   │       └── AndroidStringResourceBridge.kt    # Android utilities
│   │   └── iosMain/kotlin/io/yavero/demo/
│   │       └── StringResourceManager.ios.kt   # iOS implementation (hardcoded strings)
└── iosApp/                                     # iOS application
    ├── iosApp.xcodeproj/                      # Xcode project
    └── iosApp/
        ├── ContentView.swift                  # Main iOS SwiftUI view
        ├── NativeSwiftView.swift              # Native SwiftUI demo
        ├── StringResourcesHelper.swift        # Swift helper utilities
        ├── IOSStringResourceBridge.swift      # iOS utilities
        └── NativeStoryboardViewController.swift # Native UIKit demo
```

## ⚙️ Build Configuration

### Kotlin Multiplatform Setup
```kotlin
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
        }
        commonMain.dependencies {
            // Pure KMP dependencies only - no Compose
        }
    }
}
```

### Key Features
- **No Compose Dependencies**: Pure native Android and iOS implementations
- **Static iOS Framework**: Named "SharedApp" for iOS integration
- **Minimal Dependencies**: Only essential Android libraries

## 🧪 Testing

### Run Android App
```bash
./gradlew :composeApp:installDebug
# Launch the app on your Android device/emulator
```

### Run iOS App
```bash
cd iosApp
xcodebuild -project iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 15' build
```

## 🎯 Implementation Details

### Android Implementation
- **JSON Loading**: Loads strings from `assets/strings_*.json` files
- **Caching**: Implements string caching for performance
- **Fallback**: Falls back to English if language-specific file fails
- **RTL Detection**: Uses Android's built-in RTL detection

### iOS Implementation
- **Hardcoded Strings**: Currently uses hardcoded string maps (noted in code comments)
- **Language Detection**: Uses NSLocale for language detection
- **RTL Support**: Manual RTL detection for common RTL languages
- **Simple Formatting**: Basic string formatting support

### Shared Interface
- **StringResourceManager**: Expect/actual class for platform-specific implementations
- **StringKeys**: Object with string key constants for type safety
- **SharedStrings**: Convenience object for easy access to common strings

## 🚨 Known Limitations

1. **iOS JSON Loading**: iOS implementation currently uses hardcoded strings instead of loading JSON files
2. **String Formatting**: iOS string formatting is simplified compared to Android
3. **Language Addition**: Adding new languages requires updating both JSON files and iOS hardcoded strings

## 📄 License

This project is provided as a demonstration and educational resource. Feel free to use and adapt for your own projects.

## 🔗 Related Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Android String Resources](https://developer.android.com/guide/topics/resources/string-resource)
- [iOS Localization Guide](https://developer.apple.com/documentation/xcode/localization)

---

**This project demonstrates a practical approach to sharing string resources between Android and iOS** using Kotlin Multiplatform, providing a centralized and maintainable solution that works with native platform development without requiring Compose Multiplatform.
