plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.data"
}

dependencies {
    implementation(project(Modules.Auth.AUTH_DOMAIN))
}