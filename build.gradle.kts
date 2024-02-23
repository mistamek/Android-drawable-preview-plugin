plugins {
    kotlin("jvm") version "1.8.22"
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.mistamek.drawablepreview"
version = "1.1.8"

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // https://plugins.jetbrains.com/docs/intellij/android-studio-releases-list.html
    // https://plugins.jetbrains.com/docs/intellij/android-studio.html#android-studio-releases-listing
    version.set("2023.3.1.8")
    type.set("AI") // Target IDE Platform

    /* Plugin Dependencies */
    plugins.set(listOf("android"))

    /**
     * Patch plugin.xml with since and until build
     * values inferred from IDE version.
     */
    updateSinceUntilBuild.set(false)
}

repositories {
    google()
    mavenCentral()
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("233.*")
    }

    runIde {
        ideDir.set(file("d:\\develop\\android\\android-studio\\"))
    }
}
