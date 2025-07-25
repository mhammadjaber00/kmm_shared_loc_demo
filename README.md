# Shared String Resources for Kotlin Multiplatform

A comprehensive demo project showcasing the **best practices for sharing string resources between Android and iOS** in Kotlin Multiplatform projects, with support for native XML layouts, iOS Storyboards, and easy migration from existing native implementations.

## 🎯 Project Purpose

This project demonstrates how to:
- ✅ **Centralize string resources** in one location for both Android and iOS
- ✅ **Support native development workflows** (Android XML layouts, iOS Storyboards)
- ✅ **Enable easy migration** from existing native string resources
- ✅ **Provide localization support** with RTL languages (English and Arabic included)
- ✅ **Maintain type safety** with compile-time string validation
- ✅ **Scale to large projects** with 1000+ strings

## 🏗️ Architecture Overview

```
Shared String Resources (Pure KMP)
├── JSON Resources (Source of Truth)
│   ├── androidMain/assets/strings_en.json (English)
│   └── androidMain/assets/strings_ar.json (Arabic)
├── Common Interface
│   ├── StringResourceManager.kt (expect class)
│   └── StringKeys.kt (string constants)
├── Platform Implementations
│   ├── StringResourceManager.android.kt (actual class)
│   └── StringResourceManager.ios.kt (actual class)
└── Bridge Classes
    ├── AndroidStringResourceBridge.kt (XML generation)
    └── IOSStringResourceBridge.swift (Localizable.strings generation)
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
- **Main Activity**: Entry point with navigation to native XML demo
- **Native XML Activity**: Complete demonstration of shared strings in native Android Views
- **Features**: AlertDialogs, Toasts, dynamic content, string generation utilities

### iOS App
- **Native SwiftUI Tab**: Pure SwiftUI implementation using shared strings
- **Native Storyboard Demo**: UIKit components with shared string resources
- **Features**: Alerts, dynamic content, RTL support, localization switching

## 💡 Usage Examples

### Basic Usage (Pure KMP)

```kotlin
// Initialize string manager
val stringManager = StringResourceManager(context) // Android
val stringManager = StringResourceManager() // iOS

// Get strings
val appName = stringManager.getString(StringKeys.APP_NAME)
val welcomeMsg = stringManager.getString(StringKeys.WELCOME_TITLE)

// Formatted strings
val counter = stringManager.getFormattedString("counter_label", 42)

// Localization info
val isRTL = stringManager.isRTL()
val currentLang = stringManager.getCurrentLanguage()
```

### Android XML Layouts

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var stringManager: StringResourceManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        stringManager = StringResourceManager(this)
        
        // Set text programmatically
        findViewById<TextView>(R.id.title).text = 
            stringManager.getString(StringKeys.WELCOME_TITLE)
        
        findViewById<Button>(R.id.button).text = 
            stringManager.getString(StringKeys.BUTTON_CLICK_ME)
    }
}
```

### iOS Storyboards

```swift
import SharedApp

class ViewController: UIViewController {
    private let stringManager = StringResourceManager()
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set text programmatically
        titleLabel.text = stringManager.getString(key: "welcome_title")
        actionButton.setTitle(stringManager.getString(key: "button_click_me"), for: .normal)
        
        // Set navigation title
        title = stringManager.getString(key: "app_name")
    }
}
```

### SwiftUI Integration

```swift
struct MyView: View {
    var body: some View {
        VStack {
            Text(SharedStrings.welcomeTitle)
                .font(.largeTitle)
            
            Button(SharedStrings.buttonClickMe) {
                // Action
            }
        }
        .navigationTitle(SharedStrings.appName)
    }
}
```

## 🌍 Localization Support

### Supported Languages
- **English** (default): `strings_en.json`
- **Arabic** (RTL): `strings_ar.json`

### Adding New Languages

1. **Create JSON file:**
```bash
cp composeApp/src/androidMain/assets/strings_en.json \
   composeApp/src/androidMain/assets/strings_es.json
```

