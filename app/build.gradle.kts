import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf")
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
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        }
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:22.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx) //"androidx.core:core-ktx:1.9.0")
    implementation(libs.androidx.lifecycle.runtime.ktx)//"androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation(libs.androidx.activity.compose)//"androidx.activity:activity-compose:1.6.1")
    implementation(libs.androidx.compose.ui)//"androidx.compose.ui:ui:1.3.3")
    implementation(libs.androidx.compose.ui.tooling.preview)//"androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation(libs.androidx.compose.material3)//"androidx.compose.material3:material3:1.0.1")
    implementation(libs.androidx.datastore.preferences)//"androidx.datastore:datastore-preferences:1.0.0")
    implementation(libs.androidx.datastore)//"androidx.datastore:datastore:1.0.0")

    implementation(libs.google.dagger.hilt.android)//"com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    implementation(libs.protobuf.kotlin.lite)//"com.google.protobuf:protobuf-kotlin-lite:3.21.12")

    implementation(libs.kotlinx.serialization.json)//"org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation(libs.retrofit2.serialization.converter)//"com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation(libs.okhttp)//"com.squareup.okhttp3:okhttp:4.10.0")
    implementation(libs.retrofit)//"com.squareup.retrofit2:retrofit:2.9.0")

    testImplementation(libs.junit)//"junit:junit:4.13.2")
    androidTestImplementation(libs.androidx.test.junit)//"androidx.test.ext:junit:1.1.5")
    androidTestImplementation(libs.androidx.espresso.core)//"androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)//"androidx.compose.ui:ui-test-junit4:1.3.3")
    debugImplementation(libs.androidx.compose.ui.tooling.preview)//"androidx.compose.ui:ui-tooling:1.3.3")
    debugImplementation(libs.androidx.compose.ui.test.manifest)//"androidx.compose.ui:ui-test-manifest:1.3.3")

    // testing
    testImplementation (libs.mockito.kotlin)//"org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation (libs.kotlinx.coroutines.test)//"org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation (libs.mockito.android)//"org.mockito:mockito-android:5.3.0")
    testImplementation (libs.mockk)//"io.mockk:mockk:1.13.5")
    testImplementation (libs.robolectric)//"org.robolectric:robolectric:4.9")
    testImplementation (libs.androidx.core)//"androidx.test:core:1.5.0")
}

kapt {
    correctErrorTypes = true
}