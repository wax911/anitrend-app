package com.mxt.anitrend.util;

import androidx.annotation.IntDef;
import androidx.annotation.LongDef;
import androidx.annotation.StringDef;

/**
 * Created by max on 2017/09/16.
 * Key values to be used throughout the application
 */

public interface KeyUtil {

    /** Default Values */
    float AspectRatio = 1.37f;
    float WideAspectRatio = 0.95f;
    float PEEK_HEIGHT = 200f;
    int PAGING_LIMIT = 21, GLIDE_REQUEST_TIMEOUT = 10000, SINGLE_ITEM_LIMIT = 1;

    /** Notification Actions */
    String NOTIFICATION_ID = "anitrend_notification_id";
    String NOTIFICATION_ID_REMOTE = "anitrend_notification_id_remote";
    String NOTIFICATION_ACTION = "anitrend_notification_action";
    String NOTIFICATION_ACTION_CLEAR = "anitrend_notification_action_clear";
    String NOTIFICATION_ACTION_DISMISS = "anitrend_notification_action_dismiss";

    /** Notification Channels */
    String CHANNEL_ID = "anitrend_app";
    String CHANNEL_TITLE = "AniTrend Notifications";

    /** Work Manager Ids & Keys */
    String WorkNotificationTag = "anitrend_notification_job";
    String WorkNotificationId = "periodic_notification_sync";

    String WorkTagSyncId = "periodic_tag_sync";
    String WorkGenreSyncId = "periodic_genre_sync";
    String WorkUpdaterId = "one_time_updater";
    String WorkUpdaterSilentId = "silent";

    String WorkClearNotificationTag = "anitrend_clear_notification_job";
    String WorkClearNotificationId = "clear_notification_sync";

    String WorkAuthenticatorTag = "anitrend_notification_job";
    String WorkAuthenticatorId = "one_notification_sync";


    String THEME_LIGHT = "light", THEME_DARK = "dark", THEME_BLACK = "black";

    @StringDef({THEME_LIGHT, THEME_DARK, THEME_BLACK})
    @interface ApplicationTheme {}

    // ------------------------------------------------------------------------------------
    // GraphQL Variable Params Keys
    // ------------------------------------------------------------------------------------

    String arg_graph_params = "arg_graph_params";
    String arg_media_trailer = "arg_media_trailer";

    String arg_id = "id";
    String arg_type = "type";
    String arg_sort = "sort";
    String arg_status = "status";
    String arg_format = "format";
    String arg_page = "page";
    String arg_page_limit = "perPage";

    String arg_userId = "userId";
    String arg_recipientId = "recipientId";
    String arg_messengerId = "messengerId";

    String arg_text = "text";
    String arg_message = "message";
    String arg_asHtml = "asHtml";
    String arg_isMixed = "isMixed";
    String arg_isFollowing = "isFollowing";

    String arg_activityId = "activityId";

    String arg_mediaId = "mediaId";
    String arg_animeId = "animeId";
    String arg_mangaId = "mangaId";
    String arg_staffId = "staffId";
    String arg_studioId = "studioId";
    String arg_characterId = "characterId";

    String arg_mediaType = "type";
    String arg_search = "search";

    String arg_userName = "userName";

    /** Media List Keys */
    String arg_listStatus = "status";
    String arg_listScore= "score";
    String arg_listScore_raw = "scoreRaw";
    String arg_listProgress = "progress";
    String arg_listProgressVolumes = "progressVolumes";
    String arg_listRepeat = "repeat";
    String arg_listPriority = "priority";
    String arg_listPrivate = "private";
    String arg_listNotes = "notes";
    String arg_listHiddenFromStatusLists = "hiddenFromStatusLists";
    String arg_listAdvancedScore = "advancedScores";
    String arg_listCustom = "customLists";
    String arg_startedAt = "startedAt";
    String arg_completedAt = "completedAt";

    /** Media Browse Keys */
    String arg_startDateLike = "startDateLike";
    String arg_endDateLike = "endDateLike";
    String arg_season = "season";
    String arg_seasonYear = "seasonYear";
    String arg_genres = "genres";
    String arg_genresInclude = "genresInclude";
    String arg_genresExclude = "genresExclude";
    String arg_isAdult = "isAdult";
    String arg_onList = "onList";
    String arg_tags = "tags";
    String arg_tagsInclude = "tagsInclude";
    String arg_tagsExclude = "tagsExclude";

