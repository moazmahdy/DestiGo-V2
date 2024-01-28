import Dependencies.common
import Dependencies.retrofit
import Dependencies.google
import Dependencies.navigation
import Dependencies.firebase
import Dependencies.lifecycle
import Dependencies.hilt
import Dependencies.design
import Dependencies.compose
import Dependencies.coroutines
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = AndroidConstants.APP_ID
    compileSdk = (Versions.App.COMPILE_SDK)

    defaultConfig {
        multiDexEnabled = true
        minSdk = (Versions.App.MIN_SDK)
        targetSdk = (Versions.App.TARGET_SDK)
        testInstrumentationRunner = AndroidConstants.TEST_RUNNER
        buildTypes {
            getByName(AndroidConstants.BuildTypes.DEBUG) {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

    }

    compileOptions {
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }

    buildFeatures.apply {
        @Incubating
        compose = true
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    common()
    lifecycle()
    firebase()
    google()
    compose()
    navigation()
    retrofit()
    hilt()
    coroutines()
    design()
    testImplementation(Dependencies.Test.Unit.JUNIT)
}
