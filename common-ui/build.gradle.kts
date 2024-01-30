import Plugins

plugins {
    id(Plugins.COMMON)
}


android {
    namespace = "com.mobilebreakero.common_ui"
}

dependencies {
    implementation(project(Modules.Core.CORE_DATA))
    implementation(project(Modules.Core.CORE_DOMAIN))
}