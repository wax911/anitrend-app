package com.mxt.anitrend.base.custom.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mxt.anitrend.R;
import com.mxt.anitrend.base.custom.consumer.BaseConsumer;
import com.mxt.anitrend.base.interfaces.event.RetroCallback;
import com.mxt.anitrend.base.interfaces.view.CustomView;
import com.mxt.anitrend.databinding.WidgetAutoIncrementerBinding;
import com.mxt.anitrend.model.entity.anilist.MediaList;
import com.mxt.anitrend.presenter.widget.WidgetPresenter;
import com.mxt.anitrend.util.CompatUtil;
import com.mxt.anitrend.util.KeyUtil;
import com.mxt.anitrend.util.NotifyUtil;
import com.mxt.anitrend.util.date.DateUtil;
import com.mxt.anitrend.util.graphql.AniGraphErrorUtilKt;
import com.mxt.anitrend.util.media.MediaListUtil;
import com.mxt.anitrend.util.media.MediaUtil;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by max on 2018/02/22.
 * auto increment widget for changing series progress with just a tap
 */

public class AutoIncrementWidget extends LinearLayout implements CustomView, View.OnClickListener, RetroCallback<MediaList> {

    private @KeyUtil.RequestType int requestType = KeyUtil.MUT_SAVE_MEDIA_LIST;
    private WidgetPresenter<MediaList> presenter;
    private WidgetAutoIncrementerBinding binding;
    private @KeyUtil.MediaListStatus String status;
    private MediaList model;

    private String currentUser;
    private final String TAG = AutoIncrementWidget.class.getSimpleName();

    public AutoIncrementWidget(Context context) {
        super(context);
        onInit();
    }

    public AutoIncrementWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public AutoIncrementWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoIncrementWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    @Override
    public void onInit() {
        presenter = new WidgetPresenter<>(getContext());
        binding = WidgetAutoIncrementerBinding.inflate(CompatUtil.INSTANCE.getLayoutInflater(getContext()), this, true);
        binding.setOnClickEvent(this);
    }

    @Override
    public void onClick(View view) {
        if(presenter.isCurrentUser(currentUser) && MediaUtil.isAllowedStatus(model)) {
            if (!MediaUtil.isIncrementLimitReached(model)) {
                switch (view.getId()) {
                    case R.id.widget_flipper:
                        if (binding.widgetFlipper.getDisplayedChild() == WidgetPresenter.CONTENT_STATE) {
                            binding.widgetFlipper.showNext();
                            updateModelState();
                        } else
                            NotifyUtil.INSTANCE.makeText(getContext(), R.string.busy_please_wait, Toast.LENGTH_SHORT).show();
                        break;
                }
            } else
                NotifyUtil.INSTANCE.makeText(getContext(), MediaUtil.isAnimeType(model.getMedia()) ?
                                R.string.text_unable_to_increment_episodes : R.string.text_unable_to_increment_chapters,
                        R.drawable.ic_warning_white_18dp, Toast.LENGTH_SHORT).show();
        }
    }

    public void setModel(MediaList model, String currentUser) {
        this.model = model; this.currentUser = currentUser; this.status = model.getStatus();
        binding.seriesProgressIncrement.setSeriesModel(model, presenter.isCurrentUser(currentUser));
    }

    @Override
    public void onViewRecycled() {
        resetFlipperState();
        if(presenter != null)
            presenter.onDestroy();
        model = null;
    }

    private void resetFlipperState() {
        if(binding.widgetFlipper.getDisplayedChild() == WidgetPresenter.LOADING_STATE)
            binding.widgetFlipper.setDisplayedChild(WidgetPresenter.CONTENT_STATE);
    }

    @Override
    public void onResponse(@NonNull Call<MediaList> call, @NonNull Response<MediaList> response) {
        try {
            MediaList mediaList;
            final MediaList modelClone = model.clone();
            if(response.isSuccessful() && (mediaList = response.body()) != null) {
                boolean isModelCategoryChanged = !CompatUtil.INSTANCE.equals(mediaList.getStatus(), status);
                mediaList.setMedia(modelClone.getMedia()); model = mediaList.clone();
                binding.seriesProgressIncrement.setSeriesModel(model, presenter.isCurrentUser(currentUser));
                if(isModelCategoryChanged || MediaListUtil.INSTANCE.isProgressUpdatable(modelClone)) {
                    if(isModelCategoryChanged)
                        NotifyUtil.INSTANCE.makeText(getContext(), R.string.text_changes_saved, R.drawable.ic_check_circle_white_24dp, Toast.LENGTH_SHORT).show();
                    presenter.notifyAllListeners(new BaseConsumer<>(requestType, model), false);
                } else
                    resetFlipperState();
            } else {
                resetFlipperState();
                Timber.tag(TAG).w(AniGraphErrorUtilKt.apiError(response));
                NotifyUtil.INSTANCE.makeText(getContext(), R.string.text_error_request, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Timber.w(e);
        }
    }

    @Override
    public void onFailure(@NonNull Call<MediaList> call, @NonNull Throwable throwable) {
        try {
            Timber.w(throwable);
            resetFlipperState();
        } catch (Exception e) {
            Timber.e(e);
        }
    }


    private void updateModelState() {
        if (model.getProgress() < 1 &&
                (CompatUtil.INSTANCE.equals(model.getStatus(), KeyUtil.PLANNING) || CompatUtil.INSTANCE.equals(model.getStatus(), KeyUtil.CURRENT))) {
            model.setStatus(KeyUtil.CURRENT);
            model.setStartedAt(DateUtil.INSTANCE.getCurrentDate());
        }
        model.setProgress(model.getProgress() + 1);
        if(MediaUtil.isIncrementLimitReached(model)) {
            model.setStatus(KeyUtil.COMPLETED);
            model.setCompletedAt(DateUtil.INSTANCE.getCurrentDate());
        }
        presenter.setParams(MediaListUtil.INSTANCE.getMediaListParams(model, presenter.getDatabase()
                .getCurrentUser().getMediaListOptions().getScoreFormat()));
        presenter.requestData(requestType, getContext(), this);
    }
}
