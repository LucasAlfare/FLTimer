group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
