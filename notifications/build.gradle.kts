version = "0.0.1"

plugins {
    kotlin("jvm")
    // For building fat jar
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

kotlin {
    // This will make sure that compileKotlin and compileKotlinTest have same JVM version
    // If you use Gradle 8.0.2 or higher, you also need to add a toolchain resolver plugin.
    // This type of plugin manages which repositories to download a toolchain from. As an example, add to your settings.gradle(.kts) the following plugin:
    jvmToolchain(17)
}
repositories {
    mavenCentral()
}


val exposedVersion = "0.41.1"

dependencies {
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}


tasks {
    /**
     * Build docker image for running in docker-composer locally
     */
    val buildDockerImage by register<Exec>("buildDockerImage") {
        group = "build"
        workingDir("../docker")
        commandLine("bash", "-c", "docker-compose build notifications")
        dependsOn(":notifications:shadowJar")
    }

    shadowJar {
        archiveBaseName.set("notifications-all")
        archiveClassifier.set("")
        archiveVersion.set("")
        manifest {
            attributes["Main-Class"] = "com.shorty.notifications.MainKt"
        }
    }
}

