import org.jetbrains.compose.desktop.application.dsl.TargetFormat

group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  implementation(project(":core"))
  implementation(compose.desktop.currentOs)
  implementation("com.github.LucasAlfare:FLListening:v2.0")
}

compose.desktop {
  sourceSets.getByName("main") {
    val allNeededDirectories = mutableListOf<String>()

    allNeededDirectories.addAll(resources.srcDirs.map { it.toString() })

    allNeededDirectories += project
      .layout
      .projectDirectory
      .dir("../fltimer_resources")
      .toString()

    resources.setSrcDirs(allNeededDirectories)
  }

  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "ComposeDesktopFLTimer"
      packageVersion = "1.0.0"
    }
  }
}