    /** Media Collection Keys */
    String arg_forceSingleCompletedList = "forceSingleCompletedList";
    String arg_scoreFormat = "scoreFormat";
    String arg_statusIn = "statusIn";

    /** Review Keys */
    String arg_rating = "rating";

    /** Notification Keys */
    String arg_resetNotificationCount = "resetNotificationCount";

    // ------------------------------------------------------------------------------------


    /** Base Application Args */
    String arg_feed = "arg_feed";
    String arg_title = "arg_title";
    String arg_model = "arg_model";
    String arg_popular = "arg_popular";
    String arg_redirect = "arg_redirect";
    String arg_user_model = "arg_user_model";
    String arg_list_model = "arg_list_model";
    String arg_branch_name = "arg_branch_name";
    String arg_page_offset = "arg_page_offset";
    String arg_request_type = "arg_request_type";
    String arg_activity_tag = "arg_activity_tag";
    String arg_message_type = "arg_message_type";
    String arg_shortcut_used = "arg_shortcut_used";
    String arg_deep_link_type = "arg_deep_link_type";
    String arg_exception_error = "arg_exception_error";

    String arg_uri_error = "error";
    String arg_uri_error_description = "error_description";

    String arg_media_util = "arg_media_util";

    String arg_positive_text = "arg_positive_text";
    String arg_negative_text = "arg_negative_text";

    /** Application State Keys */

    String arg_order = "order";
    String key_recycler_state = "key_recycler_state";
    String key_model_state = "key_model_state";
    String key_pagination = "key_pagination";
    String key_columns = "key_columns";
    String key_navigation_selected = "key_navigation_selected";
    String key_navigation_title = "key_navigation_title";
    String key_bundle_param = "key_bundle_param";



    // ------------------------------------------------------------------------------------
    // Application Request Types
    // ------------------------------------------------------------------------------------

    // Base Model Requests
    int GENRE_COLLECTION_REQ = 1, MEDIA_TAG_REQ = 2,

    // None AniList affiliated Request Types
    EPISODE_LATEST_REQ = 3, EPISODE_POPULAR_REQ = 4, EPISODE_FEED_REQ = 5, UPDATE_CHECKER_REQ = 6, GIPHY_TRENDING_REQ = 7, GIPHY_SEARCH_REQ = 8,

    // Browse Model Requests
    MEDIA_LIST_COLLECTION_REQ = 68, MEDIA_BROWSE_REQ = 10, MEDIA_LIST_BROWSE_REQ = 11, MEDIA_LIST_REQ = 12, MEDIA_WITH_LIST_REQ = 67, MEDIA_REVIEWS_REQ = 13,

    // Character Model Requests
    CHARACTER_BASE_REQ = 14, CHARACTER_OVERVIEW_REQ = 15, CHARACTER_MEDIA_REQ = 16, CHARACTER_ACTORS_REQ =17,

    // Feed Model Requests
    FEED_LIST_REQ = 18, FEED_LIST_REPLY_REQ = 19, FEED_MESSAGE_REQ = 20,

    // Media Model Requests
    MEDIA_BASE_REQ = 21, MEDIA_OVERVIEW_REQ = 22, MEDIA_RELATION_REQ = 23, MEDIA_STATS_REQ = 24, MEDIA_EPISODES_REQ = 25,
    MEDIA_CHARACTERS_REQ = 26, MEDIA_STAFF_REQ = 27, MEDIA_SOCIAL_REQ = 28, MEDIA_RECOMMENDATION_REQ = 69,

    // Recommendation Model Requests
    RECOMMENDATIONS_REQ = 70,

    // Search Model Requests
    MEDIA_SEARCH_REQ = 29, STUDIO_SEARCH_REQ = 30, STAFF_SEARCH_REQ = 31, CHARACTER_SEARCH_REQ = 32, USER_SEARCH_REQ = 33,

    // Staff Model Requests
    STAFF_BASE_REQ = 34, STAFF_OVERVIEW_REQ = 35, STAFF_CHARACTERS_REQ = 72, STAFF_MEDIA_REQ = 36, STAFF_ROLES_REQ = 37,

    // Studio Model Requests
    STUDIO_BASE_REQ = 38, STUDIO_MEDIA_REQ = 39,

