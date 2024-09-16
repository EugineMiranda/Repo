plugins {
    alias(libs.plugins.android.application) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
    alias(libs.plugins.kotlin.android) apply false // this version matches your Kotlin version
}

buildscript {
    repositories {
        maven("https://jitpack.io")
        google()
        mavenCentral()
    }
    dependencies {
        // Ensure this matches your Kotlin version
        classpath(libs.kotlin.gradle.plugin)
        // You may need other classpath dependencies here
    }
}