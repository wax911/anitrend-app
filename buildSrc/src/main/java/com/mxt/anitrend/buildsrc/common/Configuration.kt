/*
 * Copyright (C) 2022  AniTrend
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

package com.mxt.anitrend.buildsrc.common

object Configuration {

    private const val major = 1
    private const val minor = 9
    private const val patch = 12
    private const val candidate = 0

    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 17

    private const val channel = "beta"

    /**
     * **RR**_X.Y.Z_
     * > **RR** reserved for build flavours and **X.Y.Z** follow the [versionName] convention
     */
    const val versionCode = major.times(1_000_000_000) +
            minor.times(1_000_000) +
            patch.times(1_000) +
            candidate

    /**
     * Naming schema: X.Y.Z-variant##
     * > **X**(Major).**Y**(Minor).**Z**(Patch)
     */
    val versionName = if (candidate > 0)
        "$major.$minor.$patch-$channel$candidate"
    else
        "$major.$minor.$patch"
}