import SwiftUI
import SharedApp

/**
 * Native SwiftUI view that demonstrates using shared string resources
 * in pure native iOS applications.
 */
struct NativeSwiftView: View {
    @State private var showAlert = false
    @State private var counter = 0
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                // Header section using shared strings
                VStack(spacing: 10) {
                    Text(SharedStrings.welcomeTitle)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                    
                    Text(SharedStrings.welcomeDescription)
                        .font(.body)
                        .foregroundColor(.secondary)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal)
                }
                .padding(.top)
                
                Divider()
                
                // Sample texts section
                VStack(alignment: .leading, spacing: 8) {
                    Text("Sample Texts:")
                        .font(.headline)
                        .padding(.bottom, 5)
                    
                    Text("• \(SharedStrings.sampleText1)")
                    Text("• \(SharedStrings.sampleText2)")
                    Text("• \(SharedStrings.sampleText3)")
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal)
                
                Divider()
                
                // Interactive section
                VStack(spacing: 15) {
                    Text("Counter: \(counter)")
                        .font(.title2)
                        .fontWeight(.semibold)
                    
                    HStack(spacing: 20) {
                        Button(SharedStrings.buttonClickMe) {
                            counter += 1
                        }
                        .buttonStyle(.borderedProminent)
                        
                        Button(SharedStrings.actionRetry) {
                            counter = 0
                        }
                        .buttonStyle(.bordered)
                    }
                    
                    Button("Show Alert") {
                        showAlert = true
                    }
                    .buttonStyle(.borderless)
                    .foregroundColor(.blue)
                }
                
                Spacer()
                
                // Language info section
                VStack(spacing: 5) {
                    Text("Language Info:")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    
                    Text("Current Language: \(SharedStrings.currentLanguage)")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    
                    Text("Is RTL: \(SharedStrings.isRTL ? "Yes" : "No")")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .padding(.bottom)
            }
            .navigationTitle(SharedStrings.appName)
            .navigationBarTitleDisplayMode(.inline)
            .alert("Alert", isPresented: $showAlert) {
                Button(SharedStrings.actionOk) {
                    showAlert = false
                }
                Button(SharedStrings.actionCancel, role: .cancel) {
                    showAlert = false
                }
            } message: {
                Text(SharedStrings.errorGeneric)
            }
        }
        .environment(\.layoutDirection, SharedStrings.isRTL ? .rightToLeft : .leftToRight)
    }
}

/**
 * Combined view that shows both native SwiftUI and Compose content
 */
struct CombinedView: View {
    @State private var selectedTab = 0
    
    var body: some View {
        TabView(selection: $selectedTab) {
            // Native SwiftUI tab
            NativeSwiftView()
                .tabItem {
                    Image(systemName: "swift")
                    Text("Native Swift")
                }
                .tag(0)
            
            // Compose Multiplatform tab
            ComposeView()
                .tabItem {
                    Image(systemName: "app.badge")
                    Text("Compose")
                }
                .tag(1)
        }
    }
}

// Preview for SwiftUI Canvas
struct NativeSwiftView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            NativeSwiftView()
                .previewDisplayName("English")
            
            NativeSwiftView()
                .environment(\.locale, Locale(identifier: "ar"))
                .previewDisplayName("Arabic")
        }
    }
}