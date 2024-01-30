plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.profile"
}

dependencies {
    implementation(project(Modules.COMMON_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation(project(Modules.Auth.AUTH_DOMAIN))
}