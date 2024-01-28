import Dependencies.compose
import Dependencies.design
import Dependencies.retrofit
import Dependencies.common
import Dependencies.google
import Dependencies.hilt
import Dependencies.coroutines
import Dependencies.firebase

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT)
    id(Plugins.GOOGLE_SERVICES)
}

android {
    namespace = "com.mobilebreakero.destigo"
    compileSdk = (Versions.App.COMPILE_SDK)
    defaultConfig {
        multiDexEnabled = true
        minSdk = (Versions.App.MIN_SDK)
        targetSdk = (Versions.App.TARGET_SDK)
        testInstrumentationRunner = AndroidConstants.TEST_RUNNER
        versionCode = (Versions.App.VERSION_CODE)
        versionName = (Versions.App.VERSION_NAME)
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
}

dependencies {
    compose()
    design()
    retrofit()
    common()
    google()
    coroutines()
    hilt()
    firebase()

    implementation(project(Modules.COMMON_UI))

    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation(project(Modules.Core.CORE_DATA))

    implementation(project(Modules.Auth.AUTH_DOMAIN))
    implementation(project(Modules.Auth.AUTH_DATA))

    implementation(project(Modules.Ui.HOME))
    implementation(project(Modules.Ui.AUTH))
    implementation(project(Modules.Ui.NAVIGATION))
}