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
    implementation("com.google.gms:google-services:4.3.15")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.44")
    implementation(kotlin("script-runtime"))
}