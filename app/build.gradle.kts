plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"
    compileSdk = 33

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "CAT_FACT_API_ENDPOINT", "\"https://catfact.ninja/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/license.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/NOTICE.txt"
            excludes += "/META-INF/notice.txt"
            excludes += "/META-INF/ASL2.0"
            excludes += "/META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    implementation(libs.core)
    implementation(libs.lifeCycleRunTime)
    implementation(libs.activityCompose)
    implementation(libs.bundles.compose)
    implementation(libs.material)
    implementation(libs.bundles.datastore)
    implementation(libs.protobufLite)

    implementation(libs.kotlinSerialization)
    implementation(libs.retrofit2Serialization)

    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.junit4)
    debugImplementation(libs.uiTooling)
    debugImplementation(libs.uiTestManifest)

    //Compose live data
    implementation(libs.liveData)

    //Koin for dependency injection
    implementation(libs.koin)

    //Timber for safe logging
    implementation(libs.timber)

    // Okhttp3 interceptor
    implementation(libs.bundles.okhttp)

    // Retrofit2
    implementation(libs.bundles.retrofit2)

    // Core Testing
    testImplementation(libs.coreTesting)
    androidTestImplementation(libs.androidCoreTesting)

    // Mockk
    testImplementation(libs.mockk)
    debugImplementation(libs.androidMockk)

}