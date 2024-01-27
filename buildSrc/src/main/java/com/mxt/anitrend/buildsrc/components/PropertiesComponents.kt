package com.mxt.anitrend.buildsrc.components

import org.gradle.api.Project
import java.io.File
import java.util.*


enum class PropertyTypes(val key: String) {
    CODE("code"),
    VERSION("version"),
}

class PropertiesReader(project: Project) {
    private val properties = Properties(2)

    init {
        val releaseFile = File(project.rootDir, "gradle/version.properties")
        if (!releaseFile.exists()) {
            project.logger.error("Release file cannot be found in path: $releaseFile")
        }

        properties.apply {
            load(releaseFile.inputStream())
        }
    }

    operator fun get(type: PropertyTypes): String {
        return properties.getProperty(type.key)
            ?: throw IllegalStateException("$type properties were not initialized")
    }
}