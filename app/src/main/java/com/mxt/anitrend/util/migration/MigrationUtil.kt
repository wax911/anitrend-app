package com.mxt.anitrend.util.migration

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.mxt.anitrend.BuildConfig
import com.mxt.anitrend.util.Settings
import com.mxt.anitrend.util.migration.contract.IMigrationUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class MigrationUtil private constructor(
    private val context: Context,
    private val migrations: List<Migration>
) : IMigrationUtil, KoinComponent {

    private val settings by inject<Settings>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getMigrationStrategies(): List<Migration> {
        val currentVersion = settings.versionCode
        val minMigrations = migrations.filter { migration ->
            IntRange(
                    migration.startVersion,
                    migration.endVersion
            ).contains(currentVersion)
        }
        val maxMigrations = migrations.filter { migration ->
            IntRange(
                    migration.startVersion,
                    migration.endVersion
            ).contains(BuildConfig.versionCode)
        }

        return minMigrations + maxMigrations
    }

    /**
     * Applies migration of the application if necessary
     */
    override fun applyMigration(): Boolean {
        if (settings.isUpdated) {
            Timber.d("Application has been updated: from ${settings.versionCode} - ${BuildConfig.versionCode}, checking for migration scripts")
            val strategies= getMigrationStrategies()
            if (strategies.isNotEmpty())
                return try {
                    strategies.forEach { strategy ->
                        strategy.applyMigration(context, settings)
                    }
                    true
                } catch (ex: Exception) {
                    Timber.e(ex)
                    false
                }
        }
        Timber.d("No migrations to run for this version of the application")
        return true
    }

    class Builder {
        private val migrations: MutableList<Migration> = ArrayList()

        fun addMigration(migration: Migration): Builder {
            if (!migrations.contains(migration))
                migrations.add(migration)
            return this
        }

        fun build(context: Context): MigrationUtil {
            return MigrationUtil(context, migrations)
        }
    }
}