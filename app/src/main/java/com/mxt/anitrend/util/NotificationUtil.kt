package com.mxt.anitrend.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.SpannableStringBuilder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.text.bold
import com.mxt.anitrend.R
import com.mxt.anitrend.extension.getCompatColor
import com.mxt.anitrend.model.entity.anilist.Notification
import com.mxt.anitrend.model.entity.anilist.User
import com.mxt.anitrend.model.entity.container.body.PageContainer
import com.mxt.anitrend.receiver.ClearNotifications
import com.mxt.anitrend.view.activity.detail.NotificationActivity
import kotlin.math.min


/**
 * Created by max on 1/22/2017.
 * NotificationUtil
 */

class NotificationUtil(
    private val context: Context,
    private val settings: Settings,
    private val notificationManager: NotificationManager?
) {

    private var defaultNotificationId = 0x00000011

    private fun multiContentIntent(): PendingIntent {
        // PendingIntent.FLAG_UPDATE_CURRENT will update notification
        val targetActivity = Intent(
                context,
                NotificationActivity::class.java
        )
        return PendingIntent.getActivity(
                context,
                defaultNotificationId,
                targetActivity,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun clearNotificationsIntent(action: String, notificationIdRemote: Long): PendingIntent {
        val intent = Intent(this.context, ClearNotifications::class.java)
        intent.putExtra(KeyUtil.NOTIFICATION_ID, defaultNotificationId)
        intent.putExtra(KeyUtil.NOTIFICATION_ID_REMOTE, notificationIdRemote)
        intent.putExtra(KeyUtil.NOTIFICATION_ACTION, action)
        return PendingIntent.getBroadcast(
            context,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun buildBigNotificationContent(unreadCount: Int, notificationsContainer: PageContainer<Notification>): CharSequence {

        // Take the (at most) last 5 unread notifications
        // and build a list that will be used as the content of the expanded notification.

        val maxNotifications = min(unreadCount, 5)
        val displayedNotificationsCount = min(maxNotifications, notificationsContainer.pageData.size)

        val builder = SpannableStringBuilder()
        for (i in 0 until displayedNotificationsCount) {
            val notification = notificationsContainer.pageData[i]
            builder.bold {
                builder.append("• ")
            }
            when (notification.type) {
                KeyUtil.ACTIVITY_MESSAGE,
                KeyUtil.FOLLOWING,
                KeyUtil.ACTIVITY_MENTION,
                KeyUtil.THREAD_COMMENT_MENTION,
                KeyUtil.THREAD_SUBSCRIBED,
                KeyUtil.THREAD_COMMENT_REPLY,
                KeyUtil.ACTIVITY_LIKE,
                KeyUtil.ACTIVITY_REPLY,
                KeyUtil.ACTIVITY_REPLY_SUBSCRIBED,
                KeyUtil.ACTIVITY_REPLY_LIKE,
                KeyUtil.THREAD_LIKE,
                KeyUtil.THREAD_COMMENT_LIKE -> {
                    builder.bold {
                        builder.append(notification.user.name)
                    }
                    builder.append(": ")
                    builder.append(notification.context)
                }
                KeyUtil.AIRING -> {
                    builder.bold {
                        builder.append(notification.media?.title?.userPreferred)
                    }
                    builder.append(": ")
                    builder.append(context.getString(R.string.notification_episode,
                        notification.episode.toString(), notification.media?.title?.userPreferred))
                }
                KeyUtil.RELATED_MEDIA_ADDITION,
                KeyUtil.MEDIA_DATA_CHANGE,
                KeyUtil.MEDIA_MERGE,
                KeyUtil.MEDIA_DELETION -> {
                    builder.bold {
                        builder.append(notification.media?.title?.userPreferred)
                    }
                    builder.append(": ")
                    builder.append(notification.context)
                }
            }
            if (i != displayedNotificationsCount - 1) {
                builder.appendLine()
            }
        }

        if (unreadCount > displayedNotificationsCount) {
            builder.append("\n• ...")
        }

        return builder
    }

    fun createNotification(userGraphContainer: User, notificationsContainer: PageContainer<Notification>) {
        val notificationIdRemote = notificationsContainer.pageData.first().id
        if (settings.lastDismissedNotificationId == notificationIdRemote)
            return

        val notificationCount = userGraphContainer.unreadNotificationCount
        if (notificationCount > 0)
            defaultNotificationId = defaultNotificationId.inc()

        val notificationBuilder = NotificationCompat.Builder(context, KeyUtil.CHANNEL_ID)
            .setColor(context.getCompatColor(R.color.colorStateBlue))
            .setSmallIcon(R.drawable.ic_new_releases)
            .setPriority(PRIORITY_HIGH)
            .addAction(
                0,
                context.resources.getString(R.string.notification_action_mark_as_read),
                clearNotificationsIntent(
                    KeyUtil.NOTIFICATION_ACTION_CLEAR,
                    notificationIdRemote
                )
            )
            .setDeleteIntent(
                clearNotificationsIntent(
                    KeyUtil.NOTIFICATION_ACTION_DISMISS,
                    notificationIdRemote
                )
            )

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                //define the importance level of the notification
                val importance = NotificationManager.IMPORTANCE_DEFAULT

                //build the actual notification channel, giving it a unique ID and name
                val channel = NotificationChannel(
                        KeyUtil.CHANNEL_ID, KeyUtil.CHANNEL_TITLE, importance
                ).apply {
                    //we can optionally add a description for the channel
                    description = "A channel which shows notifications about events on AniTrend"

                    //we can optionally set notification LED colour
                    lightColor = Color.MAGENTA
                }

                // Register the channel with the system
                notificationManager?.createNotificationChannel(channel)
            }
        }

        if (notificationCount > 0) {
            val notificationContent = buildBigNotificationContent(notificationCount, notificationsContainer)
            notificationBuilder.setContentIntent(multiContentIntent())
                    .setContentTitle(context.getString(
                            when (notificationCount > 1) {
                                true -> R.string.text_notifications
                                else -> R.string.text_notification
                            }, notificationCount)
                    )
                    .setContentText(notificationContent)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(notificationContent))

            notificationManager?.notify(defaultNotificationId, notificationBuilder.build())
        }
    }
}
