import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose") version "1.1.1"
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  google()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(compose.desktop.currentOs)
  implementation(project(":core"))
  implementation(project(":ui"))
}

compose.desktop {
  application {
    mainClass = "lucasalfare.fltimer.desktop.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "FLTimer"
      packageVersion = "1.0.0"

      windows {
        menu = true
        // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
        upgradeUuid = "AF792DA6-2EA3-495A-95E5-C3C6CBCB9948"
      }

      macOS {
        // Use -Pcompose.desktop.mac.sign=true to sign and notarize.
        bundleID = "com.jetbrains.compose.codeviewer"
      }
    }
  }
}

/*
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "lucasalfare.fltimer.desktop.MainKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}
 */