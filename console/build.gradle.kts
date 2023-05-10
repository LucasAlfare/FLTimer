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
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("com.github.ajalt.mordant:mordant:2.0.0-beta13")
  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation("com.github.LucasAlfare:FLBinary:$flBinaryVersion")
}