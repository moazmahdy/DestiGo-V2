plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.home"
}

dependencies {
    implementation(project(Modules.Core.CORE_DATA))
    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation(project(Modules.COMMON_UI))
    implementation(libs.animated.navigation.bar)
}