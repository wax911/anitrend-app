package com.mxt.anitrend.view.activity.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mxt.anitrend.R;
import com.mxt.anitrend.adapter.pager.detail.AnimePageAdapter;
import com.mxt.anitrend.adapter.pager.detail.MangaPageAdapter;
import com.mxt.anitrend.base.custom.activity.ActivityBase;
import com.mxt.anitrend.base.custom.pager.BaseStatePageAdapter;
import com.mxt.anitrend.base.custom.view.image.WideImageView;
import com.mxt.anitrend.base.custom.view.widget.FavouriteToolbarWidget;
import com.mxt.anitrend.databinding.ActivitySeriesBinding;
import com.mxt.anitrend.model.entity.base.MediaBase;
import com.mxt.anitrend.model.entity.container.request.QueryContainerBuilder;
import com.mxt.anitrend.presenter.fragment.MediaPresenter;
import com.mxt.anitrend.util.CompatUtil;
import com.mxt.anitrend.util.GraphUtil;
import com.mxt.anitrend.util.KeyUtil;
import com.mxt.anitrend.util.NotifyUtil;
import com.mxt.anitrend.util.MediaActionUtil;
import com.mxt.anitrend.util.TapTargetUtil;
import com.mxt.anitrend.view.activity.base.ImagePreviewActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by max on 2017/12/01.
 * Media activity
 */

public class MediaActivity extends ActivityBase<MediaBase, MediaPresenter> implements View.OnClickListener {

    private ActivitySeriesBinding binding;
    private MediaBase model;

    private @KeyUtil.MediaType String mediaType;

    private FavouriteToolbarWidget favouriteWidget;

    protected @BindView(R.id.toolbar) Toolbar toolbar;
    protected @BindView(R.id.page_container) ViewPager viewPager;
    protected @BindView(R.id.smart_tab) SmartTabLayout smartTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_series);
        setPresenter(new MediaPresenter(getApplicationContext()));
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        disableToolbarTitle();
        setViewModel(true);
        if(getIntent().hasExtra(KeyUtil.arg_id))
            id = getIntent().getLongExtra(KeyUtil.arg_id, -1);
        if(getIntent().hasExtra(KeyUtil.arg_mediaType))
            mediaType = getIntent().getStringExtra(KeyUtil.arg_mediaType);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBar.setHomeAsUpIndicator(CompatUtil.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        onActivityReady();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean isAuth = getPresenter().getApplicationPref().isAuthenticated();
        getMenuInflater().inflate(R.menu.media_base_menu, menu);
        menu.findItem(R.id.action_favourite).setVisible(isAuth);
        menu.findItem(R.id.action_manage).setVisible(isAuth);

        if(isAuth) {
            MenuItem favouriteMenuItem = menu.findItem(R.id.action_favourite);
            favouriteWidget = (FavouriteToolbarWidget) favouriteMenuItem.getActionView();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(model != null) {
            switch (item.getItemId()) { 
                case R.id.action_manage:
                    mediaActionUtil = new MediaActionUtil.Builder()
                            .setModel(model).build(this);
                    mediaActionUtil.startSeriesAction();
                    break;
            }
        } else
            NotifyUtil.makeText(getApplicationContext(), R.string.text_activity_loading, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Make decisions, check for permissions or fire background threads from this method
     * N.B. Must be called after onPostCreate
     */
    @Override
    protected void onActivityReady() {
        if(mediaType != null) {
            BaseStatePageAdapter baseStatePageAdapter = new AnimePageAdapter(getSupportFragmentManager(), getApplicationContext());
            if (!Objects.equals(mediaType, KeyUtil.ANIME))
                baseStatePageAdapter = new MangaPageAdapter(getSupportFragmentManager(), getApplicationContext());
            baseStatePageAdapter.setParams(getIntent().getExtras());
            viewPager.setAdapter(baseStatePageAdapter);
            viewPager.setOffscreenPageLimit(offScreenLimit + 4);
            smartTabLayout.setViewPager(viewPager);
        } else
            NotifyUtil.createAlerter(this, R.string.text_error_request, R.string.text_unknown_error, R.drawable.ic_warning_white_18dp, R.color.colorStateRed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(model == null)
            makeRequest();
        else
            updateUI();
    }

    @Override
    protected void updateUI() {
        if(model != null) {
            binding.setModel(model);
            binding.setOnClickListener(this);
            if (favouriteWidget != null)
                favouriteWidget.setModel(model);
            WideImageView.setImage(binding.seriesBanner, model.getCoverImage().getLarge());
            showApplicationTips();
        }
    }

    @Override
    protected void makeRequest() {
        QueryContainerBuilder queryContainer = GraphUtil.getDefaultQuery(false)
                .putVariable(KeyUtil.arg_mediaType, mediaType)
                .putVariable(KeyUtil.arg_id, id);

        getViewModel().getParams().putParcelable(KeyUtil.arg_graph_params, queryContainer);
        getViewModel().requestData(KeyUtil.MEDIA_BASE_REQ, getApplicationContext());
    }

    /**
     * Called when the model state is changed.
     *
     * @param model The new data
     */
    @Override
    public void onChanged(@Nullable MediaBase model) {
        super.onChanged(model);
        this.model = model;
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.series_banner:
                Intent intent = new Intent(this, ImagePreviewActivity.class);
                intent.putExtra(KeyUtil.arg_model, model.getCoverImage());
                CompatUtil.startSharedImageTransition(this, view, intent, R.string.transition_image_preview);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(favouriteWidget != null)
            favouriteWidget.onViewRecycled();
        super.onDestroy();
    }

    private void showApplicationTips() {
        if (!TapTargetUtil.isActive(KeyUtil.KEY_DETAIL_TIP) && getPresenter().getApplicationPref().isAuthenticated()) {
            if (getPresenter().getApplicationPref().shouldShowTipFor(KeyUtil.KEY_DETAIL_TIP)) {
                TapTargetUtil.buildDefault(this, R.string.tip_series_options_title, R.string.tip_series_options_message, R.id.action_manage)
                        .setPromptStateChangeListener((prompt, state) -> {
                            if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                                getPresenter().getApplicationPref().disableTipFor(KeyUtil.KEY_DETAIL_TIP);
                            if (state == MaterialTapTargetPrompt.STATE_DISMISSED)
                                TapTargetUtil.setActive(KeyUtil.KEY_DETAIL_TIP, true);
                        }).setFocalColour(CompatUtil.getColor(this, R.color.grey_600)).show();
                TapTargetUtil.setActive(KeyUtil.KEY_DETAIL_TIP, false);
            }
        }
    }
}
