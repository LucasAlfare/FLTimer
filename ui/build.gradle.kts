plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.1.1"
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
    implementation(project(":core"))
}