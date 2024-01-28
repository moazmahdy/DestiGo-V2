import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler



fun DependencyHandler.debugImplementation(dependencyNotation: String): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.implementation(dependencyNotation: String): Dependency? =
    add("implementation", dependencyNotation)


fun DependencyHandler.implementationPlatform(dependencyNotation: String): Dependency? =
    add("implementation", platform(dependencyNotation))


fun DependencyHandler.apiPlatform(dependencyNotation: String): Dependency? =
    add("api", platform(dependencyNotation))

fun DependencyHandler.annotationProcessor(dependencyNotation: String): Dependency? =
    add("annotationProcessor", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: String): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: String): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}