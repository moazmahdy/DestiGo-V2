import Dependencies.compose
import Dependencies.design
import Dependencies.retrofit
import Dependencies.common
import Dependencies.google
import Dependencies.hilt
import Dependencies.coroutines
import Dependencies.firebase
import Versions
plugins{
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mobilebreakero.destigo"
    compileSdk = (Versions.App.COMPILE_SDK)
    defaultConfig {
        multiDexEnabled = true
        minSdk = (Versions.App.MIN_SDK)
        targetSdk = (Versions.App.TARGET_SDK)
        testInstrumentationRunner = BuildConfig.TEST_RUNNER
        versionCode = (Versions.App.VERSION_CODE)
        versionName = (Versions.App.VERSION_NAME)
        buildTypes {
            getByName(BuildConfig.BuildTypes.DEBUG) {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }
    kotlinOptions {
        jvmTarget = Versions.JAVA.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.COMPOSE
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))

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