2. **Translate strings:**
```json
{
  "app_name": "aplicación de demostración",
  "welcome_title": "Bienvenido a KMP Demo",
  "button_click_me": "¡Haz clic aquí!"
}
```

3. **Update iOS implementation** to include new language in hardcoded strings (temporary solution)

### RTL Support
- Automatic RTL detection for Arabic and other RTL languages
- Layout direction handled automatically on both platforms
- Text alignment and UI mirroring supported

## 🔧 Advanced Features

### Direct XML/Storyboard Integration

For teams preferring traditional native development workflows, the project supports **build-time generation** of native resource files:

#### Android: Generate strings.xml
```kotlin
// Add to build.gradle.kts
tasks.register("generateAndroidStrings") {
    // Converts JSON to traditional strings.xml files
    // Enables @string/resource_name usage in XML layouts
}
```

#### iOS: Generate Localizable.strings
```python
# Python script to generate iOS Localizable.strings
python3 generate_ios_strings.py
# Enables direct Storyboard localization
```

See [DIRECT_XML_STORYBOARD_INTEGRATION.md](DIRECT_XML_STORYBOARD_INTEGRATION.md) for complete implementation details.

## 📚 Comprehensive Guides

This project includes detailed documentation for various use cases:

### Core Guides
- **[STRING_RESOURCES_GUIDE.md](STRING_RESOURCES_GUIDE.md)** - Basic shared string resources implementation
- **[NATIVE_XML_STORYBOARD_GUIDE.md](NATIVE_XML_STORYBOARD_GUIDE.md)** - Pure native integration without Compose

### Advanced Guides  
- **[MIGRATION_GUIDE.md](MIGRATION_GUIDE.md)** - Complete migration from existing native resources
- **[DIRECT_XML_STORYBOARD_INTEGRATION.md](DIRECT_XML_STORYBOARD_INTEGRATION.md)** - Build-time generation for direct XML/Storyboard usage
- **[NATIVE_INTEGRATION_GUIDE.md](NATIVE_INTEGRATION_GUIDE.md)** - Native iOS Swift and Android integration alongside Compose

## 🏗️ Project Structure

```
demo/
├── README.md                                    # This file
├── build.gradle.kts                            # Root build configuration
├── settings.gradle.kts                         # Project settings
├── gradle/                                     # Gradle wrapper and dependencies
├── composeApp/                                 # Main KMP module
│   ├── build.gradle.kts                       # Module build configuration
│   ├── src/
│   │   ├── commonMain/kotlin/io/yavero/demo/
│   │   │   ├── StringResourceManager.kt       # Common interface (expect)
│   │   │   └── StringKeys.kt                  # String key constants
│   │   ├── androidMain/
│   │   │   ├── assets/
│   │   │   │   ├── strings_en.json           # English strings
│   │   │   │   └── strings_ar.json           # Arabic strings
│   │   │   └── kotlin/io/yavero/demo/
│   │   │       ├── StringResourceManager.android.kt  # Android implementation
│   │   │       ├── MainActivity.kt                   # Main Android activity
│   │   │       ├── NativeXmlActivity.kt              # Native XML demo
│   │   │       └── AndroidStringResourceBridge.kt    # XML generation utilities
│   │   └── iosMain/kotlin/io/yavero/demo/
│   │       └── StringResourceManager.ios.kt   # iOS implementation
├── iosApp/                                     # iOS application
│   ├── iosApp.xcodeproj/                      # Xcode project
│   └── iosApp/
│       ├── ContentView.swift                  # Main iOS view
│       ├── NativeSwiftView.swift              # Native SwiftUI demo
│       ├── StringResourcesHelper.swift        # Swift helper utilities
│       ├── IOSStringResourceBridge.swift      # Localizable.strings generation
│       └── NativeStoryboardViewController.swift # Native UIKit demo
└── Documentation/                              # Additional guides
    ├── STRING_RESOURCES_GUIDE.md
    ├── MIGRATION_GUIDE.md
    ├── NATIVE_XML_STORYBOARD_GUIDE.md
    ├── DIRECT_XML_STORYBOARD_INTEGRATION.md
    └── NATIVE_INTEGRATION_GUIDE.md
```

