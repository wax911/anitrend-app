package com.mxt.anitrend.util.markdown

import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import com.github.rjeschke.txtmark.Processor
import timber.log.Timber

/**
 * Created by max on 2017/03/26.
 * Moved markdown processor to global location
 */
object MarkDownUtil {


    private fun fromMD(content: String): SpannableStringBuilder {
        return try {
            val processedText = Processor.process(content)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                    Html.fromHtml(processedText, Html.FROM_HTML_MODE_LEGACY)
                else ->
                    Html.fromHtml(processedText)
            } as SpannableStringBuilder
        } catch (e: Exception) {
            Timber.e(e)
            SpannableStringBuilder("Unable to process content")
        }
    }

    fun convert(input: String?): Spanned {
        var result = when(input.isNullOrBlank()) {
            true -> fromMD("<b>No content available</b>")
            else -> fromMD(
                RegexUtil.findUserTags(
                    input
                )
            )
        }

        try {
            if (result.isNotEmpty())
                while (result.last() == '\n')
                    result = result.delete(result.lastIndex - 1, result.length)
        } catch (e: Exception) {
            Timber.tag("convert(input)").w(e)
        }

        return result
    }

    fun convertLink(text: String) =
        RegexUtil.createLinkStandard(text)

    fun convertImage(text: String) =
        RegexUtil.createImageStandard(text)

    fun convertYoutube(text: String) =
        RegexUtil.createYoutubeStandard(text)

    fun convertVideo(text: String) =
        RegexUtil.createWebMStandard(text)
}
