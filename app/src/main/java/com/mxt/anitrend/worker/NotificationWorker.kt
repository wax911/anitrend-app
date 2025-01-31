package com.mxt.anitrend.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.mxt.anitrend.base.custom.consumer.BaseConsumer
import com.mxt.anitrend.model.api.retro.WebFactory
import com.mxt.anitrend.model.api.retro.anilist.UserModel
import com.mxt.anitrend.model.entity.anilist.Notification
import com.mxt.anitrend.model.entity.anilist.User
import com.mxt.anitrend.model.entity.container.body.PageContainer
import com.mxt.anitrend.presenter.base.BasePresenter
import com.mxt.anitrend.util.KeyUtil
import com.mxt.anitrend.util.NotificationUtil
import com.mxt.anitrend.util.graphql.GraphUtil
import timber.log.Timber

/**
 * Created by Maxwell on 1/22/2017.
 */
class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val presenter: BasePresenter,
    private val notificationUtil: NotificationUtil
) : CoroutineWorker(context, workerParams) {

    private val userEndpoint by lazy(LazyThreadSafetyMode.NONE) {
        WebFactory.createService(UserModel::class.java, applicationContext)
    }

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to **synchronously** do your work and return the
     * [Result] from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * [ListenableWorker].
     *
     *
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * [Result].  After this time has expired, the Worker will
     * be signalled to stop.
     *
     * @return The [Result] of the computation; note that
     * dependent work will not execute if you use
     * [Result.failure]
     */
    override suspend fun doWork(): Result {
        if (presenter.settings.isAuthenticated) {
            try {
                requestUser()?.apply {
                    if (unreadNotificationCount != 0) {
                        presenter.notifyAllListeners(
                                BaseConsumer(KeyUtil.USER_CURRENT_REQ, this),
                                false
                        )
                        requestNotifications(this)
                    }
                }
                return Result.success()
            } catch (e: Exception) {
                Timber.e(e)
            }
            return Result.retry()
        }
        return Result.failure()
    }

    private fun requestUser(): User? {
        val userGraphContainer = userEndpoint.getCurrentUser(
                GraphUtil.getDefaultQuery(false)
        ).execute().body() as? User?

        return userGraphContainer?.let {
            presenter.database.currentUser = it
            it
        }
    }

    private fun requestNotifications(user: User) {
        val notificationsContainer = userEndpoint.getUserNotifications(
            GraphUtil.getDefaultQuery(false)
        ).execute().body() as? PageContainer<Notification>?

        if (user.unreadNotificationCount > 0 && notificationsContainer != null)
            notificationUtil.createNotification(user, notificationsContainer)
    }
}
