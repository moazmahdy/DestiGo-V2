import Plugins
import Modules

plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.core_ui"
}

dependencies {
    implementation(project(Modules.Core.CORE_DOMAIN))
}