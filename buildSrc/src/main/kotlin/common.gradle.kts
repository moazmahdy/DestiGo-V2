import Dependencies.camera
import Dependencies.common
import Dependencies.compose
import Dependencies.coroutines
import Dependencies.design
import Dependencies.firebase
import Dependencies.google
import Dependencies.hilt
import Dependencies.lifecycle
import Dependencies.navigation
import Dependencies.retrofit
import java.util.Properties

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = BuildConfig.APP_ID
    compileSdk = (Versions.App.COMPILE_SDK)

    defaultConfig {
        multiDexEnabled = true
        minSdk = (Versions.App.MIN_SDK)
        targetSdk = (Versions.App.TARGET_SDK)
        testInstrumentationRunner = BuildConfig.TEST_RUNNER
        buildTypes {
            getByName(BuildConfig.BuildTypes.DEBUG) {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        defaultConfig {
            buildConfigField("String", "MAPS_API_KEY", "\"${localProperties["MAPS_API_KEY"]}\"")
            buildConfigField("String", "TRIP_API_KEY", "\"${localProperties["TRIP_API_KEY"]}\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }

    kotlinOptions {
        jvmTarget = Versions.JAVA.toString()
    }
    buildFeatures.apply {
        @Incubating
        compose = true
    }
    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.COMPOSE
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
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
    camera()
    testImplementation(Dependencies.Test.Unit.JUNIT)
    testImplementation(Dependencies.Test.Integration.JUNIT)
    testImplementation(Dependencies.Test.Integration.ESPRESSO_CORE)
    androidTestImplementation(Dependencies.Test.Integration.JUNIT)
}
