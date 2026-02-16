pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
}

rootProject.name = "Smart Go"
include(":app")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:navigation")
include(":core:common")
include(":core:notification")
include(":core:test")
include(":feature:home")
include(":feature:checklist")
include(":feature:done")
include(":feature:items")
include(":feature:settings")