    // User Model Requests
    USER_FAVOURITES_COUNT_REQ = 40, USER_ANIME_FAVOURITES_REQ = 41, USER_MANGA_FAVOURITES_REQ = 42, USER_CHARACTER_FAVOURITES_REQ = 43,
    USER_STAFF_FAVOURITES_REQ = 44, USER_STUDIO_FAVOURITES_REQ = 45, USER_CURRENT_REQ = 46, USER_BASE_REQ = 47, USER_STATS_REQ = 48,
    USER_OVERVIEW_REQ = 49, USER_FOLLOWING_REQ = 50, USER_FOLLOWERS_REQ = 51, USER_NOTIFICATION_REQ = 66,

    // Mutation Requests
    MUT_TOGGLE_LIKE = 52, MUT_TOGGLE_FAVOURITE = 53, MUT_SAVE_MEDIA_LIST = 54, MUT_UPDATE_MEDIA_LISTS = 55, MUT_DELETE_MEDIA_LIST = 56,
    MUT_RATE_REVIEW = 57, MUT_SAVE_REVIEW = 58, MUT_DELETE_REVIEW = 59, MUT_TOGGLE_FOLLOW = 60, MUT_SAVE_TEXT_FEED = 61,
    MUT_SAVE_MESSAGE_FEED = 62, MUT_SAVE_FEED_REPLY = 63, MUT_DELETE_FEED = 64, MUT_DELETE_FEED_REPLY = 65, MUT_RATE_RECOMMENDATION = 71;

    @IntDef({GENRE_COLLECTION_REQ, MEDIA_TAG_REQ,

            EPISODE_LATEST_REQ, EPISODE_POPULAR_REQ, EPISODE_FEED_REQ, UPDATE_CHECKER_REQ, GIPHY_TRENDING_REQ, GIPHY_SEARCH_REQ,

            MEDIA_LIST_COLLECTION_REQ, MEDIA_BROWSE_REQ, MEDIA_LIST_BROWSE_REQ, MEDIA_LIST_REQ, MEDIA_WITH_LIST_REQ, MEDIA_REVIEWS_REQ,

            CHARACTER_BASE_REQ, CHARACTER_OVERVIEW_REQ, CHARACTER_MEDIA_REQ, CHARACTER_ACTORS_REQ,

            FEED_LIST_REQ, FEED_LIST_REPLY_REQ, FEED_MESSAGE_REQ,

            MEDIA_BASE_REQ, MEDIA_OVERVIEW_REQ, MEDIA_RELATION_REQ, MEDIA_STATS_REQ, MEDIA_EPISODES_REQ,
            MEDIA_CHARACTERS_REQ, MEDIA_STAFF_REQ, MEDIA_SOCIAL_REQ,

            MEDIA_SEARCH_REQ, STUDIO_SEARCH_REQ, STAFF_SEARCH_REQ, CHARACTER_SEARCH_REQ, USER_SEARCH_REQ,

            STAFF_BASE_REQ, STAFF_OVERVIEW_REQ, STAFF_CHARACTERS_REQ, STAFF_MEDIA_REQ, STAFF_ROLES_REQ,

            STUDIO_BASE_REQ, STUDIO_MEDIA_REQ,

            USER_FAVOURITES_COUNT_REQ, USER_ANIME_FAVOURITES_REQ, USER_MANGA_FAVOURITES_REQ, USER_CHARACTER_FAVOURITES_REQ,
            USER_STAFF_FAVOURITES_REQ, USER_STUDIO_FAVOURITES_REQ, USER_CURRENT_REQ, USER_BASE_REQ, USER_STATS_REQ,
            USER_OVERVIEW_REQ, USER_FOLLOWING_REQ, USER_FOLLOWERS_REQ, USER_NOTIFICATION_REQ,

            MUT_TOGGLE_LIKE, MUT_TOGGLE_FAVOURITE, MUT_SAVE_MEDIA_LIST, MUT_UPDATE_MEDIA_LISTS, MUT_DELETE_MEDIA_LIST,
            MUT_RATE_REVIEW, MUT_SAVE_REVIEW, MUT_DELETE_REVIEW, MUT_TOGGLE_FOLLOW, MUT_SAVE_TEXT_FEED,
            MUT_SAVE_MESSAGE_FEED, MUT_SAVE_FEED_REPLY, MUT_DELETE_FEED, MUT_DELETE_FEED_REPLY,

            MEDIA_RECOMMENDATION_REQ, RECOMMENDATIONS_REQ, MUT_RATE_RECOMMENDATION
    })
    @interface RequestType {}

