group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") apply false
    id("org.jetbrains.compose") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
