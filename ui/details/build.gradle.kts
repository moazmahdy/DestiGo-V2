plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.details"
}

dependencies {
    implementation(project(Modules.COMMON_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
}