    // ------------------------------------------------------------------------------------




    // Deep link types
    String DEEP_LINK_USER = "user", DEEP_LINK_MANGA = "manga", DEEP_LINK_ANIME = "anime",
            DEEP_LINK_CHARACTER = "character", DEEP_LINK_STAFF = "staff", DEEP_LINK_ACTOR = "actor",
            DEEP_LINK_STUDIO = "studio", DEEP_LINK_ACTIVITY = "activity";

    @StringDef({ DEEP_LINK_USER, DEEP_LINK_MANGA, DEEP_LINK_ANIME, DEEP_LINK_CHARACTER,
            DEEP_LINK_STAFF, DEEP_LINK_ACTOR, DEEP_LINK_STUDIO, DEEP_LINK_ACTIVITY })
    @interface DeepLinkType {}


    String MD_BOLD = "__", MD_ITALIC = "_", MD_STRIKE = "~~",
            MD_NUMBER = "1. ", MD_BULLET = "- ", MD_HEADING = "# ",
            MD_CENTER_ALIGN = "~~~", MD_QUOTE = "> " ,MD_CODE = "`";

    // Share types
    int PLAIN_TYPE = 0, LINK_TYPE = 1, IMAGE_TYPE = 2, YOUTUBE_TYPE = 3, WEBM_TYPE = 4;

    String[] ShareTypes = {"plain_text", "link", "image", "youtube", "webm"};
    @IntDef({PLAIN_TYPE, LINK_TYPE, IMAGE_TYPE, YOUTUBE_TYPE, WEBM_TYPE})
    @interface ShareType {}

    // Hub request types
    int FEED_TYPE = 0, RSS_TYPE = 1, PLAYLIST_TYPE = 2, VIDEO_TYPE = 3, PLAYLIST_COLLECTION = 4;

    @IntDef({FEED_TYPE, RSS_TYPE, PLAYLIST_TYPE, VIDEO_TYPE, PLAYLIST_COLLECTION})
    @interface HubType {}

    // Token grant types
    String AUTHENTICATION_TYPE = "client_credentials", AUTHENTICATION_CODE = "authorization_code", REFRESH_TYPE = "refresh_token";
    @StringDef({AUTHENTICATION_TYPE, AUTHENTICATION_CODE, REFRESH_TYPE})
    @interface GrantType {}

    // Language Title Preference
    String ROMAJI = "ROMAJI", ENGLISH = "ENGLISH", NATIVE = "NATIVE",
            ROMAJI_STYLISED = "ROMAJI_STYLISED", ENGLISH_STYLISED = "ENGLISH_STYLISED",
            NATIVE_STYLISED = "NATIVE_STYLISED";

    @StringDef({ROMAJI, ENGLISH, NATIVE, ROMAJI_STYLISED, ENGLISH_STYLISED, NATIVE_STYLISED})
    @interface UserLanguageTitle {}


    // ------------------------------------------------------------------------------------
    // Sorting & Order Type Attributes
    // ------------------------------------------------------------------------------------

    String ID = "ID", MEDIA_ID = "MEDIA_ID", SCORE = "SCORE", STATUS = "STATUS", SEARCH_MATCH = "SEARCH_MATCH", ROLE = "ROLE", LANGUAGE = "LANGUAGE",
            DATE = "DATE", POPULARITY = "POPULARITY", TRENDING = "TRENDING", EPISODE = "EPISODE";


    String ASC = "", DESC = "_DESC";
    @StringDef({ASC, DESC})
    @interface SortOrderType {}

    String TITLE_ROMAJI = "TITLE_ROMAJI", TITLE_ENGLISH = "TITLE_ENGLISH", TITLE_NATIVE = "TITLE_NATIVE", TYPE = "TYPE",
            FORMAT = "FORMAT", START_DATE = "START_DATE", END_DATE = "END_DATE", EPISODES = "EPISODES", DURATION = "DURATION", CHAPTERS = "CHAPTERS", VOLUMES = "VOLUMES";
    @StringDef({ID, TITLE_ROMAJI, TITLE_ENGLISH, TITLE_NATIVE, TYPE, FORMAT, START_DATE, END_DATE, SCORE, POPULARITY, TRENDING, EPISODES,
            DURATION, STATUS, CHAPTERS, VOLUMES})
    @interface MediaSort {}

