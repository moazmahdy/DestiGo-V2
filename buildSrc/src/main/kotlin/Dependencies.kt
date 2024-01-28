import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    object Kotlin {
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}"
    }

    object Coroutines {
        const val KOTLIN_COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines.Coroutines}"
        const val KOTLIN_COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines.Coroutines}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
        const val GSON = "com.google.code.gson:gson:${Versions.Google.GSON}"
        const val LOCATION =
            "com.google.android.gms:play-services-location:${Versions.Google.LOCATION}"
        const val SERVICES = "com.google.android.gms:play-services-auth:${Versions.Google.SERVICES}"
    }

    object AndroidX {
        const val CORE = "androidx.core:core-ktx:${Versions.AndroidX.CORE}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APPCOMPAT}"
        const val PAGING = "androidx.paging:paging-runtime-ktx:${Versions.AndroidX.PAGING}"
        const val ANDROID_LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val ANDROID_LIFECYCLE_LIVEDATA =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val ANDROID_LIFECYCLE_COMPAILER =
            "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.LIFECYCLE}"
    }

    object Firebase {
        const val FIRESTORE =
            "com.google.firebase:firebase-firestore:${Versions.Firebase.FIRESTORE}"
        const val FIREBASE_BOM =
            "com.google.firebase:firebase-bom:${Versions.Firebase.FIREBASE_BOM}"
        const val FIREBASE_AUTH =
            "com.google.firebase:firebase-auth-ktx:${Versions.Firebase.FIREBASE_AUTH}"
        const val FIREBASE_AUTH_KTX =
            "com.google.firebase:firebase-auth-ktx:${Versions.Firebase.FIREBASE_AUTH}"
        const val FIRESTORE_KTX =
            "com.google.firebase:firebase-firestore-ktx:${Versions.Firebase.FIRESTORE}"
    }

    object Network {
        const val HILT = "com.google.dagger:hilt-android:${Versions.Network.DAGGER}"
        const val HILT_NAVIGATION =
            "androidx.hilt:hilt-navigation-compose:${Versions.Network.HILT_NAVIGATION}"
        const val DAGGER = "com.google.dagger:hilt-android:${Versions.Network.DAGGER}"
        const val HILT_COMPILER =
            "com.google.dagger:hilt-compiler:${Versions.Network.DAGGER}"
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.Network.TIMBER}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val OKHTTP3 = "com.squareup.okhttp3:okhttp:${Versions.Network.OKHTTP3}"
        const val LOGGINIG_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LOGGINIG_INTERCEPTOR}"
        const val LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LOGGING}"
        const val COIL = "io.coil-kt:coil-compose:${Versions.Network.COIL}"
        const val LEAKCANARY =
            "com.squareup.leakcanary:leakcanary-android:${Versions.Network.LEAKCANARY}"
        const val ROOM = "androidx.room:room-runtime:${Versions.Network.ROOM}"
    }

    object Compose {
        const val COMPOSE_BOOM =
            "androidx.compose:compose-bom:${Versions.Compose.COMPOSE_BOOM}"
        const val COMPOSE_ACTIVITY =
            "androidx.activity:activity-compose:${Versions.Compose.COMPOSE_ACTIVITY}"
        const val COMPOSE_NAVIGATION =
            "androidx.navigation:navigation-compose:${Versions.Compose.COMPOSE_NAVIGATION}"
        const val COMPOSE_MATERIAL =
            "androidx.compose.material:material:${Versions.Compose.COMPOSE_MATERIAL}"
        const val COMPOSE_MATERIAL3 =
            "androidx.compose.material3:material3:${Versions.Compose.COMPOSE_MATERIAL3}"
        const val COMPOSE_PAGING =
            "androidx.paging:paging-compose:${Versions.Compose.COMPOSE_PAGING}"
        const val COMPOSE_COIL = "io.coil-kt:coil-compose:${Versions.Compose.COMPOSE_COIL}"
    }

    object Design {
        const val LOTTE_VERSION =
            "com.airbnb.android:lottie-compose:${Versions.Design.LOTTE_VERSION}"
    }

    object ClassPath {
        const val BUILD_TOOLS = "com.android.tools.build:gradle:${Versions.GRADLE}"
        const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    }

    object Test {
        object Unit {
            const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
        }

        object Integration {
            const val JUNIT = "androidx.test.ext:junit:${Versions.Test.JUNIT_INTEGRATION}"
            const val ESPRESSO_CORE =
                "androidx.test.espresso:espresso-core:${Versions.Test.ESPRESSO}"
        }

    }

    fun DependencyHandlerScope.common() {
        api(AndroidX.APPCOMPAT)
        api(AndroidX.CORE)
    }

    fun DependencyHandlerScope.coroutines() {
        api(Coroutines.KOTLIN_COROUTINES)
        api(Coroutines.KOTLIN_COROUTINES_ANDROID)
    }

    fun DependencyHandlerScope.timber() {
        api(Network.TIMBER)
    }

    fun DependencyHandlerScope.hilt() {
        api(Network.HILT)
        api(Network.HILT_NAVIGATION)
        api(Network.DAGGER)
        kapt(Network.HILT_COMPILER)
    }

    fun DependencyHandlerScope.commonIntegrationTest() {
        testImplementation(Test.Integration.JUNIT)
        testImplementation(Test.Integration.ESPRESSO_CORE)
        testImplementation(Test.Unit.JUNIT)
    }

    fun DependencyHandlerScope.compose() {
        api(Compose.COMPOSE_ACTIVITY)
        api(Compose.COMPOSE_BOOM)
        api(Compose.COMPOSE_MATERIAL)
        api(Compose.COMPOSE_NAVIGATION)
        api(Compose.COMPOSE_PAGING)
        api(Compose.COMPOSE_MATERIAL3)
        api(Compose.COMPOSE_COIL)
    }

    fun DependencyHandlerScope.commonUnitTest() {
        testImplementation(Test.Unit.JUNIT)
    }

    fun DependencyHandlerScope.firebase() {
        api(Firebase.FIREBASE_BOM)
        api(Firebase.FIREBASE_AUTH)
        api(Firebase.FIRESTORE)
        api(Firebase.FIRESTORE_KTX)
        api(Firebase.FIREBASE_AUTH_KTX)
    }

    fun DependencyHandlerScope.google() {
        api(Google.MATERIAL)
        api(Google.GSON)
        api(Google.LOCATION)
        api(Google.SERVICES)
    }

    fun DependencyHandlerScope.navigation() {
        api(Compose.COMPOSE_NAVIGATION)
    }

    fun DependencyHandlerScope.room() {
        api(Network.ROOM)
    }

    fun DependencyHandlerScope.dataStore() {
        api(AndroidX.PAGING)
    }

    fun DependencyHandlerScope.lifecycle() {
        api(AndroidX.ANDROID_LIFECYCLE_VIEWMODEL)
        api(AndroidX.ANDROID_LIFECYCLE_LIVEDATA)
        kapt(AndroidX.ANDROID_LIFECYCLE_COMPAILER)
    }

    fun DependencyHandlerScope.design() {
        api(Design.LOTTE_VERSION)
    }

    fun DependencyHandlerScope.retrofit() {
        api(Network.RETROFIT)
        api(Network.OKHTTP3)
        api(Network.LOGGINIG_INTERCEPTOR)
        api(Network.LOGGING)
        api(Network.COIL)
        api(Network.LEAKCANARY)
    }

    fun DependencyHandlerScope.kotlinStubs() {
        api(Kotlin.KOTLIN)
        api(Kotlin.KOTLIN_REFLECT)
    }

}