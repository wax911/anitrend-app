package com.mxt.anitrend.koin

import android.app.NotificationManager
import android.content.Context
import android.webkit.WebSettings
import co.anitrend.support.markdown.center.CenterPlugin
import co.anitrend.support.markdown.core.CorePlugin
import co.anitrend.support.markdown.ephasis.EmphasisPlugin
import co.anitrend.support.markdown.heading.HeadingPlugin
import co.anitrend.support.markdown.horizontal.HorizontalLinePlugin
import co.anitrend.support.markdown.image.ImagePlugin
import co.anitrend.support.markdown.italics.ItalicsPlugin
import co.anitrend.support.markdown.mention.MentionPlugin
import co.anitrend.support.markdown.spoiler.SpoilerPlugin
import co.anitrend.support.markdown.strike.StrikeThroughPlugin
import co.anitrend.support.markdown.webm.WebMPlugin
import co.anitrend.support.markdown.youtube.YouTubePlugin
import com.bumptech.glide.Glide
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.mxt.anitrend.BuildConfig
import com.mxt.anitrend.analytics.AnalyticsLogging
import com.mxt.anitrend.analytics.contract.ISupportAnalytics
import com.mxt.anitrend.base.plugin.image.GlideImagePlugin
import com.mxt.anitrend.base.plugin.image.ImageConfigurationPlugin
import com.mxt.anitrend.base.plugin.text.TextConfigurationPlugin
import com.mxt.anitrend.model.api.converter.AniGraphConverter
import com.mxt.anitrend.model.api.interceptor.AuthInterceptor
import com.mxt.anitrend.model.api.interceptor.ClientInterceptor
import com.mxt.anitrend.model.entity.MyObjectBox
import com.mxt.anitrend.presenter.base.BasePresenter
import com.mxt.anitrend.presenter.fragment.MediaPresenter
import com.mxt.anitrend.presenter.widget.WidgetPresenter
import com.mxt.anitrend.util.ConfigurationUtil
import com.mxt.anitrend.util.JobSchedulerUtil
import com.mxt.anitrend.util.NotificationUtil
import com.mxt.anitrend.util.Settings
import com.mxt.anitrend.worker.*
import io.github.wax911.library.annotation.processor.GraphProcessor
import io.github.wax911.library.annotation.processor.plugin.AssetManagerDiscoveryPlugin
import io.github.wax911.library.logger.contract.ILogger
import io.github.wax911.library.logger.core.AbstractLogger
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.handler.EmphasisEditHandler
import io.noties.markwon.editor.handler.StrongEmphasisEditHandler
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.wax911.emojify.EmojiManager
import io.wax911.emojify.serializer.gson.GsonDeserializer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber

private val coreModule = module {
    single {
        MyObjectBox.builder()
            .androidContext(androidContext())
            .build()
    }

    single<ISupportAnalytics> {
        AnalyticsLogging(
            context = androidContext(),
            settings = get()
        )
    }

    single {
        JobSchedulerUtil(
            settings = get()
        )
    }

    factory {
        Settings(
            context = androidContext()
        )
    }

    factory {
        ConfigurationUtil(
            settings = get()
        )
    }

    factory {
        val context = androidContext()
        NotificationUtil(
            context = context,
            settings = get(),
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager?
        )
    }
    single(createdAtStart = true) {
        EmojiManager.create(
            context = androidContext(),
            serializer = GsonDeserializer()
        )
    }
    single(
        qualifier = named("ua"),
        createdAtStart = true
    ) {
        WebSettings.getDefaultUserAgent(androidContext())
    }
}

private val widgetModule = module {
    factory {
        Markwon.builder(get())
            .usePlugin(HtmlPlugin.create())
            .usePlugin(CorePlugin.create())
            .usePlugin(HorizontalLinePlugin.create())
            .usePlugin(HeadingPlugin.create())
            .usePlugin(EmphasisPlugin.create())
            .usePlugin(CenterPlugin.create())
            .usePlugin(ImagePlugin.create())
            .usePlugin(WebMPlugin.create())
            .usePlugin(YouTubePlugin.create())
            .usePlugin(LinkifyPlugin.create())
            .usePlugin(SpoilerPlugin.create())
            .usePlugin(MentionPlugin.create())
            .usePlugin(StrikeThroughPlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(get<Context>()))
            .usePlugin(ItalicsPlugin.create())
            .usePlugin(
                GlideImagesPlugin.create(
                    GlideImagePlugin.create(
                        Glide.with(get<Context>()),
                        get(named("ua"))
                    )
                )
            )
            .usePlugin(ImageConfigurationPlugin.create())
            .usePlugin(TextConfigurationPlugin.create())
            .build()
    }
    single {
        MarkwonEditor.builder(get())
            .useEditHandler(EmphasisEditHandler())
            .useEditHandler(StrongEmphasisEditHandler())
            .build()
    }
}

