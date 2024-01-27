package com.mxt.anitrend.view.fragment.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.annimon.stream.IntPair;
import com.annimon.stream.Stream;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mxt.anitrend.R;
import com.mxt.anitrend.adapter.recycler.detail.LinkAdapter;
import com.mxt.anitrend.adapter.recycler.detail.RankAdapter;
import com.mxt.anitrend.base.custom.fragment.FragmentBase;
import com.mxt.anitrend.base.interfaces.event.ItemClickListener;
import com.mxt.anitrend.databinding.FragmentSeriesStatsBinding;
import com.mxt.anitrend.model.entity.anilist.ExternalLink;
import com.mxt.anitrend.model.entity.anilist.Media;
import com.mxt.anitrend.model.entity.anilist.MediaRank;
import com.mxt.anitrend.model.entity.anilist.meta.ScoreDistribution;
import com.mxt.anitrend.presenter.fragment.MediaPresenter;
import com.mxt.anitrend.util.ChartUtil;
import com.mxt.anitrend.util.CompatUtil;
import com.mxt.anitrend.util.KeyUtil;
import com.mxt.anitrend.util.NotifyUtil;
import com.mxt.anitrend.util.graphql.GraphUtil;
import com.mxt.anitrend.util.media.MediaBrowseUtil;
import com.mxt.anitrend.util.media.MediaUtil;
import com.mxt.anitrend.view.activity.detail.MediaBrowseActivity;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.github.wax911.library.model.request.QueryContainerBuilder;

/**
 * Created by max on 2017/12/28.
 */

public class MediaStatsFragment extends FragmentBase<Media, MediaPresenter, Media> {

    private FragmentSeriesStatsBinding binding;
    private Media model;
    private ClipboardManager clipboardManager;

    private RankAdapter rankAdapter;
    private LinkAdapter linkAdapter;

    private long mediaId;
    private @KeyUtil.MediaType String mediaType;

