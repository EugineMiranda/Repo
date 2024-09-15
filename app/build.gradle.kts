plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.eugine.fingerprintauth"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eugine.fingerprintauth"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0"
}

dependencies {
    implementation(libs.repo)
    implementation(libs.kotlin.stdlib)
    implementation(libs.jsyn.v1710)
    testImplementation(libs.androidx.junit.v113)
    testImplementation(libs.androidx.junit.ktx)
    implementation(libs.ui)
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.biometric)
    implementation(libs.androidx.media3.common.ktx)

    dependencies {
        val media3 = "1.4.1"

        // For media playback using ExoPlayer
        implementation(libs.androidx.media3.exoplayer)

        // For DASH playback support with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.dash)
        // For HLS playback support with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.hls)
        // For SmoothStreaming playback support with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.smoothstreaming)
        // For RTSP playback support with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.rtsp)
        // For MIDI playback support with ExoPlayer (see additional dependency requirements in
        // https://github.com/androidx/media/blob/release/libraries/decoder_midi/README.md)
        implementation(libs.androidx.media3.exoplayer.midi)
        // For ad insertion using the Interactive Media Ads SDK with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.ima)

        // For loading data using the Coronet network stack
        implementation(libs.androidx.media3.datasource.cronet)
        // For loading data using the OkHttp network stack
        implementation(libs.androidx.media3.datasource.okhttp)
        // For loading data using libretto
        implementation(libs.androidx.media3.datasource.rtmp)

        // For building media playback UIs
        implementation(libs.androidx.media3.ui)
        // For building media playback UIs for Android TV using the Jetpack Leanback library
        implementation(libs.androidx.media3.ui.leanback)

        // For exposing and controlling media sessions
        implementation(libs.androidx.media3.session)

        // For extracting data from media containers
        implementation(libs.androidx.media3.extractor)

        // For integrating with Cast
        implementation(libs.androidx.media3.cast)

        // For scheduling background operations using Jetpack Work's WorkManager with ExoPlayer
        implementation(libs.androidx.media3.exoplayer.workmanager)

        // For transforming media files
        implementation(libs.androidx.media3.transformer)

        // For applying effects on video frames
        implementation(libs.androidx.media3.effect)

        // For maxing media files
        implementation(libs.androidx.media3.muxer)

        // Utilities for testing media components (including ExoPlayer components)
        implementation(libs.androidx.media3.test.utils)
        // Utilities for testing media components (including ExoPlayer components) via Robolectric
        implementation(libs.androidx.media3.test.utils.robolectric)

        // Common functionality for reading and writing media containers
        implementation(libs.androidx.media3.container)
        // Common functionality for media database components
        implementation(libs.androidx.media3.database)
        // Common functionality for media decoders
        implementation(libs.androidx.media3.decoder)
        // Common functionality for loading data
        implementation(libs.androidx.media3.datasource)
        // Common functionality used across multiple media libraries
        implementation(libs.androidx.media3.common)
        // Common Kotlin-specific functionality
        implementation(libs.androidx.media3.common.ktx)
    }

}

