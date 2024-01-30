plugins {
    id(Plugins.COMMON)
}

android {
    namespace = "com.mobilebreakero.auth_data"
}

dependencies {
    implementation(project(Modules.Auth.AUTH_DOMAIN))
    implementation(project(Modules.Core.CORE_DOMAIN))
    implementation("com.sun.mail:android-activation:1.6.0")
    implementation("com.sun.mail:android-mail:1.6.0")
}