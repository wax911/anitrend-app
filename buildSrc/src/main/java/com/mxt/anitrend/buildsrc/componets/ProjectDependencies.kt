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

package com.mxt.anitrend.buildsrc.componets

import org.gradle.api.Project
import com.mxt.anitrend.buildsrc.extensions.*

internal fun Project.configureDependencies() {
    dependencies.add(
        "implementation",
        fileTree("libs") {
            include("*.jar")
        }
    )

    dependencies.implementation(library("jetbrains-kotlin-stdlib-jdk8"))
    dependencies.implementation(library("jetbrains-kotlin-reflect"))
    dependencies.implementation(library("jetbrains-kotlinx-coroutines-core"))
    dependencies.implementation(library("jetbrains-kotlinx-coroutines-android"))
    dependencies.implementation(library("jetbrains-kotlinx-serialization-json"))
    dependencies.testImplementation(library("jetbrains-kotlinx-coroutines-test"))

    /** Material Design */
    dependencies.implementation(library("google-android-material"))
    dependencies.implementation(library("google-android-flexbox"))
    dependencies.implementation(library("google-firebase-analytics"))
    dependencies.implementation(library("google-firebase-crashlytics"))

    /** Architecture Components */
    dependencies.implementation(library("androidx-constraintLayout"))
    dependencies.implementation(library("androidx-collectionKtx"))

    dependencies.implementation(library("androidx-coreKtx"))
    dependencies.implementation(library("androidx-appcompat"))
    dependencies.implementation(library("androidx-appcompat-resources"))
    dependencies.implementation(library("androidx-activityKtx"))
    dependencies.implementation(library("androidx-fragmentKtx"))
    dependencies.implementation(library("androidx-preferenceKtx"))

    dependencies.implementation(library("androidx-work-runtimeKtx"))
    dependencies.implementation(library("androidx-startupRuntime"))

    dependencies.implementation(library("androidx-lifecycle-extensions"))
    dependencies.implementation(library("androidx-lifecycle-runTimeKtx"))
    dependencies.implementation(library("androidx-lifecycle-liveDataKtx"))
    dependencies.implementation(library("androidx-lifecycle-viewModelKtx"))
    dependencies.implementation(library("androidx-lifecycle-liveDataCoreKtx"))

    /** Koin AndroidX Dependency Injection */
    dependencies.implementation (library("koin-core"))
    dependencies.implementation (library("koin-android"))
    dependencies.implementation (library("koin-workManager"))
    dependencies.testImplementation(library("koin-test"))
    dependencies.testImplementation(library("koin-test-jUnit4"))

    /** Glide Libraries */
    dependencies.implementation(library("glide"))
    dependencies.kapt(library("glide-compiler"))

    /** Retrofit Libraries */
    dependencies.implementation(library("square-logging"))
    dependencies.implementation(library("square-retrofit"))
    dependencies.implementation(library("square-converter-gson"))

    /** Rich Text Markdown Parser */
    dependencies.implementation(library("markwon-core"))
    dependencies.implementation(library("markwon-editor"))
    dependencies.implementation(library("markwon-html"))
    dependencies.implementation(library("markwon-image"))
    dependencies.implementation(library("markwon-glide"))
    dependencies.implementation(library("markwon-parser"))
    dependencies.implementation(library("markwon-linkify"))
    dependencies.implementation(library("markwon-simpleExt"))
    dependencies.implementation(library("markwon-syntaxHighlight"))
    dependencies.implementation(library("markwon-ext-taskList"))
    dependencies.implementation(library("markwon-ext-strikeThrough"))
    dependencies.implementation(library("markwon-ext-tables"))
    dependencies.implementation(library("markwon-ext-latex"))
    dependencies.implementation(library("betterLinkMovement"))

    /** Object Box */
    dependencies.implementation(library("objectbox-android"))
    dependencies.kapt(library("objectbox-processor"))

    /** Logging */
    dependencies.implementation(library("timber"))
    dependencies.implementation(library("treessence"))

    /** AniTrend */
    dependencies.implementation(library("anitrend-android-emoji"))
    dependencies.implementation(library("anitrend-support-markwon"))
    dependencies.implementation(library("square-converter-simplexml").get()) {
        exclude("xpp3", "xpp3")
        exclude("stax", "stax-api")
        exclude("stax", "stax")
    }
    dependencies.implementation(library("anitrend-retrofit-graphql"))

    /** Smart Tab Layout */
    dependencies.implementation(library("smarttablayout-library"))
    dependencies.implementation(library("smarttablayout-utils"))

    /** Testing-only dependencies */
    dependencies.testImplementation(library("junit"))
    dependencies.testImplementation(library("hamcrest"))
    dependencies.testImplementation(library("mockito-core"))

    dependencies.androidTestImplementation(library("cash-turbine"))
    dependencies.androidTestImplementation(library("androidx-test-coreKtx"))
    dependencies.androidTestImplementation(library("androidx-test-runner"))
    dependencies.androidTestImplementation(library("androidx-test-rules"))
    dependencies.androidTestImplementation(library("androidx-fragment-test"))
    dependencies.androidTestImplementation(library("androidx-test-espresso-core"))
    dependencies.androidTestImplementation(library("androidx-junitKtx"))
    dependencies.androidTestImplementation(library("mockito-core"))
    dependencies.androidTestImplementation(library("mockk-android"))
    dependencies.androidTestImplementation(library("mockk"))
}