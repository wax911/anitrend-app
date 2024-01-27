package com.mxt.anitrend.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.mxt.anitrend.BuildConfig
import com.mxt.anitrend.R
import com.mxt.anitrend.util.collection.GenreTagUtil
import com.mxt.anitrend.util.date.DateUtil
import java.util.*

/**
 * Created by max on 2017/09/16.
 * Application preferences
 */

class Settings(
    context: Context,
    private val resources: Resources = context.resources,
    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
) : SharedPreferences by preferences {

    /** Base Application Values  */
    private val _versionCode = "_versionCode"
    private val _freshInstall = "_freshInstall"
    private val _isAuthenticated = "_isAuthenticated"
    private val _lastDismissedNotificationId = "_lastDismissedNotificationId"

    var isAuthenticated: Boolean
        get() = getBoolean(_isAuthenticated, false)
        set(value) {
            edit {
                putBoolean(_isAuthenticated, value)
            }
        }

    var lastDismissedNotificationId: Long
        get() = getLong(_lastDismissedNotificationId, -1)
        set(value) {
            edit {
                putLong(_lastDismissedNotificationId, value)
            }
        }

    @get:KeyUtil.ApplicationTheme
    @set:KeyUtil.ApplicationTheme
    var theme: String = KeyUtil.THEME_LIGHT
        get() = getString(resources.getString(R.string.pref_key_app_theme), KeyUtil.THEME_LIGHT) ?: KeyUtil.THEME_LIGHT
        set(value) {
            field = value
            edit {
                putString(resources.getString(R.string.pref_key_app_theme), value)
            }
        }

    // Returns the IDs of the startup page
    val startupPage: String?
        get() = getString(
        resources.getString(R.string.pref_key_startup_page), "3")

    var isFreshInstall: Boolean = true
        get() = getBoolean(_freshInstall, true)
        set(value) {
            field = value
            edit {
                putBoolean(_freshInstall, field)
            }
        }

    var userLanguage: String? = null
        get() = getString(resources.getString(R.string.pref_key_selected_language),
        Locale.getDefault().language)
        set(value) {
            field = value
            edit {
                putString(resources.getString(R.string.pref_key_selected_language), field)
            }
        }

    var mediaListStyle: Int = 0
        get() = getString(resources.getString(R.string.pref_key_list_view_style), "0")?.toInt() ?: 0
        set(value) {
            field = value
            edit {
                putInt(resources.getString(R.string.pref_key_list_view_style), field)
            }
        }

    //Returns amount of time in seconds
    var syncTime: Int = 15
        get() = getString(
                    resources.getString(R.string.pref_key_sync_frequency), "15")?.toInt() ?: 15
        set(value) {
            field = value
            edit {
                putInt(resources.getString(R.string.pref_key_sync_frequency), field)
            }
        }

    var isNotificationEnabled: Boolean = true
        get() = getBoolean(resources.getString(R.string.pref_key_new_message_notifications), true)
        set(value) {
            field = value
            edit {
                putBoolean(resources.getString(R.string.pref_key_new_message_notifications), field)
            }
        }

    var clearNotificationOnDismiss: Boolean = false
        get() = getBoolean(resources.getString(R.string.pref_key_clear_notification_on_dismiss), false)
        set(value) {
            field = value
            edit {
                putBoolean(resources.getString(R.string.pref_key_clear_notification_on_dismiss), field)
            }
        }

    var isCrashReportsEnabled: Boolean = true
        get() = getBoolean(resources.getString(R.string.pref_key_crash_reports), true)
        set(value) {
            field = value
            edit {
                putBoolean(resources.getString(R.string.pref_key_crash_reports), field)
            }
        }

    var isUsageAnalyticsEnabled: Boolean = false
        get() = getBoolean(resources.getString(R.string.pref_key_usage_analytics), false)
        set(value) {
            field = value
            edit {
                putBoolean(resources.getString(R.string.pref_key_usage_analytics), field)
            }
        }

    var seasonYear: Int = 0
        get() = getInt(KeyUtil.arg_seasonYear, DateUtil.getCurrentYear(1))
        set(value) {
            field = value
            edit {
                putInt(KeyUtil.arg_seasonYear, field)
            }
        }

    var displayAdultContent: Boolean = false
        get() = getBoolean(resources.getString(R.string.pref_key_display_adult_content), false)
        set(value) {
            field = value
            edit {
                putBoolean(resources.getString(R.string.pref_key_display_adult_content), field)
            }
        }

    @set:KeyUtil.SortOrderType
    @get:KeyUtil.SortOrderType
    var sortOrder: String = KeyUtil.DESC
        get() = getString(
                    _sortOrder, KeyUtil.DESC) ?: KeyUtil.DESC
        set(value) {
            field = value
            edit {
                putString(_sortOrder, value)
            }
        }

    @set:KeyUtil.MediaStatus
    @get:KeyUtil.MediaStatus
    var mediaStatus: String?
        get() = getString(_mediaStatus, null)
        set(mediaStatus) {
            edit {
                putString(_mediaStatus, mediaStatus)
            }
        }

    @set:KeyUtil.MediaFormat
    @get:KeyUtil.MediaFormat
    var mediaFormat: String?
        get() = getString(_mediaFormat, null)
        set(mediaFormat) {
            edit {
                putString(_mediaFormat, mediaFormat)
            }
        }

    @set:KeyUtil.AnimeFormat
    @get:KeyUtil.AnimeFormat
    var animeFormat: String?
        get() = getString(_animeFormat, null)
        set(animeFormat) {
            edit {
                putString(_animeFormat, animeFormat)
            }
        }

    @set:KeyUtil.MangaFormat
    @get:KeyUtil.MangaFormat
    var mangaFormat: String?
        get() = getString(_mangaFormat, null)
        set(mangaFormat) {
            edit {
                putString(_mangaFormat, mangaFormat)
            }
        }

    @set:KeyUtil.MediaSource
    @get:KeyUtil.MediaSource
    var mediaSource: String?
        get() = getString(_mediaSource, null)
        set(mediaSource) {
            edit {
                putString(_mediaSource, mediaSource)
            }
        }

    @set:KeyUtil.AiringSort
    @get:KeyUtil.AiringSort
    var airingSort: String?
        get() = getString(_airingSort, KeyUtil.EPISODE)
        set(airingSort) {
            edit {
                putString(_airingSort, airingSort)
            }
        }

    @set:KeyUtil.CharacterSort
    @get:KeyUtil.CharacterSort
    var characterSort: String?
        get() = getString(_characterSort, KeyUtil.ROLE)
        set(characterSort) {
            edit {
                putString(_characterSort, characterSort)
            }
        }

    @set:KeyUtil.MediaListSort
    @get:KeyUtil.MediaListSort
    var mediaListSort: String?
        get() = getString(_mediaListSort, KeyUtil.PROGRESS)
        set(mediaListSort) {
            edit {
                putString(_mediaListSort, mediaListSort)
            }
        }

    @set:KeyUtil.MediaSort
    @get:KeyUtil.MediaSort
    var mediaSort: String?
        get() = getString(_mediaSort, KeyUtil.POPULARITY)
        set(mediaSort) {
            edit {
                putString(_mediaSort, mediaSort)
            }
        }
    @set:KeyUtil.MediaTrendSort
    @get:KeyUtil.MediaTrendSort
    var mediaTrendSort: String?
        get() = getString(_mediaTrendSort, KeyUtil.TRENDING)
        set(mediaTrendSort) {
            edit {
                putString(_mediaTrendSort, mediaTrendSort)
            }
        }

    @set:KeyUtil.ReviewSort
    @get:KeyUtil.ReviewSort
    var reviewSort: String?
        get() = getString(_reviewSort, KeyUtil.ID)
        set(reviewSort) {
            edit {
                putString(_reviewSort, reviewSort)
            }
        }

    @set:KeyUtil.StaffSort
    @get:KeyUtil.StaffSort
    var staffSort: String?
        get() = getString(_staffSort, KeyUtil.ROLE)
        set(staffSort) {
            edit {
                putString(_staffSort, staffSort)
            }
        }

    @set:KeyUtil.Channel
    @get:KeyUtil.Channel
    var updateChannel: String?
        get() = getString(_updateChannel, KeyUtil.STABLE)
        set(channel) {
            edit {
                putString(_updateChannel, channel)
            }
        }

    val isUpdated: Boolean
        get() = versionCode < BuildConfig.versionCode

    var versionCode: Int = 1
        get() = getInt(_versionCode, 1)
        set(value) {
            field = value
            edit {
                putInt(_versionCode, value)
            }
        }

    var selectedGenres: Map<Int, String>?
        get() {
            val selected = getString(_genreFilter, null)
            return GenreTagUtil().convertToEntity(selected)
        }
        set(selectedIndices) {
            val selected = GenreTagUtil()
                    .convertToJson(selectedIndices)
            edit {
                putString(_genreFilter, selected)
            }
        }

    var selectedTags: Map<Int, String>?
        get() {
            val selected = getString(_tagFilter, null)
            return GenreTagUtil().convertToEntity(selected)
        }
        set(selectedIndices) {
            val selected = GenreTagUtil()
                    .convertToJson(selectedIndices)
            edit {
                putString(_tagFilter, selected)
            }
        }

    var experimentalMarkdown: Boolean
        get() = getBoolean(_experimentalMarkdown, false)
        set(enabled) {
            edit {
                putBoolean(_experimentalMarkdown, enabled)
            }
        }

    fun saveSeasonYear(year: Int) {
        edit {
            putInt(KeyUtil.arg_seasonYear, year)
        }
    }


    fun shouldShowTipFor(@KeyUtil.TapTargetType tipType: String): Boolean {
        return getBoolean(tipType, true)
    }

    fun disableTipFor(@KeyUtil.TapTargetType tipType: String) {
        edit {
            putBoolean(tipType, false)
        }
    }

    fun saveSortOrder(@KeyUtil.SortOrderType sortOrder: String) {
        edit {
            putString(_sortOrder, sortOrder)
        }
    }

    fun setUpdated() {
        edit {
            putInt(_versionCode, BuildConfig.versionCode)
        }
    }

    companion object {

        const val _updateChannel = "_updateChannel"
        const val _appTheme = "application_theme"

        private const val _genreFilter = "_genreFilter"
        private const val _tagFilter = "_tagFilter"
        private const val _sortOrder = "_sortOrder"
        private const val _mediaStatus = "_mediaStatus"
        private const val _mediaFormat = "_mediaFormat"
        private const val _animeFormat = "_animeFormat"
        private const val _mangaFormat = "_mangaFormat"
        private const val _mediaSource = "_mediaSource"
        private const val _airingSort = "_airingSort"
        private const val _characterSort = "_characterSort"
        const val _mediaListSort = "_mediaListSort"
        private const val _mediaSort = "_mediaSort"
        private const val _mediaTrendSort = "_mediaTrendSort"
        private const val _reviewSort = "_reviewSort"
        private const val _staffSort = "_staffSort"
        private const val _experimentalMarkdown = "_experimentalMarkdown"
    }
}
