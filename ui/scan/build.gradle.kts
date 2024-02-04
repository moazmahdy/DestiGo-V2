plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.scan"
}

dependencies {
    implementation(project(Modules.Core.CORE_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
}