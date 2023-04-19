rootProject.name = "FLTimer"

sourceControl {
    gitRepository(java.net.URI("https://github.com/LucasAlfare/FLListening.git")) {
        producesModule("com.lucasalfare.fllistening:FLListening")
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)

//        id("com.android.application") version "8.0.0" apply false
//        id("com.android.library") version "8.0.0" apply false
//        id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

include(":core")
include(":android")
include(":desktop")
include(":ui")
include(":console")
