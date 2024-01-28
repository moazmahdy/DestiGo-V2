plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.search"
}

dependencies {
    implementation(project(Modules.COMMON_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
}