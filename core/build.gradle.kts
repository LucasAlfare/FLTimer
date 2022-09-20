plugins {
    kotlin("jvm")
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("io.socket:socket.io-client:2.1.0")
}