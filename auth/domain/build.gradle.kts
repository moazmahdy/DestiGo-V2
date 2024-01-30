plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.auth_domain"
}

dependencies {
    implementation(project(Modules.Core.CORE_DOMAIN))
}