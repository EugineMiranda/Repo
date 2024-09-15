pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

    buildscript {
        repositories {
            maven { url = uri("https://jitpack.io") }
            google()
            mavenCentral()
        }
        dependencies {
            // Ensure this matches your Kotlin version
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
            // You may need other classpath dependencies here
        }
    }


rootProject.name = "FingerPrintAuth"
include(":app")
