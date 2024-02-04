plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.addpost"
}

dependencies {
    implementation(project(Modules.Core.CORE_UI))
    implementation(project(Modules.Core.CORE_DOMAIN))
}