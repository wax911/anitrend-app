/*
 * Copyright (C) 2020  AniTrend
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@file:Suppress("UnstableApiUsage")

package com.mxt.anitrend.buildsrc.components

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.mxt.anitrend.buildsrc.common.Configuration
import com.mxt.anitrend.buildsrc.extensions.*
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.io.FileWriter
import java.util.*

private fun Properties.applyToBuildConfigForBuild(buildType: ApplicationBuildType) {
    forEach { propEntry ->
        val key = propEntry.key as String
        val value = propEntry.value as String
        buildType.buildConfigField("String", key, value)
    }
}

private fun Project.createSigningConfiguration(extension: BaseExtension) {
    val keyStoreFile = project.file(".config/keystore.properties")
    if (keyStoreFile.exists()) {
        keyStoreFile.inputStream().use { fis ->
            val properties = Properties().apply { load(fis) }
            println("${keyStoreFile.name} found, creating signing configuration")
            extension.signingConfigs {
                create("release") {
                    println("Creating signing configuration for $name")
                    storeFile(file(properties["STORE_FILE"] as String))
                    storePassword(properties["STORE_PASSWORD"] as String)
                    keyAlias(properties["STORE_KEY_ALIAS"] as String)
                    keyPassword(properties["STORE_KEY_PASSWORD"] as String)
                }
            }
        }
    }
    else println("${keyStoreFile.absolutePath} could not be found, release may fail")
}

private fun NamedDomainObjectContainer<ApplicationBuildType>.applyConfiguration(project: Project) {
    asMap.forEach { buildTypeEntry ->
        println("Configuring build type -> ${buildTypeEntry.key}")
        val buildType = buildTypeEntry.value

        buildType.buildConfigField("String", "versionName", "\"${project.props[PropertyTypes.VERSION]}\"")
        buildType.buildConfigField("int", "versionCode", project.props[PropertyTypes.CODE])

        val secretsFile = project.file(".config/secrets.properties")
        if (secretsFile.exists())
            secretsFile.inputStream().use { fis ->
                Properties().run {
                    load(fis); applyToBuildConfigForBuild(buildType)
                }
            }
        else println("${secretsFile.absolutePath} could not be found, build may fail")

        val configurationFile = project.file(".config/configuration.properties")
        if (configurationFile.exists())
            configurationFile.inputStream().use { fis ->
                Properties().run {
                    load(fis); applyToBuildConfigForBuild(buildType)
                }
            }
        else println("${configurationFile.absolutePath} could not be found, build may fail")
    }
}

private fun BaseExtension.setUpWith(project: Project) {
    compileSdkVersion(Configuration.compileSdk)
    defaultConfig {
        applicationId = "com.mxt.anitrend"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = project.props[PropertyTypes.CODE].toInt()
        versionName = project.props[PropertyTypes.VERSION]
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }
}

private fun BaseAppModuleExtension.configureBuildFlavours() {
    flavorDimensions.add("version")

    productFlavors {
        create("app") {
            dimension = "version"
            resValue("string", "flavor_description", "Playstore")
        }
        create("github") {
            dimension = "version"
            versionNameSuffix = "-github"
            resValue("string", "flavor_description", "Github")
        }
    }

    applicationVariants.all {
        outputs.map { it as BaseVariantOutputImpl }.forEach { output ->
            val original = output.outputFileName
            val destination = if (output.name != "github-release")
                original.substring(4)
            else original
            println("Configuring build output build -> name: ${output.name} | output: $destination")
            output.outputFileName = destination
        }
    }
}

private fun BaseAppModuleExtension.setUpWith(project: Project) {
    project.createSigningConfiguration(this)
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (project.file(".config/keystore.properties").exists()) {
                println("Applying signing configuration for to build type: $name")
                signingConfig = signingConfigs.getByName("release")
            }
        }

        getByName("debug") {
            isMinifyEnabled = false
        }

        applyConfiguration(project)

        configureBuildFlavours()
    }

    packagingOptions {
        resources.excludes.add("META-INF/NOTICE.*")
        resources.excludes.add("META-INF/LICENSE*")
        // Exclude potential duplicate kotlin_module files
        resources.excludes.add("META-INF/*kotlin_module")
        // Exclude consumer proguard files
        resources.excludes.add("META-INF/proguard/*")
        // Exclude AndroidX version files
        resources.excludes.add("META-INF/*.version")
        // Exclude the Firebase/Fabric/other random properties files
        resources.excludes.add("META-INF/*.properties")
        resources.excludes.add("/*.properties")
        resources.excludes.add("fabric/*.properties")
    }

    sourceSets {
        map { androidSourceSet ->
            androidSourceSet.java.srcDir(
                "src/${androidSourceSet.name}/kotlin"
            )
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    lint {
        abortOnError = false
        ignoreWarnings = false
        ignoreTestSources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

internal fun Project.applyAndroidConfiguration() {
    val baseExtension = baseExtension()
    val appExtension = baseAppExtension()
    val androidExtension = androidExtensionsExtension()

    baseExtension.setUpWith(this)
    appExtension.setUpWith(this)

    androidExtension.isExperimental = true

    configurations.all {
        exclude("org.jetbrains", "annotations-java5")
        resolutionStrategy.force(
            "com.google.code.findbugs:jsr305:3.0.2",
            library("square-okhttp"),
            library("square-logging"),
            library("square-mockServer")
        )
    }

    tasks.withType(KotlinJvmCompile::class.java) {
        kotlinOptions {
            jvmTarget = "11"
        }
    }


    tasks.withType(KotlinCompile::class.java) {
        val compilerArgumentOptions = mutableListOf(
            "-opt-in=kotlin.ExperimentalStdlibApi",
            "-opt-in=kotlin.Experimental",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=org.koin.core.component.KoinExperimentalAPI",
            "-opt-in=org.koin.core.component.KoinApiExtension",
            "-opt-in=org.koin.core.KoinExperimentalAPI"
        )

        kotlinOptions {
            allWarningsAsErrors = false
            // Filter out modules that won't be using coroutines
            freeCompilerArgs = compilerArgumentOptions
        }
    }

    tasks.register("generateVersions") {
        println("Generate versions for $name -> $projectDir")
        val versionMeta = File(projectDir, ".meta/version.json")
        if (!versionMeta.exists()) {
            println("Creating versions meta file in ${versionMeta.absolutePath}")
            versionMeta.mkdirs()
        }
        println("Writing version information to ${versionMeta.absolutePath}")
        FileWriter(versionMeta).use { writer ->
            writer.write(
                """
                    {
                        "code": ${project.props[PropertyTypes.CODE].toInt()},
                        "migration": false,
                        "minSdk": ${Configuration.minSdk},
                        "releaseNotes": "",
                        "version": "${project.props[PropertyTypes.VERSION]}",
                        "appId": "com.mxt.anitrend"
                    }
                """.trimIndent()
            )
        }
    }
}