    @StringDef({ID, MEDIA_ID, DATE, SCORE, POPULARITY, TRENDING, EPISODE})
    @interface MediaTrendSort {}


    String TITLE = "TITLE", PROGRESS = "PROGRESS",PROGRESS_VOLUMES = "PROGRESS_VOLUMES", REPEAT = "REPEAT", PRIORITY = "PRIORITY", STARTED_ON = "STARTED_ON",
            FINISHED_ON = "FINISHED_ON", ADDED_TIME = "ADDED_TIME", UPDATED_TIME = "UPDATED_TIME";
    @StringDef({TITLE, MEDIA_ID, SCORE, STATUS, PROGRESS, PROGRESS_VOLUMES, REPEAT, PRIORITY, STARTED_ON, FINISHED_ON, ADDED_TIME, UPDATED_TIME})
    @interface MediaListSort {}


    String RATING = "RATING", CREATED_AT = "CREATED_AT", UPDATED_AT = "UPDATED_AT";
    @StringDef({ID, SCORE, RATING, CREATED_AT, UPDATED_AT})
    @interface ReviewSort {}


    String TIME = "TIME";
    @StringDef({ID, MEDIA_ID, TIME, EPISODE})
    @interface AiringSort {}


    @StringDef({ID, ROLE, SEARCH_MATCH})
    @interface CharacterSort {}


    @StringDef({ID, ROLE, LANGUAGE, SEARCH_MATCH})
    @interface StaffSort {}


    String[] SortOrderType = {ASC, DESC};
    String[] MediaSortType = {ID, TITLE_ROMAJI, TITLE_ENGLISH, TITLE_NATIVE, TYPE, FORMAT, START_DATE, END_DATE, SCORE, POPULARITY, TRENDING, EPISODES, DURATION, STATUS, CHAPTERS, VOLUMES};
    // String[] MediaTrendSortType = {ID, MEDIA_ID, DATE, SCORE, POPULARITY, TRENDING, EPISODE};
    String[] MediaListSortType = {TITLE ,MEDIA_ID, SCORE, STATUS, PROGRESS, PROGRESS_VOLUMES, REPEAT, PRIORITY, STARTED_ON, FINISHED_ON, ADDED_TIME, UPDATED_TIME};
    String[] ReviewSortType = {ID, SCORE, RATING, CREATED_AT, UPDATED_AT};
    // String[] AiringSortType = {ID, MEDIA_ID, TIME, EPISODE};
    // String[] CharacterSortType = {ID, ROLE, SEARCH_MATCH};
    // String[] StaffSortType = {ID, ROLE, LANGUAGE, SEARCH_MATCH};

    int LIST_VIEW_STYLE_DEFAULT = 0,
            LIST_VIEW_STYLE_COMPACT_X1 = 1,
            LIST_VIEW_STYLE_COMPACT_X2 = 2;

    // ------------------------------------------------------------------------------------




    // ------------------------------------------------------------------------------------
    // Media Type Attributes
    // ------------------------------------------------------------------------------------

    String ANIME = "ANIME", MANGA = "MANGA";
    @StringDef({ANIME, MANGA})
    @interface MediaType {}


    String WINTER = "WINTER", SPRING = "SPRING", SUMMER = "SUMMER", FALL = "FALL";
    @StringDef({WINTER, SPRING, SUMMER, FALL})
    @interface MediaSeason {}

    String CANCELLED = "CANCELLED", FINISHED = "FINISHED", HIATUS = "HIATUS",
            NOT_YET_RELEASED = "NOT_YET_RELEASED", RELEASING = "RELEASING";
    @StringDef({CANCELLED, FINISHED, HIATUS, NOT_YET_RELEASED, RELEASING})
    @interface MediaStatus {}


    String DOUJINSHI = "DOUJINSHI", LIGHT_NOVEL = "LIGHT_NOVEL", NOVEL = "NOVEL",
            ORIGINAL = "ORIGINAL", OTHER = "OTHER", VIDEO_GAME = "VIDEO_GAME",
            VISUAL_NOVEL = "VISUAL_NOVEL", WEB_NOVEL = "WEB_NOVEL",
            LIVE_ACTION = "LIVE_ACTION", GAME = "GAME", COMIC = "COMIC",
            MULTIMEDIA_PROJECT = "MULTIMEDIA_PROJECT", PICTURE_BOOK = "PICTURE_BOOK";
    @StringDef({
            ANIME, DOUJINSHI, LIGHT_NOVEL, MANGA, NOVEL, ORIGINAL, OTHER, VIDEO_GAME, VISUAL_NOVEL,
            WEB_NOVEL, LIVE_ACTION, GAME, COMIC, MULTIMEDIA_PROJECT, PICTURE_BOOK
    })
    @interface MediaSource {}


