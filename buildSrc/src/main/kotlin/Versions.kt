import org.gradle.api.JavaVersion

object Versions {
    object App {
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0"
        const val MIN_SDK = 24
        const val TARGET_SDK = 34
        const val COMPILE_SDK = 34
        const val BUILD_TOOLS = "33.0.0"
    }

    object Plugins {
        const val APP_SWEET = "1.4.0"
    }

    object Google {
        const val MATERIAL = "1.7.0"
        const val GSON = "2.9.0"
        const val LOCATION = "21.1.0"
        const val SERVICES = "18.3.0"
    }

    object AndroidX {
        const val CORE = "1.8.10"
        const val APPCOMPAT = "1.6.1"
        const val LIFECYCLE = "2.5.1"
        const val PAGING = "3.1.1"
    }

    object Test {
        const val JUNIT = "4.13.2"
        const val JUNIT_INTEGRATION = "1.1.2"
        const val ESPRESSO = "3.3.0"
    }

    object Firebase {
        const val FIRESTORE = "24.10.1"
        const val FIREBASE_BOM = "32.7.1"
        const val FIREBASE_AUTH = "22.3.1"
    }

    object Network {
        const val HILT = "2.47"
        const val HILT_NAVIGATION = "1.0.0"
        const val DAGGER = "2.44"
        const val TIMBER = "4.7.1"
        const val RETROFIT = "2.9.0"
        const val OKHTTP3 = "4.10.0"
        const val LOGGINIG_INTERCEPTOR = "3.1.0"
        const val LOGGING = "4.10.0"
        const val COIL = "2.4.0"
        const val LEAKCANARY = "2.10"
        const val ROOM = "2.4.3"
    }

    object Coroutines {
        const val Coroutines = "1.7.2"
    }

    object Compose {
        const val COMPOSE_BOOM = "2023.10.01"
        const val COMPOSE_ACTIVITY = "1.7.2"
        const val COMPOSE_NAVIGATION = "2.7.6"
        const val COMPOSE_MATERIAL = "1.6.0"
        const val COMPOSE_MATERIAL3 = "1.1.2"
        const val COMPOSE_PAGING = "1.0.0-alpha19"
        const val COMPOSE_COIL = "2.5.0"
    }

    object Design {
        const val LOTTE_VERSION = "6.0.0"
    }

    const val GRADLE = "7.3.1"

    const val KOTLIN = "1.8.0"

    const val HILT = "2.44"

    const val CORE = "1.5.0"

    const val GOOGLE = "4.3.15"

    const val APPLICATION = "8.0.2"

    val JAVA = JavaVersion.VERSION_17
}
