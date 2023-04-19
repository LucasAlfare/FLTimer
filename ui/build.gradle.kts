plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("com.lucasalfare.fllistening:FLListening") {
        version {
            branch = "master"
        }
    }

    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
    implementation(project(":core"))
}