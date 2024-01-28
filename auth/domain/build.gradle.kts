plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.domain"
}

dependencies {
    implementation(project(Modules.Core.CORE_DOMAIN))
}