    public static MediaStatsFragment newInstance(Bundle args) {
        MediaStatsFragment fragment = new MediaStatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaId = getArguments().getLong(KeyUtil.arg_id);
            mediaType = getArguments().getString(KeyUtil.arg_mediaType);
        }
        clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        isMenuDisabled = true; mColumnSize = R.integer.grid_list_x2;
        setPresenter(new MediaPresenter(getContext()));
        setViewModel(true);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSeriesStatsBinding.inflate(inflater, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        binding.stateLayout.showLoading();
        binding.linksRecycler.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(mColumnSize), StaggeredGridLayoutManager.VERTICAL));
        binding.linksRecycler.setHasFixedSize(true);
        binding.rankingRecycler.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(mColumnSize), StaggeredGridLayoutManager.VERTICAL));
        binding.rankingRecycler.setHasFixedSize(true);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        makeRequest();
    }

    /**
     * Is automatically called in the @onStart Method if overridden in list implementation
     */
    @Override
    protected void updateUI() {
        binding.setModel(model);
        if(rankAdapter == null) {
            rankAdapter = new RankAdapter(getContext());
            rankAdapter.onItemsInserted(model.getRankings());
            rankAdapter.setClickListener(new ItemClickListener<MediaRank>() {
                @Override
                public void onItemClick(View target, IntPair<MediaRank> data) {
                    Intent intent = new Intent(getActivity(), MediaBrowseActivity.class);
                    Bundle args = new Bundle();
                    QueryContainerBuilder queryContainer = GraphUtil.INSTANCE.getDefaultQuery(true)
                            .putVariable(KeyUtil.arg_type, mediaType)
                            .putVariable(KeyUtil.arg_format, data.getSecond().getFormat());

                    if(MediaUtil.isAnimeType(model))
                        queryContainer.putVariable(KeyUtil.arg_season, data.getSecond().getSeason());

                    if(!data.getSecond().isAllTime()) {
                        if (MediaUtil.isAnimeType(model))
                            queryContainer.putVariable(KeyUtil.arg_seasonYear, data.getSecond().getYear());
                        else
                            queryContainer.putVariable(KeyUtil.arg_startDateLike, String.format(Locale.getDefault(),
                                    "%d%%", data.getSecond().getYear()));
                    }

                    switch (data.getSecond().getType()) {
                        case KeyUtil.RATED:
                            queryContainer.putVariable(KeyUtil.arg_sort, KeyUtil.SCORE + KeyUtil.DESC);
                            break;
                        case KeyUtil.POPULAR:
                            queryContainer.putVariable(KeyUtil.arg_sort, KeyUtil.POPULARITY + KeyUtil.DESC);
                            break;
                    }

                    args.putParcelable(KeyUtil.arg_graph_params, queryContainer);
                    args.putParcelable(KeyUtil.arg_media_util, new MediaBrowseUtil()
                            .setCompactType(true)
                            .setFilterEnabled(false));
                    args.putString(KeyUtil.arg_activity_tag, data.getSecond().getTypeHtmlPlainTitle());
                    intent.putExtras(args);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View target, IntPair<MediaRank> data) {

                }
            });
        }

        if(linkAdapter == null) {
            linkAdapter = new LinkAdapter(getContext());
            linkAdapter.onItemsInserted(model.getExternalLinks());
            linkAdapter.setClickListener(new ItemClickListener<ExternalLink>() {
                @Override
                public void onItemClick(View target, IntPair<ExternalLink> data) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(data.getSecond().getUrl()));
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View target, IntPair<ExternalLink> data) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("", data.getSecond().getUrl()));
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        // Android 13 shows a clipboard editor overlay, thus we don't need a toast msg for it.
                        NotifyUtil.INSTANCE.makeText(getContext(), R.string.text_url_copied_to_clipboard, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        binding.stateLayout.showContent();
        binding.linksRecycler.setAdapter(linkAdapter);
        binding.rankingRecycler.setAdapter(rankAdapter);
        showStatusDistribution(); showScoreDistribution();
    }

    /**
     * All new or updated network requests should be handled in this method
     */
    @Override
    public void makeRequest() {
        QueryContainerBuilder queryContainer = GraphUtil.INSTANCE.getDefaultQuery(false)
                .putVariable(KeyUtil.arg_id, mediaId)
                .putVariable(KeyUtil.arg_type, mediaType);
        getViewModel().getParams().putParcelable(KeyUtil.arg_graph_params, queryContainer);
        getViewModel().requestData(KeyUtil.MEDIA_STATS_REQ, getContext());
    }

    private void showScoreDistribution() {
        if(model.getStats() != null && model.getStats().getScoreDistribution() != null) {

            List<BarEntry> barEntries = getPresenter().getMediaScoreDistribution(model.getStats().getScoreDistribution());

            BarDataSet barDataSet = new BarDataSet(barEntries, getString(R.string.title_score_distribution));
            barDataSet.setValueTextColor(CompatUtil.INSTANCE.getColorFromAttr(getContext(), R.attr.titleColor));

            barDataSet.setColors(
                    Color.parseColor("#c26fc1ea"),
                    Color.parseColor("#c248c76d"),
                    Color.parseColor("#c2f7464a"),
                    Color.parseColor("#c29256f3"),
                    Color.parseColor("#c2fba640"),
                    Color.parseColor("#c26fc1ea"),
                    Color.parseColor("#c248c76d"),
                    Color.parseColor("#c2f7464a"),
                    Color.parseColor("#c29256f3"),
                    Color.parseColor("#c2fba640")
            );

            configureScoreDistribution(model.getStats().getScoreDistribution());

            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.6f);

            binding.seriesScoreDist.setData(barData);
            binding.seriesScoreDist.disableScroll();
            binding.seriesScoreDist.setFitBars(true);
            binding.seriesScoreDist.setPinchZoom(false);
            binding.seriesScoreDist.setDoubleTapToZoomEnabled(false);
            binding.seriesScoreDist.invalidate();
        }
    }

    private void showStatusDistribution() {
        if(model.getStats() != null && model.getStats().getStatusDistribution() != null) {
            configureSeriesStats();

            List<PieEntry> pieEntries = getPresenter().getMediaStats(model.getStats().getStatusDistribution());
            PieDataSet pieDataSet = new PieDataSet(pieEntries, getString(R.string.title_series_stats));
            pieDataSet.setSliceSpace(3f);

            // Set legend and section colors with a moderate ~ 20% transparency
            pieDataSet.setColors(
                    Color.parseColor("#c26fc1ea"),
                    Color.parseColor("#c248c76d"),
                    Color.parseColor("#c2f7464a"),
                    Color.parseColor("#c29256f3"),
                    Color.parseColor("#c2fba640")
            );

            PieData pieData = new PieData(pieDataSet);
            if (getContext() != null)
                pieData.setValueTextColor(CompatUtil.INSTANCE.getColorFromAttr(getContext(), R.attr.titleColor));

            pieData.setValueTextSize(9f);
            pieData.setValueFormatter(new PercentFormatter());

            binding.seriesStats.getLegend().setTextColor(CompatUtil.INSTANCE.getColorFromAttr(getContext(), R.attr.titleColor));
            binding.seriesStats.setHoleColor(CompatUtil.INSTANCE.getColorFromAttr(getContext(), R.attr.color));
            binding.seriesStats.setData(pieData);
            binding.seriesStats.invalidate();
        }
    }

    /**
     * Called when the model state is changed.
     *
     * @param model The new data
     */
    @Override
    public void onChanged(@Nullable Media model) {
        if(model != null) {
            this.model = model;
            updateUI();
        } else
            binding.stateLayout.showError(CompatUtil.INSTANCE.getDrawable(getContext(), R.drawable.ic_emoji_sweat),
                    getString(R.string.layout_empty_response), getString(R.string.try_again), view -> { binding.stateLayout.showLoading(); makeRequest(); });
    }

    private void configureScoreDistribution(List<ScoreDistribution> scoreDistributions) {
        binding.seriesScoreDist.getDescription().setEnabled(false);
        binding.seriesScoreDist.setDrawGridBackground(false);
        binding.seriesScoreDist.setDrawBarShadow(false);
        binding.seriesScoreDist.setHighlightFullBarEnabled(true);


        new ChartUtil.StepXAxisFormatter()
                .setDataModel(
                        Stream.of(scoreDistributions)
                                .map(ScoreDistribution::getScore)
                                .toList()
                )
                .setChartBase(binding.seriesScoreDist)
                .build(getContext());

        new ChartUtil.StepYAxisFormatter()
                .setChartBase(binding.seriesScoreDist)
                .build(getContext());
    }

    private void configureSeriesStats() {
        binding.seriesStats.setUsePercentValues(true);
        binding.seriesStats.getDescription().setEnabled(false);
        binding.seriesStats.setExtraOffsets(0, 0, 50, 0);
        binding.seriesStats.setDrawHoleEnabled(true);
        binding.seriesStats.setHoleRadius(58f);
        binding.seriesStats.setTransparentCircleRadius(61f);

        binding.seriesStats.setRotationAngle(0);
        binding.seriesStats.setRotationEnabled(false);
        binding.seriesStats.setHighlightPerTapEnabled(true);

        Legend l = binding.seriesStats.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        // binding.seriesStats.setEntryLabelColor(CompatUtil.getColorFromAttr(getContext(), R.attr.subtitleColor));
        binding.seriesStats.setDrawEntryLabels(false);
    }
}
