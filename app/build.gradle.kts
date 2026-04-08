plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.countdowntimer"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.0") // UI
    implementation("androidx.compose.material3:material3:1.1.0") // Material3
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0") // Preview
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0")
    implementation("androidx.activity:activity-compose:1.7.0")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
}