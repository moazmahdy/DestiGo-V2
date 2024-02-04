plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.ui"
}

dependencies {
    implementation(project(Modules.Auth.AUTH_DOMAIN))
    implementation(project(Modules.Auth.AUTH_DATA))
    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation(project(Modules.Core.CORE_UI))
    implementation(project(Modules.NAVIGATION))
}