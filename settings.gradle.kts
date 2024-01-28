pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            url = uri("https://jitpack.io")
        }

        mavenCentral()

    }
}

rootProject.name = "DestiGo"
include(":app")
include(":ui:auth")
include(":core:core-domain")
include(":core:core-data")
include(":ui:home")
include(":common-ui")
include(":ui:welcome")
include(":ui:navigation")
include(":ui:Interestedplaces")
include(":ui:scan")
include(":ui:profile")
include(":ui:trips")
include(":ui:testing")
include(":ui:search")
include(":ui:addPost")
include(":ui:details")
include(":auth:data")
include(":auth:domain")
include(":auth:ui")