    String TV = "TV", TV_SHORT = "TV_SHORT", MOVIE = "MOVIE", SPECIAL = "SPECIAL",
            OVA = "OVA", ONA = "ONA", MUSIC = "MUSIC", ONE_SHOT = "ONE_SHOT";
    @StringDef({TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC, MANGA, NOVEL, ONE_SHOT})
    @interface MediaFormat {}

    @StringDef({TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC})
    @interface AnimeFormat {}

    @StringDef({MANGA, NOVEL, ONE_SHOT})
    @interface MangaFormat {}


    String RATED = "RATED", POPULAR = "POPULAR";
    @StringDef({RATED, POPULAR})
    @interface MediaRankType {}


    String[] MediaSeason = {WINTER, SPRING, SUMMER, FALL};
    String[] MediaStatus = {null, FINISHED, RELEASING, NOT_YET_RELEASED, CANCELLED};
    String[] MediaSource = {null, ORIGINAL, MANGA, LIGHT_NOVEL, VISUAL_NOVEL, VIDEO_GAME, OTHER};
    String[] MediaFormat = {null, TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC, MANGA, NOVEL, ONE_SHOT};
    String[] AnimeFormat = {null, TV, TV_SHORT, MOVIE, SPECIAL, OVA, ONA, MUSIC};
    String[] MangaFormat = {null, MANGA, NOVEL, ONE_SHOT};
    String[] MediaRankType = {RATED, POPULAR};

    // ------------------------------------------------------------------------------------




    // ------------------------------------------------------------------------------------
    // MediaList Type Attributes
    // ------------------------------------------------------------------------------------

    String CURRENT = "CURRENT", PLANNING = "PLANNING", COMPLETED = "COMPLETED",
            DROPPED = "DROPPED", PAUSED = "PAUSED", REPEATING = "REPEATING";
    @StringDef({CURRENT, PLANNING, COMPLETED, DROPPED, PAUSED, REPEATING})
    @interface MediaListStatus {}


    String POINT_100 = "POINT_100", POINT_10_DECIMAL = "POINT_10_DECIMAL", POINT_10 = "POINT_10",
            POINT_5 = "POINT_5", POINT_3 = "POINT_3";
    @StringDef({POINT_100, POINT_10_DECIMAL, POINT_10,  POINT_5, POINT_3})
    @interface ScoreFormat {}

    String[] MediaListStatus = {CURRENT, PLANNING, COMPLETED, DROPPED, PAUSED, REPEATING};
    String[] ScoreFormat = {POINT_100, POINT_10_DECIMAL, POINT_10,  POINT_5, POINT_3};

    // ------------------------------------------------------------------------------------




    // ------------------------------------------------------------------------------------
    // Feed Type Attributes
    // ------------------------------------------------------------------------------------

    String TEXT = "TEXT", ANIME_LIST = "ANIME_LIST", MANGA_LIST = "MANGA_LIST", MESSAGE = "MESSAGE", MEDIA_LIST = "MEDIA_LIST";
    @StringDef({TEXT, ANIME_LIST, MANGA_LIST, MESSAGE, MEDIA_LIST})
    @interface FeedType {}

    String THREAD = "THREAD", THREAD_COMMENT = "THREAD_COMMENT", ACTIVITY = "ACTIVITY", ACTIVITY_REPLY = "ACTIVITY_REPLY";
    @StringDef({THREAD, THREAD_COMMENT, ACTIVITY, ACTIVITY_REPLY})
    @interface LikeType {}

    // ------------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------------
    // Review Type Attributes
    // ------------------------------------------------------------------------------------

    String NO_VOTE = "NO_VOTE", UP_VOTE = "UP_VOTE", DOWN_VOTE = "DOWN_VOTE";
    @StringDef({NO_VOTE, UP_VOTE, DOWN_VOTE})
    @interface ReviewRating {}

    // ------------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------------
    // Notification Type Attributes
    // ------------------------------------------------------------------------------------

