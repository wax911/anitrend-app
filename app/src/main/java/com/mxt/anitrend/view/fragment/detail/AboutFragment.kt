package com.mxt.anitrend.view.fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mxt.anitrend.BuildConfig
import com.mxt.anitrend.R
import com.mxt.anitrend.base.custom.fragment.FragmentBase
import com.mxt.anitrend.presenter.base.BasePresenter
import com.mxt.anitrend.util.DialogUtil
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

/**
 * Created by max on 2018/03/04.
 * Application about screen
 */

class AboutFragment : FragmentBase<Void, BasePresenter, Void>() {

    private val aboutPage by lazy(LazyThreadSafetyMode.NONE) {
        AboutPage(activity)
                .setImage(R.mipmap.ic_launcher)
                .addGroup(getString(R.string.text_about_general_information))
                .setDescription(getString(R.string.app_description))
                .addItem(Element().setTitle(getString(R.string.text_about_application_version, BuildConfig.versionName)))
                .addPlayStore("com.mxt.anitrend")
                .addTwitter("anitrend_app")
                .addGroup(getString(R.string.text_about_additional_information))
                .addGitHub("AniTrend")
                .addWebsite("https://anitrend.co")
                .addItem(Element().setTitle(getString(R.string.text_what_is_new))
                        .setOnClickListener { DialogUtil.createChangeLog(activity) }
                        .setIconDrawable(R.drawable.ic_fiber_new_white_24dp))
                .addItem(Element().setTitle(getString(R.string.text_about_frequently_asked_questions))
                        .setIconDrawable(R.drawable.ic_help_grey_600_24dp)
                        .setIntent(Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://anitrend.gitbook.io/project/faq"
                                ))))
                .addGroup(getString(R.string.text_about_legal_information))
                .addItem(Element().setTitle(getString(R.string.text_about_terms_and_conditions))
                        .setIconDrawable(R.drawable.ic_privacy_grey_600_24dp)
                        .setIntent(Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://github.com/AniTrend/anitrend-app/blob/develop/TERMS_OF_SERVICE.md"
                                )
                        )))
                .addItem(Element().setTitle(getString(R.string.text_about_code_of_conduct))
                        .setIconDrawable(R.drawable.ic_privacy_grey_600_24dp)
                        .setIntent(Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://github.com/AniTrend/anitrend-app/blob/develop/CODE_OF_CONDUCT.md"
                                )
                        )))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(BasePresenter(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return aboutPage.create()
    }

    override fun updateUI() {

    }

    override fun makeRequest() {

    }

    override fun onChanged(model: Void?) {

    }

    companion object {

        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}