private val workerModule = module {
    worker { scope ->
        AuthenticatorWorker(
            context = androidContext(),
            workerParams = scope.get(),
            presenter = get()
        )
    }
    worker { scope ->
        NotificationWorker(
            context = androidContext(),
            workerParams = scope.get(),
            presenter = get(),
            notificationUtil = get()
        )
    }
    worker { scope ->
        ClearNotificationWorker(
            context = androidContext(),
            workerParams = scope.get()
        )
    }
    worker { scope ->
        val context = androidContext()
        GenreSyncWorker(
            context = context,
            workerParams = scope.get(),
            presenter = WidgetPresenter(context)
        )
    }
    worker { scope ->
        val context = androidContext()
        TagSyncWorker(
            context = context,
            workerParams = scope.get(),
            presenter = WidgetPresenter(context)
        )
    }
    worker { scope ->
        val context = androidContext()
        UpdateWorker(
            context = context,
            workerParams = scope.get(),
            presenter = WidgetPresenter(context)
        )
    }
}

private val presenterModule = module {
    factory {
        BasePresenter(androidContext())
    }
    factory {
        WidgetPresenter<Any>(androidContext())
    }
    factory {
        MediaPresenter(androidContext())
    }
}

private val networkModule = module {
    factory {
        AuthInterceptor(
            settings = get()
        )
    }
    factory {
        ChuckerInterceptor.Builder(context = androidContext())
            .collector(
                collector = ChuckerCollector(
                    context = androidContext(),
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_WEEK
                )
            )
            .maxContentLength(
                length = 250000L
            )
            .redactHeaders(
                headerNames = setOf(BuildConfig.HEADER_KEY)
            )
            .alwaysReadResponseBody(false)
            .build()
    }
    single {
        val logLevel = if (BuildConfig.DEBUG)
            ILogger.Level.INFO
        else ILogger.Level.ERROR

        AniGraphConverter(
            graphProcessor = GraphProcessor(
                discoveryPlugin = AssetManagerDiscoveryPlugin(
                    assetManager = androidContext().assets
                ),
                logger = object : AbstractLogger(logLevel) {
                    /**
                     * Write a log message to its destination.
                     *
                     * @param level Filter used to determine the verbosity level of logs.
                     * @param tag Used to identify the source of a log message. It usually
                     * identifies the class or activity where the log call occurs.
                     * @param message The message you would like logged.
                     * @param throwable An exception to log
                     */
                    override fun log(
                        level: ILogger.Level,
                        tag: String,
                        message: String,
                        throwable: Throwable?
                    ) {
                        when (level) {
                            ILogger.Level.VERBOSE -> Timber.tag(tag).v(throwable, message)
                            ILogger.Level.DEBUG -> Timber.tag(tag).d(throwable, message)
                            ILogger.Level.INFO -> Timber.tag(tag).i(throwable, message)
                            ILogger.Level.WARNING -> Timber.tag(tag).w(throwable, message)
                            ILogger.Level.ERROR -> Timber.e(throwable, message)
                            ILogger.Level.NONE -> {
                            }
                        }
                    }

                }
            ),
            GsonBuilder()
                .addSerializationExclusionStrategy(object : ExclusionStrategy {
                    /**
                     * @param clazz the class object that is under test
                     * @return true if the class should be ignored; otherwise false
                     */
                    override fun shouldSkipClass(clazz: Class<*>?) = false

                    /**
                     * @param f the field object that is under test
                     * @return true if the field should be ignored; otherwise false
                     */
                    override fun shouldSkipField(f: FieldAttributes?): Boolean {
                        return f?.name?.equals("operationName") ?: false
                                ||
                                f?.name?.equals("extensions") ?: false
                    }
                })
                .enableComplexMapKeySerialization()
                .setLenient()
                .create()
        )
    }
    single {
        ClientInterceptor(
            agent = get(named("ua"))
        )
    }
}

val appModules = listOf(
    coreModule,
    widgetModule,
    workerModule,
    presenterModule,
    networkModule
)