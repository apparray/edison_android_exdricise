@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.protobufPlugin) apply false
    alias(libs.plugins.serilizationPlugin)
    alias(libs.plugins.jvmPlugin)
}