## 🔄 Migration from Existing Projects

### From Android strings.xml

1. **Convert XML to JSON:**
```python
# Use provided conversion script
python3 convert_strings_xml_to_json.py app/src/main/res/values/strings.xml strings_en.json
```

2. **Update code:**
```kotlin
// Before
getString(R.string.welcome_title)

// After  
stringManager.getString(StringKeys.WELCOME_TITLE)
```

### From iOS Localizable.strings

1. **Convert to JSON:**
```python
# Use provided conversion script
python3 convert_localizable_to_json.py Base.lproj/Localizable.strings strings_en.json
```

2. **Update code:**
```swift
// Before
NSLocalizedString("welcome_title", comment: "")

// After
stringManager.getString(key: "welcome_title")
```

See [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) for complete migration instructions, including automated tools for large projects.

## ⚙️ Build Configuration

### Android (build.gradle.kts)
```kotlin
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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

### iOS Framework
```kotlin
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
```

## 🧪 Testing

### Run Tests
```bash
# Android
./gradlew test

# iOS  
xcodebuild test -project iosApp/iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 15'
```

### Validation
```bash
# Validate string completeness across languages
./gradlew validateStringResources
```

## 🎯 Best Practices

### 1. String Organization
- Use consistent naming conventions (snake_case)
- Group related strings with prefixes (`error_`, `action_`, `welcome_`)
- Keep string keys descriptive but concise

### 2. Localization
- Always provide fallback strings in English
- Test RTL layouts thoroughly
- Use proper format specifiers (`%s`, `%d`) for cross-platform compatibility

### 3. Performance
- Cache StringResourceManager instances
- Use StringKeys constants for type safety
- Avoid creating new instances in loops

### 4. Team Workflow
- Update JSON files first, then regenerate native resources if using build-time generation
- Use validation scripts to ensure translation completeness
- Document string usage and context for translators

## 🚨 Troubleshooting

### Common Issues

**Build Errors:**
```bash
# Clean and rebuild
./gradlew clean build
```

**Missing Strings:**
- Check JSON file syntax and encoding (UTF-8)
- Verify string keys match StringKeys constants
- Ensure all languages have the same keys

**iOS Framework Issues:**
- Verify framework name is "SharedApp" in Swift imports
- Check Xcode project settings for framework integration

**Localization Not Working:**
- Test device language settings
- Verify JSON files are in correct assets directory
- Check RTL detection logic for Arabic

## 📈 Scaling to Large Projects

This system is designed to handle enterprise-scale applications:

- **1000+ strings**: Efficient JSON parsing and caching
- **Multiple teams**: Clear separation of concerns and documentation
- **CI/CD integration**: Automated validation and generation scripts
- **Gradual migration**: Bridge classes for incremental adoption

## 🤝 Contributing

### Adding New Features
1. Update common interface in `StringResourceManager.kt`
2. Implement in both Android and iOS actual classes
3. Add tests and documentation
4. Update relevant guides

### Adding New Languages
1. Create new JSON file: `strings_[language_code].json`
2. Update iOS implementation to include hardcoded strings
3. Test RTL support if applicable
4. Update documentation

## 📄 License

This project is provided as a demonstration and educational resource. Feel free to use and adapt for your own projects.

## 🔗 Related Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Android String Resources](https://developer.android.com/guide/topics/resources/string-resource)
- [iOS Localization Guide](https://developer.apple.com/documentation/xcode/localization)

## 📞 Support

For questions about implementation or usage:
1. Check the comprehensive guides in this repository
2. Review the demo applications for practical examples
3. Examine the source code for implementation details

---

**This project demonstrates the ultimate solution for sharing string resources between Android and iOS**, providing a centralized, maintainable, and migration-friendly approach that works seamlessly with native platform development while maintaining all the benefits of shared resources.