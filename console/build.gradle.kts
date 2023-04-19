plugins {
    kotlin("jvm")
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.lucasalfare.fllistening:FLListening") {
        version {
            branch = "master"
        }
    }

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation(project((":core")))
}

//sets a basic .jar task generation for this module
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "lucasalfare.fltimer.console.MainKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}
