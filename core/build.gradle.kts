group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  val flBinaryVersion =
    findProperty("lucasalfare.flbinary.version") ?: ""

  implementation(compose.runtime)
  implementation("com.github.LucasAlfare:FLListening:v2.0")
  implementation("com.github.LucasAlfare:FLBinary:$flBinaryVersion")
}
