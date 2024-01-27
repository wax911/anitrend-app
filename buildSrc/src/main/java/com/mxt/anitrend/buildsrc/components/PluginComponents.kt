package com.mxt.anitrend.buildsrc.components

import com.mxt.anitrend.buildsrc.extensions.baseExtension
import org.gradle.api.Project

internal fun Project.configurePlugins() {
    plugins.apply("com.android.application")
    plugins.apply("kotlin-android")
    plugins.apply("kotlinx-serialization")
    plugins.apply("kotlin-android-extensions")
    plugins.apply("kotlin-kapt")
    plugins.apply("io.objectbox")
}

@Suppress("UnstableApiUsage")
internal fun Project.configureAdditionalPlugins() {
    baseExtension().variantFilter {
        if (flavors.first().name == "app") {
            println("Applying additional google plugins on -> variant: $name | type: $buildType")
            if (file("google-services.json").exists()) {
                plugins.apply("com.google.gms.google-services")
                plugins.apply("com.google.firebase.crashlytics")
            } else println("google-services.json cannot be found and will not be using any of the google plugins")
        }
    }
}