plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.details"
}

dependencies {
    implementation(project(Modules.Core.CORE_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation(project(Modules.Core.CORE_DATA))
}