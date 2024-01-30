import Plugins

plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.data"
}

dependencies {
    implementation(project(Modules.Core.CORE_DOMAIN))
}