import UIKit
import SwiftUI
import SharedApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        CombinedView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



