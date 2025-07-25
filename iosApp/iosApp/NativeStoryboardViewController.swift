import UIKit
import SharedApp

/**
 * Native iOS ViewController that demonstrates using shared string resources
 * in traditional Storyboards and UIKit components (without SwiftUI).
 */
class NativeStoryboardViewController: UIViewController {
    
    private let stringManager = StringResourceManager()
    private let stringBridge = IOSStringResourceBridge()
    private var counter = 0
    
    // UI Components created programmatically (simulating Storyboard connections)
    private let scrollView = UIScrollView()
    private let contentView = UIView()
    private let titleLabel = UILabel()
    private let descriptionLabel = UILabel()
    private let counterLabel = UILabel()
    private let clickMeButton = UIButton(type: .system)
    private let retryButton = UIButton(type: .system)
    private let showAlertButton = UIButton(type: .system)
    private let generateStringsButton = UIButton(type: .system)
    private let sampleTextsStackView = UIStackView()
    private let languageInfoStackView = UIStackView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set navigation title using shared strings
        IOSStoryboardIntegration.setNavigationTitleFromSharedString(self, key: "app_name")
        
        setupUI()
        setupConstraints()
        setupActions()
        updateCounter()
    }
    
    private func setupUI() {
        view.backgroundColor = .systemBackground
        
        // Configure scroll view
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        contentView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(scrollView)
        scrollView.addSubview(contentView)
        
        // Configure title label
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        IOSStoryboardIntegration.setLabelTextFromSharedString(titleLabel, key: "native_demo_title")
        titleLabel.font = UIFont.boldSystemFont(ofSize: 24)
        titleLabel.numberOfLines = 0
        titleLabel.textAlignment = .center
        contentView.addSubview(titleLabel)
        
        // Configure description label
        descriptionLabel.translatesAutoresizingMaskIntoConstraints = false
        IOSStoryboardIntegration.setLabelTextFromSharedString(descriptionLabel, key: "native_demo_description")
        descriptionLabel.font = UIFont.systemFont(ofSize: 16)
        descriptionLabel.numberOfLines = 0
        descriptionLabel.textAlignment = .center
        contentView.addSubview(descriptionLabel)
        
        // Configure sample texts section
        setupSampleTextsSection()
        
        // Configure counter label
        counterLabel.translatesAutoresizingMaskIntoConstraints = false
        counterLabel.font = UIFont.boldSystemFont(ofSize: 20)
        counterLabel.textAlignment = .center
        contentView.addSubview(counterLabel)
        
        // Configure buttons
        setupButtons()
        
        // Configure language info section
        setupLanguageInfoSection()
    }
    
    private func setupSampleTextsSection() {
        sampleTextsStackView.translatesAutoresizingMaskIntoConstraints = false
        sampleTextsStackView.axis = .vertical
        sampleTextsStackView.spacing = 8
        sampleTextsStackView.alignment = .leading
        contentView.addSubview(sampleTextsStackView)
        
        // Header
        let headerLabel = UILabel()
        headerLabel.text = "Sample Shared Strings:"
        headerLabel.font = UIFont.boldSystemFont(ofSize: 18)
        sampleTextsStackView.addArrangedSubview(headerLabel)
        
        // Sample texts
        let sampleKeys = ["sample_text_1", "sample_text_2", "sample_text_3"]
        for key in sampleKeys {
            let label = UILabel()
            label.text = "• " + stringManager.getString(key: key)
            label.font = UIFont.systemFont(ofSize: 14)
            label.numberOfLines = 0
            sampleTextsStackView.addArrangedSubview(label)
        }
    }
    
    private func setupButtons() {
        // Click me button
        clickMeButton.translatesAutoresizingMaskIntoConstraints = false
        IOSStoryboardIntegration.setButtonTitleFromSharedString(clickMeButton, key: "button_click_me")
        clickMeButton.backgroundColor = .systemBlue
        clickMeButton.setTitleColor(.white, for: .normal)
        clickMeButton.layer.cornerRadius = 8
        clickMeButton.contentEdgeInsets = UIEdgeInsets(top: 12, left: 24, bottom: 12, right: 24)
        contentView.addSubview(clickMeButton)
        
        // Retry button
        retryButton.translatesAutoresizingMaskIntoConstraints = false
        IOSStoryboardIntegration.setButtonTitleFromSharedString(retryButton, key: "action_retry")
        retryButton.backgroundColor = .systemGray
        retryButton.setTitleColor(.white, for: .normal)
        retryButton.layer.cornerRadius = 8
        retryButton.contentEdgeInsets = UIEdgeInsets(top: 12, left: 24, bottom: 12, right: 24)
        contentView.addSubview(retryButton)
        
        // Show alert button
        showAlertButton.translatesAutoresizingMaskIntoConstraints = false
        showAlertButton.setTitle("Show Alert Dialog", for: .normal)
        showAlertButton.backgroundColor = .systemOrange
        showAlertButton.setTitleColor(.white, for: .normal)
        showAlertButton.layer.cornerRadius = 8
        showAlertButton.contentEdgeInsets = UIEdgeInsets(top: 12, left: 24, bottom: 12, right: 24)
        contentView.addSubview(showAlertButton)
        
        // Generate strings button
        generateStringsButton.translatesAutoresizingMaskIntoConstraints = false
        generateStringsButton.setTitle("Generate Localizable.strings", for: .normal)
        generateStringsButton.backgroundColor = .systemGreen
        generateStringsButton.setTitleColor(.white, for: .normal)
        generateStringsButton.layer.cornerRadius = 8
        generateStringsButton.contentEdgeInsets = UIEdgeInsets(top: 12, left: 24, bottom: 12, right: 24)
        contentView.addSubview(generateStringsButton)
    }
    
    private func setupLanguageInfoSection() {
        languageInfoStackView.translatesAutoresizingMaskIntoConstraints = false
        languageInfoStackView.axis = .vertical
        languageInfoStackView.spacing = 4
        languageInfoStackView.alignment = .leading
        contentView.addSubview(languageInfoStackView)
        
        // Header
        let headerLabel = UILabel()
        IOSStoryboardIntegration.setLabelTextFromSharedString(headerLabel, key: "language_info")
        headerLabel.font = UIFont.boldSystemFont(ofSize: 14)
        languageInfoStackView.addArrangedSubview(headerLabel)
        
        // Current language
        let currentLanguageLabel = UILabel()
        currentLanguageLabel.text = stringManager.getFormattedString(key: "current_language", args: [stringManager.getCurrentLanguage()])
        currentLanguageLabel.font = UIFont.systemFont(ofSize: 12)
        languageInfoStackView.addArrangedSubview(currentLanguageLabel)
        
        // RTL status
        let rtlLabel = UILabel()
        rtlLabel.text = stringManager.getFormattedString(key: "is_rtl", args: [stringManager.isRTL() ? "Yes" : "No"])
        rtlLabel.font = UIFont.systemFont(ofSize: 12)
        languageInfoStackView.addArrangedSubview(rtlLabel)
        
        // System locale
        let localeLabel = UILabel()
        localeLabel.text = "System Locale: \\(Locale.current.identifier)"
        localeLabel.font = UIFont.systemFont(ofSize: 12)
        languageInfoStackView.addArrangedSubview(localeLabel)
    }
    
    private func setupConstraints() {
        NSLayoutConstraint.activate([
            // Scroll view
            scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            // Content view
            contentView.topAnchor.constraint(equalTo: scrollView.topAnchor),
            contentView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            contentView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            contentView.bottomAnchor.constraint(equalTo: scrollView.bottomAnchor),
            contentView.widthAnchor.constraint(equalTo: scrollView.widthAnchor),
            
            // Title label
            titleLabel.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 32),
            titleLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 32),
            titleLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -32),
            
            // Description label
            descriptionLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 16),
            descriptionLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 32),
            descriptionLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -32),
            
            // Sample texts stack view
            sampleTextsStackView.topAnchor.constraint(equalTo: descriptionLabel.bottomAnchor, constant: 24),
            sampleTextsStackView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 32),
            sampleTextsStackView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -32),
            
            // Counter label
            counterLabel.topAnchor.constraint(equalTo: sampleTextsStackView.bottomAnchor, constant: 24),
            counterLabel.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 32),
            counterLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -32),
            
            // Click me button
            clickMeButton.topAnchor.constraint(equalTo: counterLabel.bottomAnchor, constant: 16),
            clickMeButton.centerXAnchor.constraint(equalTo: contentView.centerXAnchor, constant: -60),
            
            // Retry button
            retryButton.topAnchor.constraint(equalTo: counterLabel.bottomAnchor, constant: 16),
            retryButton.centerXAnchor.constraint(equalTo: contentView.centerXAnchor, constant: 60),
            
            // Show alert button
            showAlertButton.topAnchor.constraint(equalTo: clickMeButton.bottomAnchor, constant: 16),
            showAlertButton.centerXAnchor.constraint(equalTo: contentView.centerXAnchor),
            
            // Generate strings button
            generateStringsButton.topAnchor.constraint(equalTo: showAlertButton.bottomAnchor, constant: 16),
            generateStringsButton.centerXAnchor.constraint(equalTo: contentView.centerXAnchor),
            
            // Language info stack view
            languageInfoStackView.topAnchor.constraint(equalTo: generateStringsButton.bottomAnchor, constant: 32),
            languageInfoStackView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 32),
            languageInfoStackView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -32),
            languageInfoStackView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -32)
        ])
    }
    
    private func setupActions() {
        clickMeButton.addTarget(self, action: #selector(clickMeButtonTapped), for: .touchUpInside)
        retryButton.addTarget(self, action: #selector(retryButtonTapped), for: .touchUpInside)
        showAlertButton.addTarget(self, action: #selector(showAlertButtonTapped), for: .touchUpInside)
        generateStringsButton.addTarget(self, action: #selector(generateStringsButtonTapped), for: .touchUpInside)
    }
    
    @objc private func clickMeButtonTapped() {
        counter += 1
        updateCounter()
    }
    
    @objc private func retryButtonTapped() {
        counter = 0
        updateCounter()
    }
    
    @objc private func showAlertButtonTapped() {
        IOSStoryboardIntegration.showAlertWithSharedStrings(
            self,
            titleKey: "error_generic",
            messageKey: "error_network"
        )
    }
    
    @objc private func generateStringsButtonTapped() {
        let englishStrings = stringBridge.generateIOSLocalizableStrings(language: "en")
        let arabicStrings = stringBridge.generateIOSLocalizableStrings(language: "ar")
        
        let alert = UIAlertController(
            title: "Generated Localizable.strings",
            message: "English:\\n\\n\\(englishStrings)\\n\\n---\\n\\nArabic:\\n\\n\\(arabicStrings)",
            preferredStyle: .alert
        )
        
        alert.addAction(UIAlertAction(
            title: stringManager.getString(key: "action_ok"),
            style: .default
        ))
        
        present(alert, animated: true)
    }
    
    private func updateCounter() {
        counterLabel.text = stringManager.getFormattedString(key: "counter_label", args: [counter])
    }
}