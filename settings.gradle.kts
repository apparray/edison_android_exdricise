pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
//    versionCatalogs {
//        create("libs") {
//
//            from(file("${rootProject.projectDir}/gradle/libs.versions.toml"))
//        }
//    }
}
rootProject.name = "edison_android_exercise"
enableFeaturePreview("VERSION_CATALOGS")

include(":app")
