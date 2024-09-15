plugins {
    alias(libs.plugins.android.application) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" // this version matches your Kotlin version
}

