plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // This should be in-sync with the Gradle version exposed by `Versions.kt`
    implementation("com.android.tools.build:gradle:7.3.1")

    // This should be in-sync with the Kotlin version exposed by `Versions.kt`
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")

    // dagger()
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.44")

    // google services
    implementation("com.google.gms:google-services:4.3.15")

    implementation(kotlin("script-runtime"))
}