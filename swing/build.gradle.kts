plugins {
  kotlin("jvm")
}

dependencies {
  val flListeningVersion = findProperty("lucasalfare.fllistening.version") ?: ""

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation(project(":core"))
}