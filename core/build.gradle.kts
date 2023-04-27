plugins {
  kotlin("jvm")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""
  val flBinaryVersion =
    findProperty("lucasalfare.flbinary.version") ?: ""

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  implementation("org.jetbrains.compose.runtime:runtime-desktop:1.4.0")
  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation("com.github.LucasAlfare:FLBinary:$flBinaryVersion")
}