    String ACTIVITY_MESSAGE = "ACTIVITY_MESSAGE", FOLLOWING = "FOLLOWING", ACTIVITY_MENTION = "ACTIVITY_MENTION",
            ACTIVITY_REPLY_SUBSCRIBED = "ACTIVITY_REPLY_SUBSCRIBED", RELATED_MEDIA_ADDITION = "RELATED_MEDIA_ADDITION",
            THREAD_COMMENT_MENTION = "THREAD_COMMENT_MENTION", THREAD_SUBSCRIBED = "THREAD_SUBSCRIBED",
            THREAD_COMMENT_REPLY = "THREAD_COMMENT_REPLY", AIRING = "AIRING", ACTIVITY_LIKE = "ACTIVITY_LIKE",
            ACTIVITY_REPLY_LIKE = "ACTIVITY_REPLY_LIKE", THREAD_LIKE = "THREAD_LIKE", THREAD_COMMENT_LIKE = "THREAD_COMMENT_LIKE",
            MEDIA_DATA_CHANGE = "MEDIA_DATA_CHANGE", MEDIA_DELETION = "MEDIA_DELETION", MEDIA_MERGE = "MEDIA_MERGE";

    @StringDef({ACTIVITY_MESSAGE, ACTIVITY_REPLY, ACTIVITY_REPLY_SUBSCRIBED, FOLLOWING,  ACTIVITY_MENTION,
            THREAD_COMMENT_MENTION, THREAD_SUBSCRIBED, THREAD_COMMENT_REPLY, RELATED_MEDIA_ADDITION,
            AIRING, ACTIVITY_LIKE, ACTIVITY_REPLY_LIKE, THREAD_LIKE, THREAD_COMMENT_LIKE,
            MEDIA_DATA_CHANGE, MEDIA_DELETION, MEDIA_MERGE
    })
    @interface NotificationType {}

    // ------------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------------
    // Edge Type Attributes
    // ------------------------------------------------------------------------------------

    String MAIN = "MAIN", SUPPORTING = "SUPPORTING", BACKGROUND = "BACKGROUND";

    @StringDef({MAIN, SUPPORTING, BACKGROUND})
    @interface CharacterRole {}

    String ADAPTATION = "ADAPTATION", ALTERNATIVE = "ALTERNATIVE", CHARACTER = "CHARACTER",
            COMPILATION = "COMPILATION", CONTAINS = "CONTAINS", PARENT = "PARENT",
            PREQUEL = "PREQUEL", SEQUEL = "SEQUEL", SIDE_STORY = "SIDE_STORY", SOURCE = "SOURCE",
            SPIN_OFF = "SPIN_OFF", SUMMARY = "SUMMARY";

    @StringDef({ADAPTATION, ALTERNATIVE, CHARACTER, COMPILATION, CONTAINS, OTHER, PARENT, PREQUEL, SEQUEL, SIDE_STORY, SOURCE, SPIN_OFF, SUMMARY})
    @interface MediaRelation {}

    // ------------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------------
    // Profile Colors
    // ------------------------------------------------------------------------------------

    String BLUE = "blue", PURPLE = "purple", PINK = "pink", ORANGE = "orange",
            RED = "red", GREEN = "green", GREY = "gray";

    @StringDef({BLUE, PURPLE, PINK, ORANGE, RED, GREEN, GREY})
    @interface ProfileColor {}

    // ------------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------------
    // Update Channels
    // ------------------------------------------------------------------------------------

    String STABLE = "master", BETA = "develop";

    @StringDef({STABLE, BETA})
    @interface Channel {}

    // ------------------------------------------------------------------------------------



    /** Alerter Durations */
    long DURATION_SHORT = 2000L, DURATION_MEDIUM = 3500L, DURATION_LONG = 6500L;

    @LongDef({DURATION_SHORT, DURATION_MEDIUM, DURATION_LONG})
    @interface AlerterDuration {}


    int SHORTCUT_SEARCH = 0, SHORTCUT_NOTIFICATION = 1, SHORTCUT_AIRING = 2, SHORTCUT_TRENDING = 3, SHORTCUT_MY_ANIME = 4,
            SHORTCUT_MY_MANGA = 5, SHORTCUT_FEEDS = 6, SHORTCUT_PROFILE = 7;

