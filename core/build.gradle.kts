group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""
  val flBinaryVersion =
    findProperty("lucasalfare.flbinary.version") ?: ""

  implementation(compose.runtime)
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation("com.github.LucasAlfare:FLBinary:$flBinaryVersion")
}
