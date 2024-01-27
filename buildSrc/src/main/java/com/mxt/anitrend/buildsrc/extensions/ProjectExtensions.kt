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

package com.mxt.anitrend.buildsrc.extensions

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.mxt.anitrend.buildsrc.components.PropertiesReader
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

val Project.props: PropertiesReader
    get() = PropertiesReader(this)

fun Project.versionCatalog() =
    versionCatalogExtension()
        .named("libs")

fun Project.library(alias: String) =
    versionCatalog()
        .findLibrary(alias)
        .get()

fun Project.version(alias: String) =
    versionCatalog()
        .findVersion(alias)
        .get()

internal fun Project.baseExtension() =
    extensions.getByType<BaseExtension>()

internal fun Project.baseAppExtension() =
    extensions.getByType<BaseAppModuleExtension>()

internal fun Project.androidExtensionsExtension() =
    extensions.getByType<AndroidExtensionsExtension>()

internal fun Project.containsAndroidPlugin(): Boolean {
    return project.plugins.toList().any { plugin ->
        plugin is BaseAppModuleExtension
    }
}

internal fun Project.versionCatalogExtension() =
    extensions.getByType<VersionCatalogsExtension>()

internal fun Project.runIfAppModule(body: BaseAppModuleExtension.() -> Unit) {
    if (containsAndroidPlugin())
        body(baseAppExtension())
}