    String[] ShortcutTypes = {"SHORTCUT_SEARCH", "SHORTCUT_NOTIFICATION", "SHORTCUT_AIRING", "SHORTCUT_TRENDING",
            "SHORTCUT_ANIME", "SHORTCUT_MANGA", "SHORTCUT_FEEDS", "SHORTCUT_PROFILE"};
    @IntDef({SHORTCUT_SEARCH, SHORTCUT_NOTIFICATION, SHORTCUT_AIRING, SHORTCUT_TRENDING, SHORTCUT_MY_ANIME, SHORTCUT_MY_MANGA,
            SHORTCUT_FEEDS, SHORTCUT_PROFILE})
    @interface ShortcutType {}


    String GIPHY_LARGE_DOWN_SAMPLE = "downsized_large", GIPHY_ORIGINAL_STILL = "original_still",
            GIPHY_ORIGINAL_ANIMATED = "original", GIPHY_PREVIEW = "preview_gif";
    @StringDef({GIPHY_LARGE_DOWN_SAMPLE, GIPHY_ORIGINAL_STILL, GIPHY_ORIGINAL_ANIMATED, GIPHY_PREVIEW})
    @interface GiphyType {}


    // Time unit conversion place identifiers
    int TIME_UNIT_DAYS = 0, TIME_UNIT_HOURS = 1, TIME_UNIT_MINUTES = 2, TIME_UNITS_SECONDS = 3;

    @IntDef({TIME_UNIT_DAYS, TIME_UNIT_HOURS, TIME_UNIT_MINUTES, TIME_UNITS_SECONDS})
    @interface TimeTargetType {}

    int MESSAGE_TYPE_INBOX = 0, MESSAGE_TYPE_OUTBOX = 1;

    @IntDef({MESSAGE_TYPE_INBOX, MESSAGE_TYPE_OUTBOX})
    @interface MessageType {}


    // Group types for recycler view
    int RECYCLER_TYPE_CONTENT = 0x00000010,  RECYCLER_TYPE_HEADER = 0x00000011,
            RECYCLER_TYPE_LOADING = 0x00000100, RECYCLER_TYPE_EMPTY = 0x00000101,
            RECYCLER_TYPE_ERROR = 0x00000110, RECYCLER_TYPE_ANIME = 0x00000111,
            RECYCLER_TYPE_MANGA = 0x00001000;

    @IntDef({RECYCLER_TYPE_CONTENT, RECYCLER_TYPE_HEADER, RECYCLER_TYPE_LOADING,
            RECYCLER_TYPE_EMPTY, RECYCLER_TYPE_ERROR, RECYCLER_TYPE_ANIME,
            RECYCLER_TYPE_MANGA})
    @interface RecyclerViewType {}

    /** Application Tips */
    String KEY_MAIN_TIP = "KEY_MAIN_TIP", KEY_DETAIL_TIP = "KEY_DETAIL_TIP", KEY_NOTIFICATION_TIP = "KEY_NOTIFICATION_TIP",
    KEY_MESSAGE_TIP = "KEY_MESSAGE_TIP", KEY_COMPOSE_TIP = "KEY_COMPOSE_TIP", KEY_CHARACTER_TIP = "KEY_CHARACTER_TIP",
    KEY_STAFF_TIP = "KEY_STAFF_TIP", KEY_STATUS_POST_TIP = "KEY_STATUS_POST_TIP", KEY_USER_PROFILE_TIP = "KEY_USER_PROFILE_TIP",
    KEY_LOGIN_TIP = "KEY_LOGIN_TIP", KEY_GIPHY_TIP = "KEY_GIPHY_TIP",
    KEY_POST_TYPE_TIP = "KEY_POST_TYPE_TIP";

    @StringDef({KEY_MAIN_TIP, KEY_DETAIL_TIP, KEY_NOTIFICATION_TIP, KEY_MESSAGE_TIP, KEY_COMPOSE_TIP, KEY_CHARACTER_TIP,
            KEY_STAFF_TIP, KEY_STATUS_POST_TIP, KEY_USER_PROFILE_TIP, KEY_LOGIN_TIP, KEY_GIPHY_TIP,
            KEY_POST_TYPE_TIP})
    @interface TapTargetType {}



    // ------------------------------------------------------------------------------------
    // Recommendation Type Attributes
    // ------------------------------------------------------------------------------------

    String NO_RATING = "NO_RATING", RATE_UP = "RATE_UP", RATE_DOWN = "RATE_DOWN";
    @StringDef({NO_RATING, RATE_DOWN, RATE_UP})
    @interface RecommendationRating {}

    // ------------------------------------------------------------------------------------
}
