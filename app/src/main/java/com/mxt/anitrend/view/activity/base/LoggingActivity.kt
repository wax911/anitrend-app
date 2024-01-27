package com.mxt.anitrend.view.activity.base

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.text.color
import androidx.core.text.toHtml
import androidx.lifecycle.lifecycleScope
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mxt.anitrend.BuildConfig
import com.mxt.anitrend.R
import com.mxt.anitrend.base.custom.activity.ActivityBase
import com.mxt.anitrend.base.custom.view.text.SingleLineTextView
import com.mxt.anitrend.extension.getCompatColor
import com.mxt.anitrend.extension.logFile
import com.mxt.anitrend.presenter.base.BasePresenter
import com.mxt.anitrend.util.NotifyUtil
import com.nguyenhoanglam.progresslayout.ProgressLayout
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader

class LoggingActivity : ActivityBase<Void, BasePresenter>(), CoroutineScope by MainScope() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.report_display)
    lateinit var reportLogTextView: AppCompatTextView

    @BindView(R.id.application_version)
    lateinit var applicationVersionTextView: SingleLineTextView

    @BindView(R.id.stateLayout)
    lateinit var progressLayout: ProgressLayout

    private var binder: Unbinder? = null

    private val spannableLogBuilder = SpannableStringBuilder()
    private val red by lazy { applicationContext.getCompatColor(R.color.colorStateRed) }
    private val orange by lazy {  applicationContext.getCompatColor(R.color.colorStateOrange) }
    private val linePattern = Regex("\\s")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging)
        binder = ButterKnife.bind(this)
        setSupportActionBar(toolbar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        applicationVersionTextView.text = getString(
                R.string.text_about_application_version,
                BuildConfig.versionName
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logging_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_log -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    runCatching {
                        FileWriter(applicationContext.logFile()).use { writer ->
                            writer.write("")
                        }
                        spannableLogBuilder.clear()
                        loadLogFileContents()
                        printLog()
                    }.onFailure {
                        Timber.e(it)
                    }
                }
            }
            R.id.action_save_log -> {
                if (requestPermissionIfMissing(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    runCatching {
                        val root = File(
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS
                            ),
                            "AniTrend Logcat.txt"
                        )
                        applicationContext.logFile().copyTo(root, true)
                    }.onFailure {
                        Timber.e(it)
                    }.onSuccess {
                        NotifyUtil.createAlerter(
                            this@LoggingActivity,
                            R.string.text_post_information,
                            R.string.bug_report_saved,
                            R.drawable.ic_insert_emoticon_white_24dp,
                            R.color.colorStateGreen
                        )
                    }
                }
            }
            R.id.action_share_log -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                        applicationContext,
                        "${applicationContext.packageName}.provider",
                        applicationContext.logFile()
                    ))
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, getString(R.string.abc_shareactionprovider_share_with)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are *not* resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * [.onResumeFragments].
     */
    override fun onResume() {
        super.onResume()
        onActivityReady()
    }

    override fun onActivityReady() {
        progressLayout.showLoading()
        makeRequest()
    }

    override fun updateUI() {
        progressLayout.showContent()
    }

    private suspend fun printLog() {
        withContext(Dispatchers.Main) {
            updateUI()
            reportLogTextView.text = Html.fromHtml(spannableLogBuilder.toHtml())
        }
    }

    private suspend fun loadLogFileContents() {
        //val process = Runtime.getRuntime().exec("logcat -d -v threadtime com.mxt.anitrend:*")
        withContext(Dispatchers.IO) {
            runCatching {
                val logInputStream = applicationContext.logFile().inputStream()
                InputStreamReader(logInputStream).use { inputStream ->
                    inputStream.forEachLine { line ->
                        val segments = line.split(linePattern, 4)
                        val identifier = segments.getOrNull(2)
                        if (identifier != null) {
                            when {
                                identifier.startsWith("E") -> {
                                    spannableLogBuilder.color(red) {
                                        spannableLogBuilder.appendLine(line)
                                    }
                                }
                                identifier.startsWith("W") -> {
                                    spannableLogBuilder.color(orange) {
                                        spannableLogBuilder.appendLine(line)
                                    }
                                }
                                else -> spannableLogBuilder.appendLine(line)
                            }
                        } else
                            spannableLogBuilder.appendLine(line)
                    }
                    inputStream.close()
                }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    override fun makeRequest() {
        lifecycleScope.launch(Dispatchers.Default) {
            loadLogFileContents()
            printLog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binder?.unbind()
    }
}
