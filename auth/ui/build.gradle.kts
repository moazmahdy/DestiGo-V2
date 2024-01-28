plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.ui"
}

dependencies {
    implementation(project(Modules.Auth.AUTH_DOMAIN))
    implementation(project(Modules.Auth.AUTH_DATA))
}