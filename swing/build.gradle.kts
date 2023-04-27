plugins {
  kotlin("jvm")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  runtimeOnly("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation(project(":core"))
}